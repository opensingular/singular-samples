package br.net.mirante.singular.form.mform;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MILista<E extends MInstancia> extends MInstancia implements Iterable<E>, IPathEnabledInstance {

    private List<E> valores;

    private MTipo<E> tipoElementos;

    public MILista() {
    }

    static <I extends MInstancia> MILista<I> of(MTipo<I> tipoElementos) {
        MILista<I> lista = new MILista<>();
        lista.setTipo(tipoElementos.getDicionario().getTipo(MTipoLista.class));
        lista.tipoElementos = tipoElementos;
        return lista;
    }

    @Override
    public MTipoLista<?> getMTipo() {
        return (MTipoLista<?>) super.getMTipo();
    }

    @SuppressWarnings("unchecked")
    public MTipo<E> getTipoElementos() {
        if (tipoElementos == null) {
            tipoElementos = (MTipo<E>) getMTipo().getTipoElementos();
        }
        return tipoElementos;
    }

    @Override
    public List<Object> getValor() {
        if (valores == null) {
            return Collections.emptyList();
        }
        return valores.stream().map(v -> v.getValor()).collect(Collectors.toList());
    }

    @Override
    public final <T extends Object> T getValor(String pathCampo, Class<T> classeDestino) {
        return getValor(new LeitorPath(pathCampo), classeDestino);
    }

    @Override
    public boolean isEmptyOfData() {
        return isEmpty() || valores.stream().allMatch(i -> i.isEmptyOfData());
    }

    public E addNovo() {
        if (getTipoElementos() instanceof MTipoComposto) {
            E instancia = getTipoElementos().novaInstancia();
            addInterno(instancia);
            return instancia;
        }
        throw new RuntimeException("O tipo da lista não é um tipo composto (é " + getTipoElementos().getNome() + ")");
    }

    public E addNovoAt(int index) {
        if (getTipoElementos() instanceof MTipoComposto) {
            E instancia = getTipoElementos().novaInstancia();
            addAtInterno(index, instancia);
            return instancia;
        }
        throw new RuntimeException("O tipo da lista não é um tipo composto (é " + getTipoElementos().getNome() + ")");
    }

    public void addValor(Object valor) {
        if (valor == null) {
            throw new RuntimeException("Não é aceito null na lista de instâncias");
        }
        E instancia = getTipoElementos().novaInstancia();
        instancia.setValor(valor);
        if (instancia.isEmptyOfData()) {
            throw new RuntimeException("Apesar da opção '" + valor
                    + "' não ser null, o resultado na instância foi convertido para null. Não é permitido ter uma opção com valor null");
        }
        addInterno(instancia);
    }

    private void addInterno(E instancia) {
        if (valores == null) {
            valores = new ArrayList<>();
        }
        valores.add(instancia);
        instancia.setPai(this);
    }

    private void addAtInterno(int index, E instancia) {
        if (valores == null) {
            valores = new ArrayList<>();
        }
        valores.add(index, instancia);
        instancia.setPai(this);
    }

    public MInstancia get(int index) {
        if (valores == null) {
            throw new IndexOutOfBoundsException("A lista " + getNome() + " está vazia (index=" + index + ")");
        }
        return valores.get(index);
    }

    @Override
    public MInstancia getCampo(String path) {
        return getCampo(new LeitorPath(path));
    }

    @Override
    final MInstancia getCampoLocal(LeitorPath leitor) {
        if (!leitor.isIndice()) {
            throw new RuntimeException(leitor.getTextoErro(this, "Era esperado um indice do elemento (exemplo [1])"));
        }
        MInstancia instancia = isEmpty() ? null : valores.get(leitor.getIndice());
        if (instancia == null) {
            MFormUtil.resolverTipoCampo(getMTipo(), leitor);
        }
        return instancia;
    }

    @Override
    final MInstancia getCampoLocalSemCriar(LeitorPath leitor) {
        if (!leitor.isIndice()) {
            throw new RuntimeException(leitor.getTextoErro(this, "Era esperado um indice do elemento (exemplo [1])"));
        }
        return isEmpty() ? null : valores.get(leitor.getIndice());
    }

    @Override
    public final void setValor(String pathCampo, Object valor) {
        setValor(new LeitorPath(pathCampo), valor);
    }

    @Override
    void setValor(LeitorPath leitorPath, Object valor) {
        if (!leitorPath.isIndice()) {
            throw new RuntimeException(leitorPath.getTextoErro(this, "Era esperado um indice do elemento (exemplo [1])"));
        }
        MInstancia instancia = get(leitorPath.getIndice());
        if (leitorPath.isUltimo()) {
            instancia.setValor(valor);
        } else {
            instancia.setValor(leitorPath.proximo(), valor);
        }
    }

    public MInstancia remove(int index) {
        if (valores == null) {
            throw new IndexOutOfBoundsException("A lista " + getNome() + " está vazia (index=" + index + ")");
        }
        return valores.remove(index);
    }

    public Object getValorAt(int index) {
        return get(index).getValor();
    }

    public int indexOf(MInstancia object) {
        return valores.indexOf(object);
    }

    public int size() {
        return (valores == null) ? 0 : valores.size();
    }

    public boolean isEmpty() {
        return (valores == null) ? true : valores.isEmpty();
    }

    public List<E> getValores() {
        return (valores == null) ? Collections.emptyList() : valores;
    }

    @Override
    public Iterator<E> iterator() {
        return (valores == null) ? Collections.emptyIterator() : valores.iterator();
    }

    public Stream<E> stream() {
        return getValores().stream();
    }

    public String toDebug() {
        return stream().map(i -> i.getDisplayString()).collect(Collectors.joining("; "));
    }
}

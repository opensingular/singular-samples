package br.net.mirante.mform;

public class MISimples<TIPO_NATIVO> extends MInstancia {

    private TIPO_NATIVO valor;

    protected MISimples() {
    }

    @Override
    public TIPO_NATIVO getValor() {
        return valor;
    }

    @Override
    public TIPO_NATIVO getValorWithDefault() {
        TIPO_NATIVO v = getValor();
        if (v == null) {
            return getMTipo().converter(getMTipo().getValorAtributoDefaultValueIfNull());
        }
        return v;
    }

    @Override
    final <T extends Object> T getValor(LeitorPath leitor, Class<T> classeDestino) {
        if (!leitor.isEmpty()) {
            throw new RuntimeException("Não ser aplica path a um tipo simples");
        }
        return getValor(classeDestino);
    }

    @Override
    final <T extends Object> T getValorWithDefaultIfNull(LeitorPath leitor, Class<T> classeDestino) {
        if (!leitor.isEmpty()) {
            throw new RuntimeException("Não ser aplica path a um tipo simples");
        }
        return getValorWithDefault(classeDestino);
    }

    @Override
    public boolean isNull() {
        return valor == null;
    }

    @Override
    public final void setValor(Object valor) {
        this.valor = onSetValor(getMTipo().converter(valor));
    }

    protected TIPO_NATIVO onSetValor(TIPO_NATIVO valor) {
        return valor;
    }

    @Override
    public MTipoSimples<?, TIPO_NATIVO> getMTipo() {
        return (MTipoSimples<?, TIPO_NATIVO>) super.getMTipo();
    }

    @Override
    public String getDisplayString() {
        return getMTipo().toStringDisplay(valor);
    }

    public String toStringPersistencia() {
        if (valor == null) {
            return null;
        }
        return getMTipo().toStringPersistencia(valor);
    }
}

package br.net.mirante.singular.form.mform.util.transformer;

import br.net.mirante.singular.form.mform.SIComposite;
import br.net.mirante.singular.form.mform.SList;
import br.net.mirante.singular.form.mform.SISimple;
import br.net.mirante.singular.form.mform.SInstance2;
import br.net.mirante.singular.form.mform.SType;
import br.net.mirante.singular.form.mform.STypeComposto;
import br.net.mirante.singular.form.mform.STypeLista;
import br.net.mirante.singular.form.mform.STypeSimples;
import br.net.mirante.singular.form.mform.SingularFormException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Essa classe utilitaria realiza uma serie de operacoes sobre os valores guardados pelos MTIpos
 */
public class Value {

    private SInstance2 instancia;

    public Value(SInstance2 instancia) {
        this.instancia = instancia;
    }

    private static SInstance2 getInstance(SInstance2 instancia, SType target) {
        return (SInstance2) instancia.findNearest(target).orElse(null);
    }

    /**
     * @param current instancia a partir da qual será buscada a instancia mais proxima do tipo simples tipo
     * @param tipo    um tipo simples
     * @param <T>
     * @return false se o valor do tipo simples for nulo ou se o tipo não for encontrado a partir da instancia
     * current informada
     */
    public static <T> boolean notNull(SInstance2 current, STypeSimples<? extends SISimple<T>, T> tipo) {
        return Value.of(current, tipo) != null;
    }

    public static <T> boolean notNull(SInstance2 current, STypeComposto tipo) {
        if (current != null && tipo != null) {
            SIComposite targetInstance = (SIComposite) getInstance(current, tipo);
            return Value.notNull(targetInstance);
        }
        return false;
    }


    public static <T> boolean notNull(SInstance2 current, STypeLista tipo) {
        if (current != null && tipo != null) {
            SList instanciaLista = (SList) getInstance(current, tipo);
            return Value.notNull(instanciaLista);
        }
        return false;
    }

    public static <T> boolean notNull(SList instanciaLista) {
        return instanciaLista != null && !instanciaLista.isEmpty();
    }

    public static <T> boolean notNull(SIComposite instanciaComposta) {
        return instanciaComposta != null && !instanciaComposta.isEmptyOfData();
    }

    public static <T> boolean notNull(SISimple instanciaSimples) {
        return instanciaSimples != null && !instanciaSimples.isEmptyOfData();
    }

    /**
     * Retorna o valor de uma instancia simples
     * @param instanciaSimples
     * @param <T>
     * @return
     */
    public static <T> T of(SISimple<?> instanciaSimples) {
        if (instanciaSimples != null) {
            return (T) instanciaSimples.getValor();
        }
        return null;
    }

    public static <T> boolean notNull(SInstance2 instancia) {
        if (instancia instanceof SIComposite) {
            return Value.notNull((SIComposite) instancia);
        } else if (instancia instanceof SISimple) {
            return Value.notNull((SISimple) instancia);
        } else if (instancia instanceof SList) {
            return Value.notNull((SList) instancia);
        } else {
            throw new SingularFormException("Tipo de instancia não suportado");
        }
    }

    /**
     * Retorna o valor de uma instancia de um tipo simples que pode ser alcançada a partir
     * do {@paramref instancia} fornecido
     * @param instancia
     * @param tipo
     * @param <T>
     * @return
     */
    public static <T> T of(SInstance2 instancia, STypeSimples<? extends SISimple<T>, T> tipo) {
        if (instancia != null && tipo != null) {
            SISimple targetInstance = (SISimple) getInstance(instancia, tipo);
            if (targetInstance != null) {
                return (T) Value.of(targetInstance);
            }
        }
        return null;
    }

    /**
     * Configura os valores contidos em value
     * na MInstancia passara como parametro recursivamente.
     * Usualmente value é o retorno do metodo dehydrate.
     * @param instancia
     * @param value
     */
    public static void hydrate(SInstance2 instancia, Object value) {
        if (instancia != null) {
            if (instancia instanceof SIComposite) {
                fromMap((Map<String, Object>) value, (SIComposite) instancia);
            } else if (instancia instanceof SISimple) {
                ((SISimple) instancia).setValor(value);
            } else if (instancia instanceof SList) {
                fromList((List<Object>) value, (SList) instancia);
            } else {
                throw new SingularFormException("Tipo de instancia não suportado");
            }
        }
    }

    private static void fromMap(Map<String, Object> map, SIComposite instancia) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            hydrate(instancia.getCampo(entry.getKey()), entry.getValue());
        }
    }

    private static void fromList(List<Object> list, SList sList) {
        for (Object o : list) {
            SInstance2 novo = sList.addNovo();
            hydrate(novo, o);
        }
    }

    /**
     * Extrai para objetos serializáveis todos
     * os dados de uma MIinstancia recursivamente
     *
     * @param value
     *  MIinstancia a partir da qual se deseja extrair os dados
     * @return
     *  Objetos serializáveis representando os dados da MInstancia
     */
    public static Object dehydrate(SInstance2 value) {
        if (value != null) {
            if (value instanceof SIComposite) {
                Map<String, Object> map = new LinkedHashMap<>();
                toMap(map, (SInstance2) value);
                return map;
            } else if (value instanceof SISimple) {
                return ((SISimple) value).getValor();
            } else if (value instanceof SList) {
                List<Object> list = new ArrayList<>();
                toList(list, (SInstance2) value);
                return list;
            } else {
                throw new SingularFormException("Tipo de instancia não suportado");
            }
        }
        return null;
    }

    /**
     * Remove um espiríto maligno (valores serializaveis) de um corpo puro e inocente (MIinstancia)
     * e de toda sua descendência.
     * @param innocentVessel
     * @return
     */
    public static Soul exorcize(SInstance2 innocentVessel){
        Soul s = new Soul();
        s.value = dehydrate(innocentVessel);
        return s;
    }

    /**
     * Realiza um ritual para encarnar um espirito maligno em um pobre corpo inocente.
     * @param pureVessel
     *  A pobre vitma do ritual
     * @param evilSpirit
     *  A alma do espírito realmente extraída a partir do método exorcize
     */
    public static void possess(SInstance2 pureVessel, Soul evilSpirit) {
        hydrate(pureVessel, evilSpirit.value);
    }

    private static void toMap(Map<String, Object> value, SInstance2 instancia) {
        if (instancia instanceof SIComposite) {
            SIComposite item = (SIComposite) instancia;
            for (SInstance2 i : item.getAllChildren()) {
                value.put(i.getNome(), dehydrate(i));
            }
        }
    }

    private static void toList(List<Object> value, SInstance2 instancia) {
        if (instancia instanceof SList<?>) {
            for (SInstance2 i : ((SList<?>) instancia).getValores()) {
                value.add(dehydrate(i));
            }
        }
    }

    public <T> T of(STypeSimples<? extends SISimple<T>, T> tipo) {
        return Value.of(instancia, tipo);
    }

    public <T> boolean notNull(STypeSimples<? extends SISimple<T>, T> tipo) {
        return Value.notNull(instancia, tipo);
    }

    public static class Soul {
        private Object value;
    }

}

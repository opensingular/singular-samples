package br.net.mirante.singular.flow.core.variable.type;

import br.net.mirante.singular.flow.core.variable.VarDefinition;
import br.net.mirante.singular.flow.core.variable.VarInstance;
import br.net.mirante.singular.flow.core.variable.VarType;

public class VarTypeLong implements VarType {

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public String toDisplayString(VarInstance varInstance) {
        return toDisplayString(varInstance.getValor(), varInstance.getDefinicao());
    }

    @Override
    public String toDisplayString(Object valor, VarDefinition varDefinition) {
        return Long.toString((Long) valor);
    }

    @Override
    public String toPersistenceString(VarInstance varInstance) {
        return Long.toString((Long) varInstance.getValor());
    }
}
package br.net.mirante.singular.ui.mform.core;

import java.util.function.Supplier;

import br.net.mirante.singular.ui.mform.MTranslatorParaAtributo;

public class AtrFormula extends MTranslatorParaAtributo {

    public AtrFormula set(Supplier<Object> supplier) {
        getTipo().setValorAtributo(MPacoteCore.ATR_FORMULA, null, null);
        return this;
    }

}
package br.net.mirante.singular.showcase.input.core;

import br.net.mirante.singular.showcase.CaseBase;

import java.io.Serializable;

public class CaseInputCoreDate extends CaseBase implements Serializable {

    public CaseInputCoreDate() {
        super("Date");
        setDescriptionHtml("Componente para inserção de data através da seleção por calendário ou texto.");
    }
}
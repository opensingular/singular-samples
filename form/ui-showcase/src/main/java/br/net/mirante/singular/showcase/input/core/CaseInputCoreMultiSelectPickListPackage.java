package br.net.mirante.singular.showcase.input.core;

import br.net.mirante.singular.form.mform.MPacote;
import br.net.mirante.singular.form.mform.MTipoComposto;
import br.net.mirante.singular.form.mform.MTipoLista;
import br.net.mirante.singular.form.mform.PacoteBuilder;
import br.net.mirante.singular.form.mform.basic.ui.AtrBasic;
import br.net.mirante.singular.form.mform.basic.view.MSelecaoMultiplaPorPicklistView;
import br.net.mirante.singular.form.mform.core.MIString;
import br.net.mirante.singular.form.mform.core.MTipoString;

public class CaseInputCoreMultiSelectPickListPackage extends MPacote {

    //@formatter:off
    @Override
    protected void carregarDefinicoes(PacoteBuilder pb) {

        MTipoComposto<?> tipoMyForm = pb.createTipoComposto("testForm");

        MTipoString tipoContato = pb.createTipo("tipoContato", MTipoString.class)
                 .withSelectionOf("Endereço", "Email", "Telefone", "Celular", "Fax");

        MTipoLista<MTipoString, MIString> infoPub = tipoMyForm.addCampoListaOf("infoPub", tipoContato);
/*
        MTipoLista<MTipoString, MIString> infoPub = tipoMyForm.addCampoListaOf("infoPub","tipoContrato")
                .withMultiSelectionOf("Endereço", "Email", "Telefone", "Celular", "Fax");

        MTipoLista<MTipoString, MIString> infoPub = tipoMyForm.addCampoListaOf("infoPub","tipoContrato")
                .withMultiSelectionOf("Endereço", "Email", "Telefone", "Celular", "Fax");


        MTipoLista<MTipoString, MIString> infoPub = tipoMyForm.addCampoListaOf("infoPub", "tipoContato", MTipoString.class);
        infoPub.getTipoElemento().withSelectionOf("Endereço", "Email", "Telefone", "Celular", "Fax");

        MTipoLista<MTipoString, MIString> infoPub = tipoMyForm.addCampoSelecaoMultiplaOf("infoPub", "tipoContato", MTipoString.class);
        infoPub.getTipoElemento().withSelectionOf("Endereço", "Email", "Telefone", "Celular", "Fax");
*/
        infoPub
            .withView(MSelecaoMultiplaPorPicklistView::new)
            .as(AtrBasic::new).label("Informações Públicas");
    }

}

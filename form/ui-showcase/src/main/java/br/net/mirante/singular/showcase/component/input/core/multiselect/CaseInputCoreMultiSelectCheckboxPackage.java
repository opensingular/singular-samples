package br.net.mirante.singular.showcase.component.input.core.multiselect;

import br.net.mirante.singular.form.mform.SPackage;
import br.net.mirante.singular.form.mform.STypeComposite;
import br.net.mirante.singular.form.mform.STypeList;
import br.net.mirante.singular.form.mform.PackageBuilder;
import br.net.mirante.singular.form.mform.basic.ui.AtrBasic;
import br.net.mirante.singular.form.mform.basic.view.SMultiSelectionByCheckboxView;
import br.net.mirante.singular.form.mform.core.SIString;
import br.net.mirante.singular.form.mform.core.STypeString;

public class CaseInputCoreMultiSelectCheckboxPackage extends SPackage {

    //@formatter:off
    @Override
    protected void carregarDefinicoes(PackageBuilder pb) {

        STypeComposite<?> tipoMyForm = pb.createCompositeType("testForm");

        STypeString tipoContato = pb.createType("tipoContato", STypeString.class)
                 .withSelectionOf("Endereço", "Email", "Telefone", "Celular", "Fax");

        STypeList<STypeString, SIString> infoPub = tipoMyForm.addFieldListOf("infoPub", tipoContato);

        infoPub
            .withView(SMultiSelectionByCheckboxView::new)
            .as(AtrBasic::new).label("Informações Públicas");
    }
}

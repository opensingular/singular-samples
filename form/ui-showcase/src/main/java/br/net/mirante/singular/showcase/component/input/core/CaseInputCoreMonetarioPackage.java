package br.net.mirante.singular.showcase.component.input.core;

import br.net.mirante.singular.form.mform.MPacote;
import br.net.mirante.singular.form.mform.MTipoComposto;
import br.net.mirante.singular.form.mform.PacoteBuilder;
import br.net.mirante.singular.form.mform.basic.ui.AtrBasic;

public class CaseInputCoreMonetarioPackage extends MPacote {

    @Override
    protected void carregarDefinicoes(PacoteBuilder pb) {
        MTipoComposto<?> tipoMyForm = pb.createTipoComposto("testForm");

        tipoMyForm.addCampoMonetario("monetario")
                .as(AtrBasic.class).label("Monetário default");

        tipoMyForm.addCampoMonetario("monetarioLongo")
                .as(AtrBasic.class).label("Monetário com 15 inteiros e 3 decimais")
                .tamanhoInteiroMaximo(15)
                .tamanhoDecimalMaximo(3);

        super.carregarDefinicoes(pb);
    }

}
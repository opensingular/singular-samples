package br.net.mirante.singular.showcase.component.validation;

import br.net.mirante.singular.form.mform.MPacote;
import br.net.mirante.singular.form.mform.MTipoComposto;
import br.net.mirante.singular.form.mform.PacoteBuilder;
import br.net.mirante.singular.form.mform.basic.ui.AtrBasic;
import br.net.mirante.singular.form.mform.core.AtrCore;
import br.net.mirante.singular.form.mform.core.MTipoInteger;

public class CaseValidationCustomPackage extends MPacote {

    @Override
    protected void carregarDefinicoes(PacoteBuilder pb) {

        MTipoComposto<?> tipoMyForm = pb.createTipoComposto("testForm");
        MTipoInteger mTipoInteger = tipoMyForm.addCampoInteger("qtd");
        mTipoInteger.as(AtrBasic::new).label("Quantidade");
        mTipoInteger.as(AtrCore::new).obrigatorio();
        mTipoInteger.addInstanceValidator(validatable -> {
            if(validatable.getInstance().getInteger() > 1000){
                validatable.error("O Campo deve ser menor que 1000");
            }
        });

    }
}

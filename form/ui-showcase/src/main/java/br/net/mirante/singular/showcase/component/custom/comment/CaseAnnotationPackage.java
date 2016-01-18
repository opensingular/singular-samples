package br.net.mirante.singular.showcase.component.custom.comment;

import br.net.mirante.singular.form.mform.MIComposto;
import br.net.mirante.singular.form.mform.MPacote;
import br.net.mirante.singular.form.mform.MTipoComposto;
import br.net.mirante.singular.form.mform.PacoteBuilder;
import br.net.mirante.singular.form.mform.basic.ui.AtrBasic;
import br.net.mirante.singular.form.mform.basic.view.MAnnotationView;
import br.net.mirante.singular.form.mform.core.MTipoString;
import br.net.mirante.singular.form.mform.util.comuns.MTipoCPF;

public class CaseAnnotationPackage extends MPacote {

    @Override
    protected void carregarDefinicoes(PacoteBuilder pb) {
        MTipoComposto<?> targetForm = pb.createTipoComposto("testForm");
        targetForm.as(AtrBasic::new).label("Pedido");

        MTipoComposto<MIComposto> cliente = targetForm.addCampoComposto("Cliente");
        cliente.asAtrBasic().label("Dados do Cliente");
        cliente.addCampoCPF("cpf").as(AtrBasic.class).label("CPF");
        cliente.addCampoEmail("email").as(AtrBasic.class).label("E-Mail");
        //@destacar
        cliente.setView(MAnnotationView::new);

        MTipoComposto<MIComposto> endereco = targetForm.addCampoComposto("Endereco");
        endereco.asAtrBasic().label("Endereco do Cliente");
        endereco.addCampoCEP("cep").as(AtrBasic.class).label("CEP");
        endereco.addCampoCEP("Logradouro").as(AtrBasic.class).label("Logradouro");

        MTipoComposto<MIComposto> request = targetForm.addCampoComposto("request");
        request.asAtrBasic().label("Pedido");
        request.addCampoString("itens").asAtrBasic().label("Itens");
        request.addCampoString("obs").asAtrBasic().label("Observações");

        //@destacar
        request.setView(MAnnotationView::new);
        super.carregarDefinicoes(pb);
    }
}

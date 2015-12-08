package br.net.mirante.singular.showcase.layout;

import br.net.mirante.singular.form.mform.*;
import br.net.mirante.singular.form.mform.basic.ui.AtrBasic;
import br.net.mirante.singular.form.mform.basic.view.MTableListaView;
import br.net.mirante.singular.form.mform.core.MTipoData;
import br.net.mirante.singular.form.mform.core.MTipoString;
import br.net.mirante.singular.form.mform.util.comuns.MTipoAnoMes;
import br.net.mirante.singular.form.wicket.AtrWicket;

public class CaseGridTablePackage extends MPacote {

    @Override
    protected void carregarDefinicoes(PacoteBuilder pb) {

        MTipoComposto<?> testForm = pb.createTipoComposto("testForm");

        final MTipoLista<MTipoComposto<MIComposto>, MIComposto> certificacoes = testForm.addCampoListaOfComposto("certificacoes", "certificacao");
        final MTipoComposto<?> certificacao = certificacoes.getTipoElementos();
        final MTipoAnoMes dataCertificacao = certificacao.addCampo("data", MTipoAnoMes.class, true);
        final MTipoString entidadeCertificacao = certificacao.addCampoString("entidade", true);
        final MTipoData validadeCertificacao = certificacao.addCampoData("validade");
        final MTipoString nomeCertificacao = certificacao.addCampoString("nome", true);
        {
            certificacoes
                    .withView(MTableListaView::new)
                    .as(AtrBasic::new).label("Certificações").tamanhoInicial(3);
            certificacao
                    .as(AtrBasic::new).label("Certificação");
            dataCertificacao
                    .as(AtrBasic::new).label("Data")
                    .as(AtrWicket::new).larguraPref(2);
            entidadeCertificacao
                    .as(AtrBasic::new).label("Entidade")
                    .as(AtrWicket::new).larguraPref(10);
            validadeCertificacao
                    .as(AtrBasic::new).label("Validade")
                    .as(AtrWicket::new).larguraPref(2);
            nomeCertificacao
                    .as(AtrBasic::new).label("Nome")
                    .as(AtrWicket::new).larguraPref(10);
        }
    }
}
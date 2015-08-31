package br.net.mirante.singular.view.page.form;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

import br.net.mirante.singular.form.mform.MDicionario;
import br.net.mirante.singular.form.mform.MIComposto;
import br.net.mirante.singular.form.mform.MTipoComposto;
import br.net.mirante.singular.form.mform.PacoteBuilder;
import br.net.mirante.singular.form.mform.basic.ui.MPacoteBasic;
import br.net.mirante.singular.form.wicket.UIBuilderWicket;
import br.net.mirante.singular.form.wicket.WicketBuildContext;
import br.net.mirante.singular.util.wicket.feedback.BSFeedbackPanel;
import br.net.mirante.singular.view.SingularWicketContainer;
import br.net.mirante.singular.view.template.Content;

public class FormContent extends Content implements SingularWicketContainer<FormContent, Void> {

    public FormContent(String id, boolean withSideBar) {
        super(id, false, withSideBar, true);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        MDicionario dicionario = MDicionario.create();
        PacoteBuilder pb = dicionario.criarNovoPacote("teste");

        MTipoComposto<? extends MIComposto> tipoEndereco = pb.createTipoComposto("endereco");
        tipoEndereco.addCampoString("logradouro").as(MPacoteBasic.aspect()).label("Logradouro");
        tipoEndereco.addCampoInteger("numero").as(MPacoteBasic.aspect()).label("Número");
        tipoEndereco.addCampoString("complemento").as(MPacoteBasic.aspect()).label("Complemento");
        tipoEndereco.addCampoString("cidade").as(MPacoteBasic.aspect()).label("Cidade");
        tipoEndereco.addCampoString("uf").as(MPacoteBasic.aspect()).label("UF");
        tipoEndereco.addCampoInteger("cep").as(MPacoteBasic.aspect()).label("CEP");

        IModel<MIComposto> mEndereco = new LoadableDetachableModel<MIComposto>() {
            @Override
            @SuppressWarnings("unchecked")
            protected MIComposto load() {

                MTipoComposto<? extends MIComposto> tipoEndereco =
                    (MTipoComposto<? extends MIComposto>) dicionario.getTipo("teste.endereco");
                MIComposto iEndereco = tipoEndereco.novaInstancia();
                iEndereco.setValor("logradouro", "QNA 44");
                iEndereco.setValor("numero", 17);
                iEndereco.setValor("complemento", "Taguatinga");
                iEndereco.setValor("cidade", "Brasília");
                iEndereco.setValor("uf", "DF");
                iEndereco.setValor("cep", "72110440");

                return iEndereco;
            }
        };

        WicketBuildContext ctx = new WicketBuildContext();

        add(new BSFeedbackPanel("feedback"));
        add(new Form<>("form")
            .add(UIBuilderWicket.createForEdit("generated", ctx, mEndereco))
            .add(new Button("enviar") {
                @Override
                public void onSubmit() {
                    MIComposto iEndereco = mEndereco.getObject();
                    iEndereco.debug();
                    info(iEndereco.getValorString("logradouro"));
                    info(iEndereco.getValorString("numero"));
                    info(iEndereco.getValorString("complemento"));
                    info(iEndereco.getValorString("cidade"));
                    info(iEndereco.getValorString("uf"));
                    info(iEndereco.getValorString("cep"));
                }
            }));
    }

    @Override
    protected String getContentTitlelKey() {
        return "label.content.title";
    }

    @Override
    protected String getContentSubtitlelKey() {
        return "label.content.subtitle";
    }
}

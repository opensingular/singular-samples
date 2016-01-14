package br.net.mirante.singular.showcase.view.page.form.examples.canabidiol;

import br.net.mirante.singular.form.mform.MIComposto;
import br.net.mirante.singular.form.mform.MILista;
import br.net.mirante.singular.form.mform.MInfoTipo;
import br.net.mirante.singular.form.mform.MInstancia;
import br.net.mirante.singular.form.mform.MTipoComposto;
import br.net.mirante.singular.form.mform.TipoBuilder;
import br.net.mirante.singular.form.mform.basic.ui.AtrBasic;
import br.net.mirante.singular.form.mform.options.MSelectionableInstance;
import br.net.mirante.singular.form.wicket.AtrBootstrap;
import br.net.mirante.singular.showcase.view.page.form.examples.SelectBuilder;
import org.apache.wicket.util.string.Strings;

@MInfoTipo(nome = "MTipoDocumentoSelect", pacote = MPacotePeticaoCanabidiol.class)
public class MTipoDocumentoSelect extends MTipoComposto<MIComposto> {

    @Override
    protected void onCargaTipo(TipoBuilder tb) {
        super.onCargaTipo(tb);

        //ruim: Para adicionar selection não é possível adicionar atributos
        //ruim: esse metodo deveria estar disponivel apenas para tipo composto.
        this.withSelection()
                .add("55358721", Strings.capitalize("carteira de identidade (RG) expedida pela Secretaria de Segurança Pública de um dos estados da Federação ou Distrito Federal"))
                .add("55358722", Strings.capitalize("cartão de identidade expedido por ministério ou órgão subordinado à Presidência da República, incluindo o Ministério da Defesa e os Comandos da Aeronáutica, da Marinha e do Exército"))
                .add("55358723", Strings.capitalize("cartão de identidade expedido pelo poder judiciário ou legislativo, no nível federal ou estadual"))
                .add("55358724", Strings.capitalize("carteira nacional de habilitação (modelo com fotografia)"))
                .add("55358725", Strings.capitalize("carteira de trabalho"))
                .add("55358726", Strings.capitalize("carteira de identidade emitida por conselho ou federação de categoria profissional, com fotografia e fé pública em todo território nacional"))
                .add("55358727", Strings.capitalize("certidão de nascimento"))
                .add("55358728", Strings.capitalize("passaporte nacional"))
                .add("55358729", Strings.capitalize("outro documento de identificação com fotografia e fé pública"));

    }
}
package br.net.mirante.singular.exemplos.notificacaosimplificada.form;

import br.net.mirante.singular.exemplos.notificacaosimplificada.form.vocabulario.STypeFarmacopeia;
import br.net.mirante.singular.exemplos.notificacaosimplificada.service.DominioService;
import org.opensingular.singular.form.SIComposite;
import org.opensingular.singular.form.SInfoType;
import org.opensingular.singular.form.SInstance;
import org.opensingular.singular.form.STypeComposite;
import org.opensingular.singular.form.TypeBuilder;

@SInfoType(spackage = SPackageNotificacaoSimplificada.class)
public class STypeFarmacopeiaReferencia extends STypeComposite<SIComposite> {


    static DominioService dominioService(SInstance ins) {
        return ins.getDocument().lookupService(DominioService.class);
    }

    @Override
    protected void onLoadType(TypeBuilder tb) {

        this.addField("farmacopeia", STypeFarmacopeia.class);

        this.addFieldString("edicao")
                .asAtr().label("Edição")
                .asAtrBootstrap().colPreference(2);
        this.addFieldString("pagina")
                .asAtr().label("Página")
                .asAtrBootstrap().colPreference(2);
    }
}

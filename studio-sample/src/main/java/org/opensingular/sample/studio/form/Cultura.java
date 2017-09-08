package org.opensingular.sample.studio.form;

import org.opensingular.form.SIComposite;
import org.opensingular.form.SInfoType;
import org.opensingular.form.STypeComposite;
import org.opensingular.form.TypeBuilder;
import org.opensingular.form.type.core.STypeString;

import javax.annotation.Nonnull;

@SInfoType(name = "Cultura", spackage = ResiduoPackage.class)
public class Cultura extends STypeComposite<SIComposite> {

    public STypeString nome;

    @Override
    protected void onLoadType(@Nonnull TypeBuilder tb) {
        nome = addField("nome", STypeString.class);
        nome.asAtr().label("Nome da cultura").asAtrBootstrap().colPreference(12);
        nome.asAtr().required();
        this.asSQL()
                .table("TD_CULTURA")
                .tablePK("CO_SEQ_CULTURA");
        nome.asSQL()
                .column("NO_CULTURA");
    }
}

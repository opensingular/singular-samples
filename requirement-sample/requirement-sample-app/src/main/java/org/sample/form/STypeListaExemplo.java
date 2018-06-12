package org.sample.form;

import javax.annotation.Nonnull;

import org.opensingular.form.SIComposite;
import org.opensingular.form.SInfoType;
import org.opensingular.form.STypeComposite;
import org.opensingular.form.TypeBuilder;
import org.opensingular.form.type.core.STypeString;
import org.opensingular.lib.commons.util.Loggable;

@SInfoType(name = "ListaExemplo", spackage = RequirementsamplePackage.class)
public class STypeListaExemplo extends STypeComposite<SIComposite> implements Loggable {

    public STypeString nome2;
    public STypeString  sobrenome2;
    public STypeString  nomeMae2;
    public STypeString  nomeGato2;
    public STypeString  nomeDog2;

    @Override
    protected void onLoadType(@Nonnull TypeBuilder tb) {
        nome2 = this.addFieldString("nome2");
        nome2.asAtr().label("Nome");
        nomeGato2 = this.addFieldString("nomeGato2");
        nomeGato2.asAtr().label("Nome Gato");
        nomeDog2 = this.addFieldString("nomeDog2");
        nomeDog2.asAtr().label("Nome Dog");

        nome2.asAtrAnnotation().setAnnotated();

        sobrenome2 = this.addFieldString("sobrenome2");
        sobrenome2.asAtr().label("Sobrenome");
        sobrenome2.asAtrAnnotation().setAnnotated();
        nomeMae2 = this.addFieldString("nomeMae2");
        nomeMae2.asAtr().label("Nome Mãe");

        nomeMae2.asAtrAnnotation().setAnnotated();

    }
}
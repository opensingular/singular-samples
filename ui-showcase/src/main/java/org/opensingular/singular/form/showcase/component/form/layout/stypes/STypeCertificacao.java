/*
 * Copyright (C) 2016 Singular Studios (a.k.a Atom Tecnologia) - www.opensingular.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.opensingular.singular.form.showcase.component.form.layout.stypes;


import org.opensingular.form.SIComposite;
import org.opensingular.form.SInfoType;
import org.opensingular.form.STypeComposite;
import org.opensingular.form.TypeBuilder;
import org.opensingular.form.type.core.STypeDate;
import org.opensingular.form.type.core.STypeString;
import org.opensingular.form.type.util.STypeYearMonth;
import org.opensingular.singular.form.showcase.component.form.layout.CaseLayoutPackage;

import javax.annotation.Nonnull;

@SInfoType(spackage = CaseLayoutPackage.class)
public class STypeCertificacao extends STypeComposite<SIComposite> {

    public STypeYearMonth data;
    public STypeString    entidade;
    public STypeDate      validade;
    public STypeString    nome;

    @Override
    protected void onLoadType(@Nonnull TypeBuilder tb) {
        data = this.addField("data", STypeYearMonth.class, true);
        entidade = this.addFieldString("entidade", true);
        validade = this.addFieldDate("validade");
        nome = this.addFieldString("nome", true);

        this
                .asAtr().label("Certificação");

        data
                .asAtr().label("Data")
                .asAtrBootstrap().colPreference(2)
                .asAtrAnnotation().setAnnotated();

        entidade
                .asAtr().label("Entidade")
                .asAtrBootstrap().colPreference(4)
                .asAtrAnnotation().setAnnotated();

        validade
                .asAtr().label("Validade")
                .asAtrBootstrap().colPreference(2)
                .asAtrAnnotation().setAnnotated();

        nome
                .asAtr().label("Nome").help("Ajuda do campo nome")
                .asAtrBootstrap().colPreference(4)
                .asAtrAnnotation().setAnnotated();

    }
}

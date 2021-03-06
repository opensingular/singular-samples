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

package org.opensingular.singular.form.showcase.component.form.help;

import org.opensingular.form.SIComposite;
import org.opensingular.form.SInfoType;
import org.opensingular.form.STypeComposite;
import org.opensingular.form.TypeBuilder;
import org.opensingular.form.type.core.STypeString;
import org.opensingular.form.type.util.STypeYearMonth;

import javax.annotation.Nonnull;

@SInfoType(spackage = CaseHelpPackage.class)
public class STypeExperienciaProfissional extends STypeComposite<SIComposite> {

    public STypeYearMonth dtInicioExperiencia;
    public STypeYearMonth dtFimExperiencia;
    public STypeString empresa;
    public STypeString cargo;
    public STypeString atividades;

    @Override
    protected void onLoadType(@Nonnull TypeBuilder tb) {
        dtInicioExperiencia = this.addField("dtInicioExperiencia", STypeYearMonth.class, true);
        dtFimExperiencia = this.addField("dtFimExperiencia", STypeYearMonth.class);
        empresa = this.addFieldString("empresa", true);
        cargo = this.addFieldString("cargo", true);
        atividades = this.addFieldString("atividades");

        dtInicioExperiencia
                .asAtr().label("Data inicial da experiência profissional")
                .asAtrBootstrap().colPreference(2);

        dtFimExperiencia
                .asAtr().label("Data final da experiência profissional")
                .subtitle("Data final da experiência profissional. Deixe em branco para indicar a permanência atual.")
                //@destacar
                .help("Data final da experiência profissional. Deixe em branco para indicar a permanência atual.")
                .asAtrBootstrap().colPreference(2);

        empresa
                .asAtr().label("Empresa")
                //@destacar
                .help("Nome da empresa e/ou área de atuação")
                .asAtrBootstrap().colPreference(8);

        cargo
                .asAtr().label("Cargo")
                //@destacar
                .help("Cargo(s) desempenhados durante o período");

        atividades
                .withTextAreaView()
                .asAtr().label("Atividades Desenvolvidas")
                //@destacar
                .help("");

        this
                .asAtr().label("Experiência")
                //@destacar
                .help("Experiência profissional");
    }
}

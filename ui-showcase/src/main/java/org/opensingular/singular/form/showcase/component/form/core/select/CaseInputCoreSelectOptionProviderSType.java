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

package org.opensingular.singular.form.showcase.component.form.core.select;

import java.text.DateFormat;

import javax.annotation.Nonnull;

import org.opensingular.form.SIComposite;
import org.opensingular.form.SInfoType;
import org.opensingular.form.STypeComposite;
import org.opensingular.form.STypeList;
import org.opensingular.form.TypeBuilder;
import org.opensingular.form.type.core.STypeFieldRef;
import org.opensingular.form.type.core.STypeString;
import org.opensingular.form.view.SViewListByTable;
import org.opensingular.singular.form.showcase.component.CaseItem;
import org.opensingular.singular.form.showcase.component.Group;
import org.opensingular.singular.form.showcase.component.Resource;
import org.opensingular.singular.form.showcase.component.form.core.CaseInputCorePackage;

@CaseItem(componentName = "Select", subCaseName = "Seleção de dados do próprio formulário", group = Group.INPUT, resources = { @Resource(STypePessoa.class), @Resource(SIPessoa.class) })
@SInfoType(spackage = CaseInputCorePackage.class, name = "SelectOptionProvider")
public class CaseInputCoreSelectOptionProviderSType extends STypeComposite<SIComposite> {

    public STypeList<STypePessoa, SIPessoa> pessoas;
    public STypeFieldRef<SIPessoa>          pessoaSelecionada;
    public STypeString                      detalhes;

    @Override
    protected void onLoadType(@Nonnull TypeBuilder tb) {
        pessoas = this.addFieldListOf("pessoas", STypePessoa.class);
        //@destacar:bloco
        pessoaSelecionada = this.addFieldRef("pessoaSelecionada", STypePessoa.class);
        //@destacar:fim
        detalhes = this.addFieldString("detalhes");

        STypePessoa pessoa = pessoas.getElementsType();

        //@destacar:bloco
        pessoaSelecionada.selectFromField(pessoas)
            .display(pessoa.nome);
        //@destacar:fim

        detalhes.withUpdateListener(ins -> ins.setValue(
            ins.root().find(pessoaSelecionada)
                //@destacar:bloco
                .flatMap(it -> it.findSourceInstance())
                //@destacar:fim
                .map(it -> it.getDataNascimento())
                .map(it -> DateFormat.getDateInstance().format(it))
                .orElse("")));

        pessoas.withView(new SViewListByTable())
            .asAtr().label("Pessoas");

        pessoaSelecionada
            .asAtr().label("Seleção").dependsOn(pessoas, pessoa.nome)
            .asAtrBootstrap().colPreference(6);
        detalhes
            .asAtr().label("Detalhes").enabled(false).dependsOn(pessoaSelecionada, pessoa.dataNascimento)
            .asAtrBootstrap().colPreference(6);
    }
}
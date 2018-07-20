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

package org.opensingular.singular.form.showcase.component.form.core.multiselect;

import org.opensingular.form.SIComposite;
import org.opensingular.form.SInfoType;
import org.opensingular.form.STypeComposite;
import org.opensingular.form.STypeList;
import org.opensingular.form.TypeBuilder;
import org.opensingular.form.type.core.SIString;
import org.opensingular.form.type.core.STypeString;
/*hidden*/import org.opensingular.singular.form.showcase.component.CaseItem;
/*hidden*/import org.opensingular.singular.form.showcase.component.Group;
/*hidden*/import org.opensingular.singular.form.showcase.component.Resource;
import org.opensingular.singular.form.showcase.component.form.core.CaseInputCorePackage;

import javax.annotation.Nonnull;

/**
 * Se a view não for definida, então define o componente dependendo da quantidade de dados.
 */
/*hidden*/@CaseItem(componentName = "Multi Select", subCaseName = "Default", group = Group.INPUT, resources = @Resource(CaseInputCorePackage.class))
@SInfoType(spackage = CaseInputCorePackage.class, name = "MultiSelectDefault")
public class STCaseInputCoreMultiSelectDefault extends STypeComposite<SIComposite> {

    public STypeList<STypeString, SIString> frutas;

    @Override
    protected void onLoadType(@Nonnull TypeBuilder tb) {
        frutas = this.addFieldListOf("frutas", STypeString.class);

        frutas.selectionOf(String.class)
                .selfIdAndDisplay()
                .simpleProviderOf("Amora", "Banana", "Maçã", "Laranja", "Manga", "Melão", "Morango");

        this.asAtr().label("Salada de Frutas");
    }
}
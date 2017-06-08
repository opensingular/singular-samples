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

import org.opensingular.form.SIComposite;
import org.opensingular.form.SInfoType;
import org.opensingular.form.STypeComposite;
import org.opensingular.form.TypeBuilder;
import org.opensingular.form.type.core.STypeString;
import org.opensingular.singular.form.showcase.component.CaseItem;
import org.opensingular.singular.form.showcase.component.Group;
import org.opensingular.singular.form.showcase.component.form.core.CaseInputCorePackage;

import javax.annotation.Nonnull;

/**
 * Se a view não for definida, então define o componente dependendo da quantidade de dados e da obrigatoriedade.
 */
@CaseItem(componentName = "Select", subCaseName = "Default", group = Group.INPUT)
@SInfoType(spackage = CaseInputCorePackage.class, name = "SelectDefault")
public class CaseInputCoreSelectDefaultSType extends STypeComposite<SIComposite> {

    public STypeComposite<?> testForm;

    @Override
    protected void onLoadType(@Nonnull TypeBuilder tb) {
        testForm = this.addFieldComposite("testForm");

        addSelection(testForm, 3, true);
        addSelection(testForm, 3, false);
        addSelection(testForm, 10, false);

        final STypeString favoriteFruit = testForm.addFieldString("favoriteFruit");
        favoriteFruit.withSelectView();
        favoriteFruit.asAtr().label("Fruta Favorita");
        favoriteFruit.selectionOf("Maçã", "Laranja", "Banana", "Goiaba");
    }

    private static void addSelection(STypeComposite<?> tipoMyForm, int sizeOptions, boolean required) {
        STypeString tipoSelection = tipoMyForm.addFieldString("opcoes" + sizeOptions + required);
        tipoSelection.selectionOf(createOptions(sizeOptions));
        tipoSelection.withRequired(required);
        tipoSelection.asAtr().label("Seleção de " + sizeOptions);
    }

    private static String[] createOptions(int sizeOptions) {
        String[] options = new String[sizeOptions];
        for (int i = 1; i <= sizeOptions; i++) {
            options[i - 1] = "Opção " + i;
        }
        return options;
    }
}
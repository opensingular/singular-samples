/*
 * Copyright (c) 2016, Mirante and/or its affiliates. All rights reserved.
 * Mirante PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package br.net.mirante.singular.showcase.component.form.core.multiselect;

import org.opensingular.singular.form.PackageBuilder;
import org.opensingular.singular.form.SPackage;
import org.opensingular.singular.form.STypeComposite;
import org.opensingular.singular.form.type.core.STypeString;
import org.opensingular.singular.form.view.SMultiSelectionByCheckboxView;
import br.net.mirante.singular.showcase.component.CaseItem;
import br.net.mirante.singular.showcase.component.Group;

@CaseItem(componentName = "Multi Select", subCaseName = "Checkbox", group = Group.INPUT)
public class CaseInputCoreMultiSelectCheckboxPackage extends SPackage {

    @Override
    protected void onLoadPackage(PackageBuilder pb) {
        STypeComposite<?> tipoMyForm = pb.createCompositeType("testForm");
        tipoMyForm.asAtr().label("Salada de Frutas");
        tipoMyForm.addFieldListOf("frutas", STypeString.class).selectionOf(String.class, new SMultiSelectionByCheckboxView())
                .selfIdAndDisplay()
                .simpleProviderOf("Amora", "Banana", "Maçã", "Laranja", "Manga", "Melão", "Morango");
    }
}

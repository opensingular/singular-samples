/*
 * Copyright (c) 2016, Singular and/or its affiliates. All rights reserved.
 * Singular PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.opensingular.singular.form.showcase.component.form.core;

import org.opensingular.form.PackageBuilder;
import org.opensingular.form.SPackage;
import org.opensingular.form.STypeComposite;
import org.opensingular.form.type.core.STypeDateTime;
import org.opensingular.singular.form.showcase.component.CaseItem;
import org.opensingular.singular.form.showcase.component.Group;

/**
 * Componente para inserção de data e hora.
 */
@CaseItem(componentName = "Date", subCaseName = "Data e Hora", group = Group.INPUT)
public class CaseInputCoreDateTimePackage extends SPackage {

    @Override
    protected void onLoadPackage(PackageBuilder pb) {
        STypeComposite<?> tipoMyForm = pb.createCompositeType("testForm");
        STypeDateTime inicio = tipoMyForm.addFieldDateTime("inicio");

        inicio.asAtr().label("Início");
        inicio.asAtrBootstrap().colPreference(3);

    }

}
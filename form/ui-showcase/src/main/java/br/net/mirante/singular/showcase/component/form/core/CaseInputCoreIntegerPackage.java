/*
 * Copyright (c) 2016, Mirante and/or its affiliates. All rights reserved.
 * Mirante PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package br.net.mirante.singular.showcase.component.form.core;

import org.opensingular.singular.form.PackageBuilder;
import org.opensingular.singular.form.SPackage;
import org.opensingular.singular.form.STypeComposite;
import org.opensingular.singular.form.type.core.STypeInteger;
import br.net.mirante.singular.showcase.component.CaseItem;
import br.net.mirante.singular.showcase.component.Group;

/**
 * Campo para edição de dados inteiro
 */
@CaseItem(componentName = "Numeric", subCaseName = "Integer", group = Group.INPUT)
public class CaseInputCoreIntegerPackage extends SPackage {

    @Override
    protected void onLoadPackage(PackageBuilder pb) {

        STypeComposite<?> tipoMyForm = pb.createCompositeType("testForm");
        STypeInteger mTipoInteger = tipoMyForm.addFieldInteger("qtd");
        mTipoInteger.asAtr().label("Quantidade");

    }
}

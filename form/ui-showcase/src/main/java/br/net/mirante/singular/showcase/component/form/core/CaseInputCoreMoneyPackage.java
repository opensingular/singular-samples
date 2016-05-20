/*
 * Copyright (c) 2016, Mirante and/or its affiliates. All rights reserved.
 * Mirante PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package br.net.mirante.singular.showcase.component.form.core;

import br.net.mirante.singular.form.PackageBuilder;
import br.net.mirante.singular.form.SPackage;
import br.net.mirante.singular.form.STypeComposite;
import br.net.mirante.singular.form.type.basic.AtrBasic;
import br.net.mirante.singular.showcase.component.CaseItem;
import br.net.mirante.singular.showcase.component.Group;

/**
 * Campo para inserção de dados monetários.
 */
@CaseItem(componentName = "Numeric", subCaseName = "Monetário", group = Group.INPUT)
public class CaseInputCoreMoneyPackage extends SPackage {

    @Override
    protected void carregarDefinicoes(PackageBuilder pb) {
        STypeComposite<?> tipoMyForm = pb.createCompositeType("testForm");

        tipoMyForm.addFieldMonetary("monetario")
                .as(AtrBasic.class).label("Monetário default");

        tipoMyForm.addFieldMonetary("monetarioLongo")
                .as(AtrBasic.class).label("Monetário com 15 inteiros e 3 decimais")
                .tamanhoInteiroMaximo(15)
                .tamanhoDecimalMaximo(3);

        super.carregarDefinicoes(pb);
    }

}
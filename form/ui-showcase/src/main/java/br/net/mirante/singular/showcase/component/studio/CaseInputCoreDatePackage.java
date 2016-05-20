/*
 * Copyright (c) 2016, Mirante and/or its affiliates. All rights reserved.
 * Mirante PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package br.net.mirante.singular.showcase.component.studio;

import br.net.mirante.singular.form.PackageBuilder;
import br.net.mirante.singular.form.SPackage;
import br.net.mirante.singular.form.STypeComposite;
import br.net.mirante.singular.form.type.basic.AtrBasic;
import br.net.mirante.singular.form.type.core.STypeTime;
import br.net.mirante.singular.showcase.component.CaseItem;
import br.net.mirante.singular.showcase.component.Group;

/**
 * Apenas para teste do tipo STUDIO
 */
@CaseItem(componentName = "Teste", group = Group.STUDIO)
public class CaseInputCoreDatePackage extends SPackage {

    @Override
    protected void carregarDefinicoes(PackageBuilder pb) {
        STypeComposite<?> tipoMyForm = pb.createCompositeType("testForm");
        tipoMyForm.addFieldDate("inicioDia")
                  .as(AtrBasic.class).label("Data Início")
                  .asAtrBootstrap().colPreference(2);
        tipoMyForm.addField("inicioHora", STypeTime.class)
                .as(AtrBasic.class).label("Hora Início")
                .asAtrBootstrap().colPreference(2);
    }

}

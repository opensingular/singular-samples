/*
 * Copyright (c) 2016, Mirante and/or its affiliates. All rights reserved.
 * Mirante PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package br.net.mirante.singular.showcase.component.form.core.multiselect;

import org.opensingular.singular.form.PackageBuilder;
import org.opensingular.singular.form.SIComposite;
import org.opensingular.singular.form.SPackage;
import org.opensingular.singular.form.STypeComposite;
import org.opensingular.singular.form.STypeList;
import org.opensingular.singular.form.type.core.STypeString;
import org.opensingular.singular.form.view.SMultiSelectionByPicklistView;
import br.net.mirante.singular.showcase.component.CaseItem;
import br.net.mirante.singular.showcase.component.Group;

/**
 * É permitido alterar o provedor de dados de forma que estes sejam carregados de forma dinâmica ou de outras fontes de informação.
 */
@CaseItem(componentName = "Multi Select", subCaseName = "Provedor Dinâmico", group = Group.INPUT)
public class CaseInputCoreMultiSelectProviderPackage extends SPackage {

    @Override
    protected void onLoadPackage(PackageBuilder pb) {
        final STypeComposite<?> tipoMyForm = pb.createCompositeType("testForm");

        final STypeList<STypeComposite<SIComposite>, SIComposite> arquivos = tipoMyForm.addFieldListOfComposite("arquivos", "arquivo");

        /*
         * Neste caso será utilizado o serviço de nome filesChoiceProvider
         * cadastrado através do Document.bindLocalService
         */
        final STypeComposite<SIComposite> arquivo  = arquivos.getElementsType();
        final STypeString                 id       = arquivo.addFieldString("id");
        final STypeString                 hashSha1 = arquivo.addFieldString("hashSha1");

        arquivos.asAtr().label("Seleção de Arquivos Persistidos");

        arquivos.selection()
                .id(id)
                .display(hashSha1)
                .simpleProvider("filesChoiceProvider");
        arquivos.withView(SMultiSelectionByPicklistView::new);

    }

}
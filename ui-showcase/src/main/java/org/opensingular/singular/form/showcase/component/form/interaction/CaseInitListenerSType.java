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

package org.opensingular.singular.form.showcase.component.form.interaction;

import org.opensingular.form.SIComposite;
import org.opensingular.form.SIList;
import org.opensingular.form.SInfoType;
import org.opensingular.form.SInstance;
import org.opensingular.form.STypeComposite;
import org.opensingular.form.STypeList;
import org.opensingular.form.TypeBuilder;
import org.opensingular.form.type.core.STypeInteger;
import org.opensingular.form.type.core.STypeString;
import org.opensingular.form.view.SViewListByForm;
import org.opensingular.singular.form.showcase.component.CaseItem;
import org.opensingular.singular.form.showcase.component.Group;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Optional;

/**
 * Listener que é executado ao criar uma nova instância de um tipo
 */
@CaseItem(componentName = "Listeners", subCaseName = "Init Listener", group = Group.INTERACTION)
@SInfoType(spackage = CaseInteractionPackage.class, name = "InitListener")
public class CaseInitListenerSType extends STypeComposite<SIComposite> {

    public STypeList<STypeComposite<SIComposite>, SIComposite> itens;
    public STypeString                                         nome;

    @Override
    protected void onLoadType(@Nonnull TypeBuilder tb) {
        itens = this.addFieldListOfComposite("itens", "itenm");
        itens.asAtr().label("Itens");
        itens.withView(new SViewListByForm().disableDelete().disableNew());

        final STypeComposite<SIComposite> item = itens.getElementsType();
        nome = item.addFieldString("nome");
        nome
            .asAtr().label("Nome").enabled(false)
            .asAtrBootstrap().colPreference(3);

        final STypeInteger quantidade = item.addFieldInteger("quantidade");
        quantidade
                .asAtr().label("Quantidade")
                .asAtrBootstrap().colPreference(2);

        //@destacar
        this.withInitListener(this::initForm);

    }

    private void initForm(SInstance instance) {
        for (String n : Arrays.asList("Mauro", "Laura")) {
            final Optional<SIList<SIComposite>> itensList = instance.findNearest(itens);
            itensList.ifPresent(il -> initItem(il, n));
        }
    }

    private void initItem(SIList<SIComposite> list, String nomeItem) {
        final SIComposite item = list.addNew();
        item.findNearest(nome)
                .ifPresent(n -> n.setValue(nomeItem));
    }
}
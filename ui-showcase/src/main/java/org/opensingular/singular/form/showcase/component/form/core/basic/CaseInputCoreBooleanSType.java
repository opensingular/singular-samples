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

package org.opensingular.singular.form.showcase.component.form.core.basic;

import org.opensingular.form.SIComposite;
import org.opensingular.form.SInfoType;
import org.opensingular.form.STypeComposite;
import org.opensingular.form.TypeBuilder;
import org.opensingular.form.type.core.STypeBoolean;
import org.opensingular.form.view.SViewCheckBox;
import org.opensingular.lib.commons.ui.Alignment;
import org.opensingular.singular.form.showcase.component.CaseItem;
import org.opensingular.singular.form.showcase.component.Group;
import org.opensingular.singular.form.showcase.component.Resource;
import org.opensingular.singular.form.showcase.component.form.core.CaseInputCorePackage;

import javax.annotation.Nonnull;

/**
 * Campo para inserção de dados booleanos.
 */
//@formatter:off
@CaseItem(componentName = "Boolean", group = Group.INPUT, resources = @Resource(CaseInputCorePackage.class))
@SInfoType(spackage = CaseInputCorePackage.class, name = "Boolean")
public class CaseInputCoreBooleanSType extends STypeComposite<SIComposite> {

    public STypeBoolean aceitaTermos;
    public STypeBoolean receberNotificacoes;
    public STypeBoolean receberNotificacoes2;
    public STypeBoolean aceitaTermos2;

    @Override
    protected void onLoadType(@Nonnull TypeBuilder tb) {
        aceitaTermos = this.addFieldBoolean("aceitaTermos");
        aceitaTermos2 = this.addFieldBoolean("aceitaTermos2");
        receberNotificacoes = this.addFieldBoolean("receberNotificacoes");
        receberNotificacoes2 = this.addFieldBoolean("receberNotificacoes2");

        aceitaTermos
                .asAtr().label("Aceito os termos e condições").required(true);

        aceitaTermos2
                //@destacar
                .withRadioView("Aceito", "Rejeito")
                .asAtr().label("Aceito os termos e condições");

        receberNotificacoes
                //@destacar
                .withView(new SViewCheckBox().setAlignLabelOfCheckBox(Alignment.LEFT))
                .asAtr().label("Receber notificações").required(true);

        //@destacar
        receberNotificacoes2.withRadioView()
                .asAtr().label("Receber notificações");
    }
}

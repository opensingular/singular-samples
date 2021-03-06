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
import org.opensingular.form.SInfoType;
import org.opensingular.form.STypeComposite;
import org.opensingular.form.TypeBuilder;
import org.opensingular.form.type.core.STypeBoolean;
import org.opensingular.singular.form.showcase.component.CaseItem;
import org.opensingular.singular.form.showcase.component.Group;
import org.opensingular.singular.form.showcase.component.Resource;
import org.opensingular.singular.form.showcase.component.form.interaction.form.STypeRecord;

import javax.annotation.Nonnull;

/**
 * Torna os campos obrigatórios dinamicamente.
 */
@CaseItem(componentName = "Enabled, Visible, Required", subCaseName = "Required", group = Group.INTERACTION,
        resources = {@Resource(STypeRecord.class), @Resource(CaseInteractionPackage.class)})
@SInfoType(spackage = CaseInteractionPackage.class, name = "Required")
public class CaseInteractionRequiredSType extends STypeComposite<SIComposite> {

    public STypeBoolean required;
    public STypeRecord record;

    @Override
    protected void onLoadType(@Nonnull TypeBuilder tb) {
        required = this.addFieldBoolean("required");
        record = this.addField("record", STypeRecord.class);

        required.asAtr().label("Required");

        record.text
                .asAtr().dependsOn(required)
                .required(ins -> ins.findNearestValue(required, Boolean.class).orElse(Boolean.FALSE));

        record.date
                .asAtr().dependsOn(required)
                .required(ins -> ins.findNearestValue(required, Boolean.class).orElse(Boolean.FALSE));
    }
}

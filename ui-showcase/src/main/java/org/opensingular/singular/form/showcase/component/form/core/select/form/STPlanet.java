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

package org.opensingular.singular.form.showcase.component.form.core.select.form;

import org.opensingular.form.SInfoType;
import org.opensingular.form.TypeBuilder;
import org.opensingular.form.type.core.STypeDecimal;
import org.opensingular.form.type.core.STypeInteger;
import org.opensingular.form.type.core.STypeString;
import org.opensingular.form.type.generic.STGenericComposite;
import org.opensingular.singular.form.showcase.component.form.core.CaseInputCorePackage;

import javax.annotation.Nonnull;

@SInfoType(spackage = CaseInputCorePackage.class)
public class STPlanet extends STGenericComposite<SIPlanet> {

    public STypeString name;
    public STypeInteger position;
    public STypeDecimal diameter;

    public STPlanet() {
        super(SIPlanet.class);
    }

    @Override
    protected void onLoadType(@Nonnull TypeBuilder tb) {
        name     = this.addFieldString("name");
        position = this.addFieldInteger("position");
        diameter = this.addFieldDecimal("diameter");
    }
}

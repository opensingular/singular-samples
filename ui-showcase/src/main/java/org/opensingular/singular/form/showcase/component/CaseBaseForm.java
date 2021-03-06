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

package org.opensingular.singular.form.showcase.component;

import org.opensingular.form.SDictionary;
import org.opensingular.form.SFormUtil;
import org.opensingular.form.SType;
import org.opensingular.form.STypeComposite;

import java.util.Optional;


public class CaseBaseForm extends CaseBase<STypeComposite<?>> {

    private transient SType<?> caseType;

    public CaseBaseForm(Class<? extends STypeComposite<?>> caseClass) {
        super(caseClass);
    }

    public CaseBaseForm() {}

    @SuppressWarnings("unchecked")
    private Class<? extends STypeComposite<?>> getSTypeClass() {
        return getCaseClass();
    }

    public String getTypeName() {
        return SFormUtil.getTypeName(getSTypeClass());
    }

    public SType<?> getCaseType() {
        if (caseType == null) {
            caseType = SDictionary.create().getType(getSTypeClass());
        }
        return caseType;
    }

    @Override
    public Optional<ResourceRef> getMainSourceResourceName() {
        return ResourceRef.forSource(getSTypeClass());
    }


    public boolean showValidateButton() {
        return getCaseType().hasAnyValidation();
    }
}
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

package org.opensingular.singular.form.showcase.component.form.core.html;

import org.opensingular.form.SIComposite;
import org.opensingular.form.SInfoType;
import org.opensingular.form.STypeComposite;
import org.opensingular.form.TypeBuilder;
import org.opensingular.form.type.core.STypeHTML;
import org.opensingular.form.view.richtext.SViewByRichText;
import org.opensingular.singular.form.showcase.component.CaseItem;
import org.opensingular.singular.form.showcase.component.Group;
import org.opensingular.singular.form.showcase.component.Resource;
import org.opensingular.singular.form.showcase.component.form.core.CaseInputCorePackage;

import javax.annotation.Nonnull;

/**
 * Permite a formatação de texto utilizando HTML.
 */
@CaseItem(componentName = "HTML", subCaseName = "Editor Rico", group = Group.INPUT, resources = @Resource(CaseInputCorePackage.class))
@SInfoType(spackage = CaseInputCorePackage.class, name = "RichText")
public class CaseInputCoreRichTextSType extends STypeComposite<SIComposite> {

    public STypeHTML parecer;

    @Override
    protected void onLoadType(@Nonnull TypeBuilder tb) {
        parecer = this.addField("parecer", STypeHTML.class);

        //@destacar
        parecer.withView(SViewByRichText::new)
               .asAtr().label("Parecer Técnico");
    }
}
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

package org.opensingular.singular.form.showcase.component.form.importer;

import org.opensingular.form.SIComposite;
import org.opensingular.form.SInfoType;
import org.opensingular.form.STypeComposite;
import org.opensingular.form.TypeBuilder;
import org.opensingular.form.type.core.STypeInteger;
import org.opensingular.form.type.core.STypeString;
import org.opensingular.form.type.util.STypeEMail;
import org.opensingular.singular.form.showcase.component.CaseItem;
import org.opensingular.singular.form.showcase.component.Group;
import org.opensingular.singular.form.showcase.component.Resource;

import javax.annotation.Nonnull;

/**
 * Importador de atributos através de arquivos XML.<br/>
 * Exemplo básico de importação. 
 */
@CaseItem(componentName = "ImporterXML", subCaseName = "Default", group = Group.IMPORTER,
        resources = {@Resource(value = CaseImporterSType.class, extension = "xml"), @Resource(CaseImporterPackage.class)})
@SInfoType(spackage = CaseImporterPackage.class, name = "ImporterXMLDefault")
public class CaseImporterSType extends STypeComposite<SIComposite> {

    public STypeString nome;
    public STypeInteger idade;
    public STypeEMail email;
    public STypeString descricao;

    @Override
    protected void onLoadType(@Nonnull TypeBuilder tb) {
        nome = this.addFieldString("nome");
        idade = this.addFieldInteger("idade");
        email = this.addFieldEmail("email");
        descricao = this.addFieldString("descricao");
    }

}

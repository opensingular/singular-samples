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

package org.opensingular.singular.form.showcase.component.form.layout.composite;

import org.opensingular.form.SIComposite;
import org.opensingular.form.SInfoType;
import org.opensingular.form.STypeComposite;
import org.opensingular.form.TypeBuilder;
import org.opensingular.form.type.country.brazil.STypeAddress;
import org.opensingular.form.view.SViewCompositeModal;
import org.opensingular.form.wicket.enums.AnnotationMode;
import org.opensingular.singular.form.showcase.component.CaseItem;
import org.opensingular.singular.form.showcase.component.Group;
import org.opensingular.singular.form.showcase.component.form.layout.CaseLayoutPackage;

import javax.annotation.Nonnull;

/**
 * Composite Modal with annotations
 */
@CaseItem(componentName = "Composite Modal", subCaseName = "Annotated", group = Group.LAYOUT,
            annotation = AnnotationMode.EDIT)
@SInfoType(spackage = CaseLayoutPackage.class, name = "DefaultAnnotatedCompositeModal")
public class CaseAnnotatedCompositeModal extends STypeComposite<SIComposite> {

    public STypeAddress endereco;
    public STypeAddress enderecoNaoAnotado;

    @Override
    protected void onLoadType(@Nonnull TypeBuilder tb) {
        endereco = this.addField("endereco", STypeAddress.class);
        endereco.asAtr()
                .required()
                .label("Endereço Residencial")
                .asAtrAnnotation()
                .setAnnotated();

        endereco.cep.asAtrAnnotation().setAnnotated();
        endereco.logradouro.asAtrAnnotation().setAnnotated();
        endereco.numero.asAtrAnnotation().setAnnotated();
        endereco.complemento.asAtrAnnotation().setAnnotated();
        endereco.bairro.asAtrAnnotation().setAnnotated();
        endereco.cidade.asAtrAnnotation().setAnnotated();
        endereco.estado.asAtrAnnotation().setAnnotated();


        enderecoNaoAnotado = this.addField("enderecoNaoAnotado", STypeAddress.class);
        enderecoNaoAnotado.asAtr()
                .required()
                .label("Endereço Comercial");

        //@destacar:bloco
        endereco.withView(SViewCompositeModal::new);

        enderecoNaoAnotado.withView(SViewCompositeModal::new);
        //@destacar:fim

    }
}
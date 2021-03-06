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

package org.opensingular.singular.form.showcase.component.form.core.search;

import org.opensingular.form.SIComposite;
import org.opensingular.form.SInfoType;
import org.opensingular.form.STypeComposite;
import org.opensingular.form.TypeBuilder;
import org.opensingular.form.view.SViewSearchModal;
import org.opensingular.singular.form.showcase.component.CaseItem;
import org.opensingular.singular.form.showcase.component.Group;
import org.opensingular.singular.form.showcase.component.Resource;
import org.opensingular.singular.form.showcase.component.form.core.CaseInputCorePackage;
import org.opensingular.singular.form.showcase.component.form.core.search.form.Funcionario;
import org.opensingular.singular.form.showcase.component.form.core.search.form.FuncionarioRepository;
import org.opensingular.singular.form.showcase.component.form.core.search.form.LazyFuncionarioProvider;
import org.opensingular.singular.form.showcase.component.form.core.search.form.SIFuncionario;
import org.opensingular.singular.form.showcase.component.form.core.search.form.SIFuncionarioConverter;
import org.opensingular.singular.form.showcase.component.form.core.search.form.STFuncionario;

import javax.annotation.Nonnull;

/**
 * Permite a seleção a partir de uma busca filtrada, sendo necessario fazer o controle de paginação manualmente
 */
@CaseItem(componentName = "Search Select", subCaseName = "Lazy Pagination", group = Group.INPUT,
resources = {@Resource(Funcionario.class), @Resource(STFuncionario.class), @Resource(SIFuncionario.class), @Resource(CaseInputCorePackage.class),
        @Resource(LazyFuncionarioProvider.class), @Resource(FuncionarioRepository.class), @Resource(SIFuncionarioConverter.class)})
@SInfoType(spackage = CaseInputCorePackage.class, name = "LazyPagination")
public class CaseLazyInputModalSearchSType extends STypeComposite<SIComposite> {

    public STFuncionario funcionario;

    @Override
    protected void onLoadType(@Nonnull TypeBuilder tb) {
        funcionario = this.addField("funcionario", STFuncionario.class);

        funcionario.asAtr().label("Funcionario").displayString("${nome} - ${funcao}");
        funcionario.withView(new SViewSearchModal().title("Buscar Profissionais"))
                .asAtrProvider()
                //@destacar
                .filteredProvider(new LazyFuncionarioProvider())
                .converter(new SIFuncionarioConverter());
    }
}
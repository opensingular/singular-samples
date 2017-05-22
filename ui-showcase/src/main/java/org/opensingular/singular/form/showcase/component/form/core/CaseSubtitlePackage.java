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

package org.opensingular.singular.form.showcase.component.form.core;

import org.opensingular.form.PackageBuilder;
import org.opensingular.form.SIComposite;
import org.opensingular.form.SPackage;
import org.opensingular.form.STypeComposite;
import org.opensingular.form.type.core.STypeDecimal;
import org.opensingular.form.type.core.STypeString;
import org.opensingular.form.type.util.STypeEMail;
import org.opensingular.form.view.SViewByBlock;
import org.opensingular.singular.form.showcase.component.CaseItem;
import org.opensingular.singular.form.showcase.component.Group;

/**
 * Subtitle - Exemplo de como utilizar nos agrupadores (StypeComposite) e fields. 
 */
@CaseItem(componentName = "Subtitle", group = Group.INPUT)
public class CaseSubtitlePackage extends SPackage {

    @Override
    protected void onLoadPackage(PackageBuilder pb) {
        STypeComposite<?> testForm = pb.createCompositeType("testForm");

        STypeString nome;
        STypeEMail email;

        STypeString profissao;
        STypeString funcao;

        STypeDecimal redimento;
        STypeDecimal redimentoFamilia;

        
        STypeComposite<SIComposite> dadosPessoais = testForm.addFieldComposite("dadosPessoais");
        dadosPessoais.asAtr().label("Dados Pessoais");
        // @destacar
        dadosPessoais.asAtr().subtitle("Favor preencher todos os dados pessoais.");
        
        nome = dadosPessoais.addFieldString("nome");
        nome.asAtr().label("Nome");

        email = dadosPessoais.addFieldEmail("email");
        email.asAtr().label("E-mail");

        STypeComposite<SIComposite> dadosProfissionais = testForm.addFieldComposite("dadosProfissionais");
        dadosProfissionais.asAtr().label("Dados Profissionais");
        
        profissao = dadosProfissionais.addFieldString("profissao");
        profissao.asAtr().label("Profissão");
        
        funcao = dadosProfissionais.addFieldString("funcao");
        funcao.asAtr().label("Função");

        redimento = dadosProfissionais.addFieldDecimal("rendimento");
        redimento.asAtr().label("Rendimento");
        redimento.asAtrBootstrap().colPreference(6);
        
        redimentoFamilia = dadosProfissionais.addFieldDecimal("redimentoFamilia");
        redimentoFamilia.asAtr().label("Rendimento Familiar");
        // @destacar
        redimentoFamilia.asAtr().subtitle("O somatório de todo redimento da mesma família.");
        redimentoFamilia.asAtrBootstrap().colPreference(6);

        testForm.withView(new SViewByBlock(), v -> v.newBlock("Ficha de Cadastro")
                .add(dadosPessoais)
                .newBlock("Ficha de Cadastro")
                .add(dadosProfissionais)
                );

    }
}
/*
 *
 *  * Copyright (C) 2016 Singular Studios (a.k.a Atom Tecnologia) - www.opensingular.com
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  *  you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package org.opensingular.sample.studio.definition;

import org.opensingular.sample.studio.repository.NormaRepository;
import org.opensingular.studio.core.definition.StudioDefinition;
import org.opensingular.studio.core.definition.StudioTableDefinition;

public class NormaStudioDefinition implements StudioDefinition {
    @Override
    public Class<NormaRepository> getRepositoryClass() {
        return NormaRepository.class;
    }

    @Override
    public void configureStudioDataTable(StudioTableDefinition studioDataTable) {
        studioDataTable.add("Norma", "nome");
    }


    @Override
    public String getTitle() {
        return "Cadastro de Normas";
    }
}
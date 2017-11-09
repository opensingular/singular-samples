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

package org.opensingular.requirementsamplemodule;

import org.opensingular.requirementsamplemodule.flow.RequirementSampleFlow;
import org.sample.form.RequirementsampleForm;
import org.opensingular.server.commons.requirement.SingularRequirement;
import org.opensingular.server.module.RequirementConfiguration;
import org.opensingular.server.module.SingularModule;
import org.opensingular.server.module.WorkspaceConfiguration;
import org.opensingular.server.module.requirement.FormFlowSingularRequirement;
import org.opensingular.server.module.workspace.DefaultDonebox;
import org.opensingular.server.module.workspace.DefaultInbox;
import org.opensingular.server.module.workspace.DefaultDraftbox;
import org.opensingular.server.module.workspace.DefaultOngoingbox;

public class RequirementsampleModule implements SingularModule {

    public static final String REQUIREMENT_SAMPLE = "REQUIREMENTSAMPLE";
    private             SingularRequirement requirementsample = new FormFlowSingularRequirement("Requirementsample", RequirementsampleForm.class, RequirementSampleFlow.class);

    @Override
    public String abbreviation() {
        return REQUIREMENT_SAMPLE;
    }

    @Override
    public String name() {
        return "Módulo Requirementsample";
    }

    @Override
    public void requirements(RequirementConfiguration config) {
        config
                .addRequirement(requirementsample);
    }

    @Override
    public void workspace(WorkspaceConfiguration config) {
        config
                .addBox(new DefaultDraftbox()).newFor(requirementsample)
                .addBox(new DefaultInbox())
                .addBox(new DefaultOngoingbox())
                .addBox(new DefaultDonebox());
    }
}

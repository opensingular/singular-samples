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

package org.opensingular.requirementsamplemodule.report;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.opensingular.form.report.SingularFormReport;
import org.opensingular.lib.wicket.views.SingularReportPanel;
import org.opensingular.requirement.module.wicket.view.template.ServerTemplate;

public abstract class AbstractRelatorioPage extends ServerTemplate {

    public abstract SingularFormReport getSingularFormReport();

    @Override
    protected void onInitialize() {
        super.onInitialize();
        addReportPanel();
    }

    private void addReportPanel() {
        add(new SingularReportPanel("report", this.getSingularFormReport()));
    }

    @Override
    protected IModel<String> getContentTitle() {
        return new Model<>();
    }

    @Override
    protected IModel<String> getContentSubtitle() {
        return new Model<>();
    }

    @Override
    protected boolean isWithMenu() {
        return false;
    }

}
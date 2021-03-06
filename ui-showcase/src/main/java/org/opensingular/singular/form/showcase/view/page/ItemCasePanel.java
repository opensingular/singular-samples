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

package org.opensingular.singular.form.showcase.view.page;

import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.opensingular.form.SInstance;
import org.opensingular.lib.wicket.util.tab.BSTabPanel;
import org.opensingular.singular.form.showcase.component.CaseBase;
import org.opensingular.singular.form.showcase.component.ResourceRef;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.opensingular.lib.wicket.util.util.WicketUtils.$m;

public abstract class ItemCasePanel<T extends CaseBase<?>> extends Panel {

    private static final long serialVersionUID = 3200319871613673285L;

    private final IModel<T> caseBase;

    public ItemCasePanel(String id, IModel<T> caseBase) {
        super(id);
        this.caseBase = caseBase;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        add(buildHeaderText());

        add(buildCodeTabs());
    }

    private WebMarkupContainer buildHeaderText() {

        WebMarkupContainer headerContainer = new WebMarkupContainer("header");
        final Optional<ResourceRef> mainSource = caseBase.getObject().getMainSourceResourceName();
        final SourceCodeProcessor pcf = new SourceCodeProcessor(mainSource.map(ResourceRef::getContent).orElse(""));
        String description = caseBase.getObject().getDescriptionHtml()
                .orElse(pcf.getJavadoc());

        headerContainer.add(new Label("description", $m.ofValue(description)).setEscapeModelStrings(false));
        headerContainer.setVisible(!description.isEmpty());

        return headerContainer;
    }

    private BSTabPanel buildCodeTabs() {

        final BSTabPanel bsTabPanel = new BSTabPanel("codes");
        final List<ResourceRef> sources = new ArrayList<>();
        final Optional<ResourceRef> mainSource = caseBase.getObject().getMainSourceResourceName();

        mainSource.ifPresent(sources::add);

        sources.addAll(caseBase.getObject().getAdditionalSources());

        for (ResourceRef rr : sources) {
            bsTabPanel.addTab(rr.getDisplayName(), new ItemCodePanel(
                    BSTabPanel.TAB_PANEL_ID, $m.ofValue(rr.getContent()), rr.getExtension()));
        }

        return bsTabPanel;
    }

    protected IModel<T> getCaseBase() {
        return caseBase;
    }

    public interface ItemCaseButton extends Serializable {
        AjaxButton buildButton(String id, IModel<? extends SInstance> currentInstance);
    }
}

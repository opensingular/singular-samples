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

package org.opensingular.singular.form.showcase.view.template;

import static org.opensingular.lib.wicket.util.util.WicketUtils.*;

import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.opensingular.lib.wicket.util.metronic.menu.DropdownMenu;
import org.opensingular.singular.form.showcase.component.ShowCaseType;
import org.opensingular.singular.form.showcase.view.page.form.ListPage;
import org.opensingular.singular.form.showcase.view.page.studio.StudioHomePage;
import org.opensingular.singular.form.showcase.view.page.wicket.WicketHomePage;

public class ShowcaseHeader extends Panel {

    private boolean withTogglerButton;

    public ShowcaseHeader(String id) {
        super(id);
        this.withTogglerButton = true;
    }

    public ShowcaseHeader(String id, boolean withTogglerButton) {
        super(id);
        this.withTogglerButton = withTogglerButton;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        add(new WebMarkupContainer("togglerButton")
                .add($b.attrAppender("class", "hide", " ", $m.ofValue(!withTogglerButton))));
        add(new WebMarkupContainer("_TopAction"));
        
        Behavior devMode = $b.visibleIf(()-> Boolean.FALSE);
        
    	DropdownMenu d = buildShowcaseOptions();	
    	add(d.add(devMode));
    	ShowcaseTopMenu showcaseTopMenu = new ShowcaseTopMenu("_TopMenu");
    	add(showcaseTopMenu.add(devMode));
        add(new WebMarkupContainer("brandLogo"));
    }

    private DropdownMenu buildShowcaseOptions() {
        final DropdownMenu dropdownMenu = new DropdownMenu("showcase-options", "Tipo");
        dropdownMenu.adicionarMenu(i -> new Link<String>(i) {
            @Override
            public void onClick() {
                setResponsePage(ListPage.class, ShowCaseType.buildPageParameters(ShowCaseType.FORM));
            }
            @Override
            public IModel<?> getBody() {
                return $m.ofValue("Form");
            }
        });
        dropdownMenu.adicionarMenu(i -> new Link<String>(i) {
            @Override
            public void onClick() {
                setResponsePage(StudioHomePage.class, ShowCaseType.buildPageParameters(ShowCaseType.STUDIO));
            }
            @Override
            public IModel<?> getBody() {
                return $m.ofValue("Studio");
            }
        });
        dropdownMenu.adicionarMenu(i -> new Link<String>(i) {
            @Override
            public void onClick() {
                setResponsePage(WicketHomePage.class, ShowCaseType.buildPageParameters(ShowCaseType.WICKET_UTILS));
            }
            @Override
            public IModel<?> getBody() {
                return $m.ofValue("Wicket");
            }
        });
        return dropdownMenu;
    }
}

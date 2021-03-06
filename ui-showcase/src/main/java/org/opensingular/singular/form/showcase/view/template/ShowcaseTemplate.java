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

import de.alpharogroup.wicket.js.addon.toastr.ToastrType;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.opensingular.lib.wicket.SingularWebResourcesFactory;
import org.opensingular.lib.wicket.util.template.admin.SingularAdminTemplate;
import org.opensingular.lib.wicket.util.toastr.ToastrHelper;
import org.opensingular.singular.form.showcase.component.ShowCaseType;

import javax.annotation.Nonnull;
import javax.inject.Inject;

@AuthorizeAction(action = Action.RENDER, roles = Roles.ADMIN)
public abstract class ShowcaseTemplate extends SingularAdminTemplate {
    private ShowCaseType showCaseType;

    @Inject
    private SingularWebResourcesFactory singularWebResourcesFactory;

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(singularWebResourcesFactory.newCssHeader("plugins/syntaxHighlighter/theme.css"));
        response.render(singularWebResourcesFactory.newJavaScriptHeader("plugins/syntaxHighlighter/syntaxhighlighter.js"));
        response.render(CssHeaderItem.forReference(new PackageResourceReference(ShowcaseTemplate.class, "ShowcaseTemplate.css")));
    }

    public ShowcaseTemplate() {
        this(null, null);
    }

    public ShowcaseTemplate(ShowCaseType showCaseType) {
        this(showCaseType, null);
    }

    public ShowcaseTemplate(PageParameters parameters) {
        this(null, parameters);
    }

    public ShowcaseTemplate(ShowCaseType showCaseType, PageParameters parameters) {
        super(parameters);
        this.showCaseType = showCaseType;
    }

    @Override
    protected boolean isWithMenu() {
        return true;
    }

    @Override
    protected @Nonnull
    WebMarkupContainer buildPageMenu(String id) {
        if (isWithMenu()) {
            return new ShowcaseMenu(id, showCaseType);
        } else {
            return new WebMarkupContainer(id);
        }
    }

    public void addToastrSuccessMessage(String messageKey, String... args) {
        new ToastrHelper(this).addToastrMessage(ToastrType.SUCCESS, messageKey, args);
    }

    public void addToastrErrorMessage(String messageKey, String... args) {
        new ToastrHelper(this).addToastrMessage(ToastrType.ERROR, messageKey, args);
    }

    public void addToastrWarningMessage(String messageKey, String... args) {
        new ToastrHelper(this).addToastrMessage(ToastrType.WARNING, messageKey, args);
    }

    public void addToastrInfoMessage(String messageKey, String... args) {
        new ToastrHelper(this).addToastrMessage(ToastrType.INFO, messageKey, args);
    }

}

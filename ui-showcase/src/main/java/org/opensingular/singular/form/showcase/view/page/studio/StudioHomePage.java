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

package org.opensingular.singular.form.showcase.view.page.studio;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.opensingular.singular.form.showcase.component.ShowCaseType;
import org.opensingular.singular.form.showcase.view.SingularWicketContainer;
import org.opensingular.singular.form.showcase.view.template.ShowcaseTemplate;
import org.wicketstuff.annotation.mount.MountPath;

@MountPath("studio")
public class StudioHomePage extends ShowcaseTemplate implements SingularWicketContainer<StudioHomePage, Void> {
    public StudioHomePage() {
        super(ShowCaseType.STUDIO);
    }

    @Override
    protected IModel<String> getContentTitle() {
        return new ResourceModel("label.content.title", "");
    }

    @Override
    protected IModel<String> getContentSubtitle() {
        return new ResourceModel("label.content.subtitle", "");
    }
    
}
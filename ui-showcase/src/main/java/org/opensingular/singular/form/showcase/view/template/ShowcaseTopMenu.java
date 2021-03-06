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

import static org.opensingular.lib.wicket.util.util.WicketUtils.$b;
import static org.opensingular.lib.wicket.util.util.WicketUtils.$m;

import java.util.Optional;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.StatelessLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.opensingular.lib.commons.base.SingularProperties;
import org.opensingular.singular.form.showcase.wicket.UIAdminSession;

public class ShowcaseTopMenu extends Panel {

    public ShowcaseTopMenu(String id) {
        super(id);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        Label label = new Label("nome", $m.ofValue(UIAdminSession.get().getName()));
        queue(label);

        WebMarkupContainer avatar    = new WebMarkupContainer("codrh");
        Optional<String>   avatarSrc = Optional.ofNullable(UIAdminSession.get().getAvatar());
        avatarSrc.ifPresent(src -> avatar.add($b.attr("src", src)));
        queue(avatar);

        WebMarkupContainer logout     = new WebMarkupContainer("logout");
        Optional<String>   logoutHref = Optional.ofNullable(UIAdminSession.get().getLogout());
        logoutHref.ifPresent(href -> logout.add($b.attr("href", href)));
        queue(logout);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
    }
}

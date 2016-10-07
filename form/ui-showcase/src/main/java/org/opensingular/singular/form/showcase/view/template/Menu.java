/*
 * Copyright (c) 2016, Singular and/or its affiliates. All rights reserved.
 * Singular PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.opensingular.singular.form.showcase.view.template;

import java.util.Collection;

import javax.inject.Inject;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.util.string.StringValue;

import org.opensingular.singular.form.showcase.component.ShowCaseTable;
import org.opensingular.singular.form.showcase.component.ShowCaseType;
import org.opensingular.singular.form.showcase.view.page.ComponentPage;
import org.opensingular.singular.form.showcase.view.page.form.crud.CrudPage;
import org.opensingular.singular.form.showcase.view.page.prototype.PrototypeListPage;
import org.opensingular.singular.form.showcase.view.page.studio.StudioHomePage;
import org.opensingular.lib.wicket.util.menu.MetronicMenu;
import org.opensingular.lib.wicket.util.menu.MetronicMenuGroup;
import org.opensingular.lib.wicket.util.menu.MetronicMenuItem;
import org.opensingular.lib.wicket.util.resource.Icone;

public class Menu extends Panel {

    private static final long serialVersionUID = 7622791136418841943L;

    @Inject
    private ShowCaseTable showCaseTable;

    public Menu(String id) {
        super(id);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        add(buildMenu());
    }

    private MetronicMenu buildMenu() {
        MetronicMenu menu = new MetronicMenu("menu");

        final StringValue tipoValue = getPage().getPageParameters().get(ShowCaseType.SHOWCASE_TYPE_PARAM);

        if (tipoValue.isNull() || ShowCaseType.FORM.toString().equals(tipoValue.toString())) {

            menu.addItem(new MetronicMenuItem(Icone.ROCKET, "Demo", CrudPage.class, ShowCaseType.buildPageParameters(ShowCaseType.FORM)));
            menu.addItem(new MetronicMenuItem(Icone.PENCIL, "Protótipo", PrototypeListPage.class, ShowCaseType.buildPageParameters(ShowCaseType.FORM)));

        } else if (tipoValue.isNull() || ShowCaseType.STUDIO.toString().equals(tipoValue.toString())) {

            menu.addItem(new MetronicMenuItem(Icone.MAP, "Studio", StudioHomePage.class, ShowCaseType.buildPageParameters(ShowCaseType.STUDIO)));

        }

        final Collection<ShowCaseTable.ShowCaseGroup> groups = showCaseTable.getGroups(tipoValue);

        groups.forEach(group -> {
            final MetronicMenuGroup showCaseGroup = new MetronicMenuGroup(group.getIcon(), group.getGroupName());
            final Collection<ShowCaseTable.ShowCaseItem> itens = group.getItens();
            itens.forEach(item -> {

                final String componentName = item.getComponentName();
                showCaseGroup.addItem(
                        new MetronicMenuItem(null, item.getComponentName(), ComponentPage.class,
                                ShowCaseType.buildPageParameters(componentName.toLowerCase())));
            });
            menu.addItem(showCaseGroup);
        });

        return menu;
    }


}
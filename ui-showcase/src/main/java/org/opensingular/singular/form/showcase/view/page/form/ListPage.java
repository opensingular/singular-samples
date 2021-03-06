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

package org.opensingular.singular.form.showcase.view.page.form;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;
import org.opensingular.form.wicket.feedback.SFeedbackPanel;
import org.opensingular.lib.wicket.util.datatable.BSDataTable;
import org.opensingular.lib.wicket.util.datatable.BSDataTableBuilder;
import org.opensingular.lib.wicket.util.datatable.BaseDataProvider;
import org.opensingular.lib.wicket.util.datatable.column.BSActionColumn;
import org.opensingular.lib.wicket.util.resource.DefaultIcons;
import org.opensingular.singular.form.showcase.component.ShowCaseType;
import org.opensingular.singular.form.showcase.dao.form.ShowcaseTypeLoader;
import org.opensingular.singular.form.showcase.view.SingularWicketContainer;
import org.opensingular.singular.form.showcase.view.page.form.crud.CrudPage;
import org.opensingular.singular.form.showcase.view.template.ShowcaseTemplate;
import org.wicketstuff.annotation.mount.MountPath;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.opensingular.lib.wicket.util.util.WicketUtils.$m;

@MountPath("form/list")
@SuppressWarnings("serial")
public class ListPage extends ShowcaseTemplate implements SingularWicketContainer<ListPage, Void> {

    public ListPage(PageParameters parameters) {
        super(parameters);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(OnDomReadyHeaderItem.forScript("$('#_menuItemHome').addClass('active');"));
    }

    private List<FormVO> formTypes;

    @Inject
    @Named("showcaseTypeLoader")
    ShowcaseTypeLoader showcaseTypeLoader;


    private List<FormVO> getFormTypes() {
        if (formTypes == null) {
            formTypes = showcaseTypeLoader.getEntries().stream().filter(this::verificarTipo).map(FormVO::new).collect(Collectors.toList());
        }
        return formTypes;
    }

    private boolean verificarTipo(ShowcaseTypeLoader.TemplateEntry templateEntry) {
        final StringValue tipoValue = getPage().getPageParameters().get(ShowCaseType.SHOWCASE_TYPE_PARAM);
        ShowCaseType tipo;
        if (tipoValue.isNull() || tipoValue.toString().equals(ShowCaseType.FORM.toString())) {
            tipo = ShowCaseType.FORM;
        } else {
            tipo = ShowCaseType.STUDIO;
        }
        return tipo == templateEntry.getTipo();
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        queue(new SFeedbackPanel("feedback", this));
        queue(buildFormDataTable());
    }

    private BSDataTable<FormVO, String> buildFormDataTable() {
        BaseDataProvider<FormVO, String> provider =
                new BaseDataProvider<FormVO, String>() {

                    @Override
                    public long size() {
                        return getFormTypes().size();
                    }

                    @Override
                    public Iterator<? extends FormVO> iterator(int first, int count,
                                                               String sortProperty, boolean ascending) {
                        return getFormTypes().iterator();
                    }
                };

        final BSDataTable<FormVO, String> dataTable = new BSDataTableBuilder<>(provider)
                .appendPropertyColumn(getMessage("label.table.column.form"),
                        "key", FormVO::getKey)
                .appendColumn(new BSActionColumn<FormVO, String>($m.ofValue(""))
                        .appendAction(getMessage("label.table.column.preview"),
                                DefaultIcons.ROCKET, this::goToDemo
                        )
                )
                .setRowsPerPage(Long.MAX_VALUE) //TODO: proper pagination
                .setStripedRows(false)
                .build("form-list");
        return dataTable;
    }

    private void goToDemo(AjaxRequestTarget target, IModel<FormVO> model) {
        FormVO form = model.getObject();
        PageParameters params = new PageParameters()
                .add(CrudPage.TYPE_NAME, form.getTypeName());
        setResponsePage(CrudPage.class, params);
    }

    @Override
    protected IModel<String> getContentSubtitle() {
        return new ResourceModel("label.content.subtitle", "");
    }

    @Override
    protected IModel<String> getContentTitle() {
        return new ResourceModel("label.content.title", "");
    }

}
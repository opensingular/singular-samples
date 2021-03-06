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

package org.opensingular.singular.form.showcase.view.page.prototype;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.util.lang.Bytes;
import org.apache.wicket.util.string.StringValue;
import org.opensingular.form.SDictionary;
import org.opensingular.form.SIComposite;
import org.opensingular.form.SInstance;
import org.opensingular.form.context.SFormConfig;
import org.opensingular.form.document.RefType;
import org.opensingular.form.io.SFormXMLUtil;
import org.opensingular.form.wicket.component.SingularFormWicket;
import org.opensingular.form.wicket.model.SInstanceRootModel;
import org.opensingular.form.wicket.panel.SingularFormPanel;
import org.opensingular.form.wicket.util.WicketFormProcessing;
import org.opensingular.lib.wicket.util.ajax.ActionAjaxButton;
import org.opensingular.singular.form.showcase.dao.form.Prototype;
import org.opensingular.singular.form.showcase.dao.form.PrototypeDAO;
import org.opensingular.singular.form.showcase.view.template.ShowcaseTemplate;
import org.wicketstuff.annotation.mount.MountPath;

import javax.inject.Inject;
import javax.inject.Named;

@MountPath("prototype/edit")
public class PrototypePage extends ShowcaseTemplate {
    protected static String ID = "id";

    public PrototypePage() {
        StringValue idValue = getPageParameters().get(ID);
        if (!idValue.isEmpty()) {
            idPrototype = idValue.toLong();
        }
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(OnDomReadyHeaderItem.forScript("$('#_menuItemPrototype').addClass('active');"));
    }

    private static final SDictionary dictionary = SDictionary.create();

    @Inject
    @Named("formConfigWithDatabase")
    private SFormConfig<String> singularFormConfig;

    @Inject
    private PrototypeDAO prototypeDAO;

    private Long idPrototype;
    protected Prototype prototype;

    static {
        dictionary.loadPackage(SPackagePrototype.class);
    }

    private SInstanceRootModel<SIComposite> model;
    private SingularFormPanel singularFormPanel;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        SingularFormWicket<?> newItemForm = new SingularFormWicket<>("prototype_form");
        newItemForm.setMultiPart(true);
        newItemForm.setFileMaxSize(Bytes.MAX);
        newItemForm.setMaxSize(Bytes.MAX);
        newItemForm.setOutputMarkupId(true);
        queue(buildSingularFormPanel());

        newItemForm.add(new ActionAjaxButton("save-btn") {
            @Override
            protected void onAction(AjaxRequestTarget target, Form<?> form) {
                SIComposite instance = (SIComposite) singularFormPanel.getInstance();
                prototype.setName(instance.getValueString(STypePrototype.NAME_FIELD));
                prototype.setXml(getXmlFromInstance(instance));
                prototypeDAO.save(prototype);
                addToastrSuccessMessage("message.save.success");
            }

            private String getXmlFromInstance(SIComposite instance) {
                return SFormXMLUtil.toStringXML(instance).orElse(null);
            }
        });

        newItemForm.add(new ActionAjaxButton("preview_btn") {
            @Override
            protected void onAction(AjaxRequestTarget target, Form<?> form) {
                if (WicketFormProcessing.onFormSubmit(form, target, singularFormPanel.getInstanceModel(), true)) {
                    setResponsePage(new PreviewPage(model, PrototypePage.this.getPage()));
                } else {
                    addToastrWarningMessage("message.error.form");
                }
            }
        });

        newItemForm.add(new ActionAjaxButton("cancel-btn") {
            @Override
            protected void onAction(AjaxRequestTarget target, Form<?> form) {
                setResponsePage(new PrototypeListPage());
            }
        });
        queue(newItemForm);
    }

    private SingularFormPanel buildSingularFormPanel() {
        singularFormPanel = new SingularFormPanel("singular-panel");
        singularFormPanel.setInstanceCreator(this::createInstance);
        return singularFormPanel;
    }

    private SInstance createInstance() {
        loadOrBuildModel();

        RefType refType = RefType.of(() -> dictionary.getType(STypePrototype.class));
        SIComposite currentInstance = loadOrCreateInstance(refType);
        model = new SInstanceRootModel<>(currentInstance);

        return currentInstance;
    }

    private SIComposite loadOrCreateInstance(RefType refType) {
        String xml = prototype.getXml();
        SInstance instance;
        if (StringUtils.isBlank(xml)) {
            instance = singularFormConfig.getDocumentFactory().createInstance(refType);
        } else {
            instance = SFormXMLUtil.fromXML(refType, xml, singularFormConfig.getDocumentFactory());
        }
        return (SIComposite) instance;
    }

    protected void loadOrBuildModel() {
        if (idPrototype == null) {
            prototype = new Prototype();
        } else {
            prototype = prototypeDAO.findById(idPrototype);
        }
    }

    @Override
    protected IModel<String> getContentTitle() {
        return new ResourceModel("label.content.title", "");
    }

    @Override
    protected IModel<String> getContentSubtitle() {
        return new ResourceModel("label.content.title", "");
    }
}

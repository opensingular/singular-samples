package br.net.mirante.singular.form.wicket.mapper.search;

import br.net.mirante.singular.commons.lambda.IConsumer;
import br.net.mirante.singular.form.mform.*;
import br.net.mirante.singular.form.mform.basic.view.SViewSearchModal;
import br.net.mirante.singular.form.mform.context.SFormConfig;
import br.net.mirante.singular.form.mform.document.RefType;
import br.net.mirante.singular.form.wicket.WicketBuildContext;
import br.net.mirante.singular.form.wicket.panel.SingularFormPanel;
import br.net.mirante.singular.util.wicket.datatable.BSDataTableBuilder;
import br.net.mirante.singular.util.wicket.datatable.BaseDataProvider;
import br.net.mirante.singular.util.wicket.datatable.IBSAction;
import br.net.mirante.singular.util.wicket.datatable.column.BSActionPanel;
import br.net.mirante.singular.util.wicket.resource.Icone;
import org.apache.commons.lang3.text.WordUtils;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

import java.lang.reflect.Method;
import java.util.Iterator;

@SuppressWarnings("unchecked")
class SearchModalContent extends Panel {

    private final WicketBuildContext           ctx;
    private final SViewSearchModal             view;
    private final IConsumer<AjaxRequestTarget> selectCallback;

    private SingularFormPanel innerSingularFormPanel;
    private MarkupContainer   resultTalbe;

    SearchModalContent(String id, WicketBuildContext ctx, IConsumer<AjaxRequestTarget> selectCallback) {
        super(id);
        this.ctx = ctx;
        this.view = (SViewSearchModal) ctx.getView();
        this.selectCallback = selectCallback;
        validate();
    }

    private void validate() {
        if (view.getPagedResultProvider() == null) {
            throw new SingularFormException("O provider não foi informado");
        }
        if (view.getFilterBuilder() == null) {
            throw new SingularFormException("O filter não foi informado");
        }
        if (view.getConverter() == null) {
            throw new SingularFormException("O converter não foi informado");
        }
    }

    @Override
    protected void onInitialize() {

        super.onInitialize();

        final SingularFormPanel parentSingularFormPanel = this.visitParents(SingularFormPanel.class, new IVisitor<SingularFormPanel, SingularFormPanel>() {
            @Override
            public void component(SingularFormPanel parent, IVisit<SingularFormPanel> visit) {
                visit.stop(parent);
            }
        });

        innerSingularFormPanel = new SingularFormPanel("formPanel", parentSingularFormPanel.getSingularFormConfig()) {
            @Override
            protected SInstance createInstance(SFormConfig singularFormConfig) {
                RefType filterRefType = new RefType() {
                    @Override
                    protected SType<?> retrieve() {
                        final STypeComposite<SIComposite> filter = SDictionary.create()
                                .createNewPackage("filterPackage")
                                .createCompositeType("filter");
                        view.getFilterBuilder().accept(filter);
                        return filter;
                    }
                };
                return singularFormConfig.getDocumentFactory().createInstance(filterRefType);
            }
        };

        add(innerSingularFormPanel);

        add(new AjaxButton("filterButton") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                super.onSubmit(target, form);
                target.add(resultTalbe);
            }
        });

        BSDataTableBuilder<Object, ?, ?> builder = new BSDataTableBuilder(new BaseDataProvider() {
            @Override
            public long size() {
                return view.getPagedResultProvider().getSize((SInstance) innerSingularFormPanel.getRootInstance().getObject());
            }

            @Override
            public Iterator iterator(int first, int count, Object sortProperty, boolean ascending) {
                return view.getPagedResultProvider().load((SInstance) innerSingularFormPanel.getRootInstance().getObject(), first, count).iterator();
            }
        });

        builder.setRowsPerPage(view.getPageSize());

        for (SViewSearchModal.Column column : view.getColumns()) {
            builder.appendPropertyColumn(Model.of(column.getLabel()), object -> {
                try {
                    final Method getter = object.getClass().getMethod("get" + WordUtils.capitalize(column.getProperty()));
                    return getter.invoke(object);
                } catch (Exception ex) {
                    throw new SingularFormException("Não foi possivel recuperar a propriedade " + column.getProperty() + " via metodo get na classe " + object.getClass());
                }
            });
        }

        builder.appendActionColumn(Model.of(), (actionColumn) -> actionColumn
                .appendAction(new BSActionPanel.ActionConfig<>().iconeModel(Model.of(Icone.HAND_UP)),
                        (IBSAction<Object>) (target, model) -> {
                            view.getConverter().convert(ctx.getModel().getObject(), model.getObject());
                            selectCallback.accept(target);
                        })
        );

        add(resultTalbe = builder.build("resultTalbe"));
    }

}
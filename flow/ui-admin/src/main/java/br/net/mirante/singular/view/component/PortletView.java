package br.net.mirante.singular.view.component;

import java.util.Set;

import org.apache.wicket.ClassAttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import br.net.mirante.singular.bamclient.portlet.PortletConfig;
import br.net.mirante.singular.bamclient.portlet.PortletFilterContext;
import br.net.mirante.singular.bamclient.portlet.PortletQuickFilter;

public class PortletView<C extends PortletConfig> extends Panel {

    private final IModel<C> config;
    private final IModel<PortletFilterContext> filter = Model.of(new PortletFilterContext());

    public PortletView(String id, C config) {
        super(id);
        this.config = Model.of(config);
    }

    protected ViewResult buildViewResult() {
        return PortletViewConfigResolver.newViewResult("portletContent", config, filter);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        add(buildQuickFilters());
        add(buildViewResult());
        setOutputMarkupId(true);
    }

    private Component buildQuickFilters() {
        return new ListView<PortletQuickFilter>("quickFilterOptions", config.getObject().getQuickFilter()) {
            @Override
            protected void populateItem(ListItem<PortletQuickFilter> item) {
                item.add(new AjaxEventBehavior("click") {
                    @Override
                    protected void onEvent(AjaxRequestTarget target) {
                        target.add(getParent());
                        filter.getObject().setQuickFilter(item.getModelObject());
                    }
                });
                item.add(new Behavior() {
                    @Override
                    public void onConfigure(Component component) {
                        component.add(new ClassAttributeModifier() {
                            @Override
                            protected Set<String> update(Set<String> oldClasses) {
                                if (item.getModelObject().equals(filter.getObject().getQuickFilter())) {
                                    oldClasses.add("active");
                                } else {
                                    oldClasses.remove("active");
                                }
                                return oldClasses;
                            }
                        });
                        super.onConfigure(component);
                    }
                });
                item.setOutputMarkupId(true);
                item.add(new Label("filterLabel", Model.of(item.getModelObject().getLabel())));
            }
        };
    }
}

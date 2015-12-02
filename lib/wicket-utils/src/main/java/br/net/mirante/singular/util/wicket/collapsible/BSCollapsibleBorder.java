package br.net.mirante.singular.util.wicket.collapsible;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.model.IModel;

import static br.net.mirante.singular.util.wicket.util.WicketUtils.$b;

public class BSCollapsibleBorder extends Border {

    private WebMarkupContainer anchor;
    private WebMarkupContainer collapsible;
    private Component parent;
    private boolean expandByDefault;

    public BSCollapsibleBorder(String id, IModel<String> headerText, boolean expandByDefault, Component parent) {
        super(id);
        this.parent = parent;
        this.expandByDefault = expandByDefault;
        addToBorder(buildAnchor(headerText));
        addToBorder(buildCollapisble());
    }

    public BSCollapsibleBorder(String id, IModel<String> headerText, boolean expandByDefault) {
        super(id);
        this.expandByDefault = expandByDefault;
        addToBorder(buildAnchor(headerText));
        addToBorder(buildCollapisble());
    }

    private Component buildCollapisble() {
        collapsible = new WebMarkupContainer("collapsible");
        return collapsible;
    }

    private Component buildAnchor(IModel<String> headerText) {
        anchor = new WebMarkupContainer("anchor");
        anchor.add(new Label("headerText", headerText));
        return anchor;
    }

    @Override
    protected void onConfigure() {
        super.onConfigure();
        addCollapseLogic();
    }

    private void addCollapseLogic() {
        anchor.setOutputMarkupId(true);
        collapsible.setOutputMarkupId(true);

        anchor.add($b.attr("href", "#" + collapsible.getMarkupId()));

        if (expandByDefault) {
            collapsible.add($b.classAppender("in"));
        }

        if (parent != null) {
            parent.setOutputMarkupId(true);
            parent.add($b.attr("aria-multiselectable", "true"));
            anchor.add($b.attr("data-parent", "#" + parent.getMarkupId()));
        }

    }
}

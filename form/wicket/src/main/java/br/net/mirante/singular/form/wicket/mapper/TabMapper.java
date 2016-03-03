package br.net.mirante.singular.form.wicket.mapper;

import java.util.Collection;
import java.util.List;

import br.net.mirante.singular.form.mform.SIComposite;
import br.net.mirante.singular.form.mform.SInstance;
import br.net.mirante.singular.form.mform.STypeComposite;
import br.net.mirante.singular.form.mform.basic.view.MTabView;
import br.net.mirante.singular.form.mform.core.annotation.AtrAnnotation;
import br.net.mirante.singular.form.wicket.WicketBuildContext;
import br.net.mirante.singular.form.wicket.model.SInstanceCampoModel;
import br.net.mirante.singular.form.wicket.panel.BSPanelGrid;
import br.net.mirante.singular.util.wicket.tab.BSTabPanel;
import org.apache.wicket.Component;

import static com.google.common.collect.Lists.newArrayList;

public class TabMapper extends DefaultCompostoMapper {

    @Override
    @SuppressWarnings("unchecked")
    public void buildView(final WicketBuildContext ctx) {

        final SIComposite instance = (SIComposite) ctx.getModel().getObject();
        final STypeComposite<SIComposite> tComposto = (STypeComposite<SIComposite>) instance.getType();
        MTabView tabView = (MTabView) tComposto.getView();

        BSPanelGrid panel = new BSPanelGrid("panel") {
            @Override
            public void updateTab(BSTab tab, List<BSTab> tabs) {
                updateTabIcons(tabs);
                renderTab(tab.getSubtree(), this, ctx);
            }

            private void updateTabIcons(List<BSTab> tabs) {
                for(BSTab t : tabs){
                    t.iconClass(defineTabIconCss(ctx, (SIComposite) ctx.getModel().getObject(),
                            t.getSubtree()));
                }
            }

            public Collection<Component> toUpdadeOnTab(){
                if(ctx.getRootContext().annotation().enabled()){
                    return newArrayList(ctx.updateOnRefresh());
                }
                return newArrayList();
            }
        };

        for (MTabView.MTab tab : tabView.getTabs()) {
            String iconCSS = defineTabIconCss(ctx, instance, tab.getNomesTipo());
            panel.addTab(tab.getId(), tab.getTitulo(), iconCSS, tab.getNomesTipo());
        }

        ctx.getContainer().newTag("div", panel);

        MTabView.MTab tabDefault = tabView.getTabDefault();

        renderTab(tabDefault.getNomesTipo(), panel, ctx);

    }

    public static String defineTabIconCss(WicketBuildContext ctx, SIComposite instance,
                                          List<String> subtree) {
        String iconCSS = "";
        if(ctx.getRootContext().annotation().enabled()){
            for(String name : subtree){
                SInstance field = instance.getCampo(name);
                if(field != null){
                    AtrAnnotation annotatedField = field.as(AtrAnnotation::new);
                    if(annotatedField.hasAnnotationOnTree()){
                        iconCSS = "fa fa-comment";
                        if(annotatedField.hasAnyRefusal()){
                            iconCSS+= " sannotation-color-danger";
                        }else{
                            iconCSS+= " sannotation-color-info";
                        }
                    }else if(ctx.getRootContext().annotation().editable() && annotatedField.isAnnotated()) {
                        iconCSS = "fa fa-comment-o";
                    }
                }

            }
        }
        return iconCSS;
    }

    private void renderTab(List<String> nomesTipo, BSPanelGrid panel, WicketBuildContext ctx) {
        for (String nomeTipo : nomesTipo) {
            final SInstanceCampoModel<SInstance> subtree = new SInstanceCampoModel<>(ctx.getModel(), nomeTipo);
            final WicketBuildContext childContext = ctx.createChild(panel.getContainer().newGrid().newColInRow(), true, subtree);
            childContext.init(ctx.getUiBuilderWicket(), ctx.getViewMode());
            childContext.getUiBuilderWicket().build(childContext, childContext.getViewMode());
            childContext.initContainerBehavior();
        }
    }
}

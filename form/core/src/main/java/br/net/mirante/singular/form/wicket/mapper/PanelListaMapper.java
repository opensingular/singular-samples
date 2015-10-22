package br.net.mirante.singular.form.wicket.mapper;

import static br.net.mirante.singular.util.wicket.util.Shortcuts.*;
import static org.apache.commons.lang3.StringUtils.*;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;

import br.net.mirante.singular.form.mform.MILista;
import br.net.mirante.singular.form.mform.MInstancia;
import br.net.mirante.singular.form.mform.basic.ui.MPacoteBasic;
import br.net.mirante.singular.form.mform.basic.view.MPanelListaView;
import br.net.mirante.singular.form.mform.basic.view.MView;
import br.net.mirante.singular.form.wicket.UIBuilderWicket;
import br.net.mirante.singular.form.wicket.WicketBuildContext;
import br.net.mirante.singular.util.wicket.bootstrap.layout.BSContainer;
import br.net.mirante.singular.util.wicket.bootstrap.layout.BSGrid;
import br.net.mirante.singular.util.wicket.bootstrap.layout.BSRow;
import br.net.mirante.singular.util.wicket.bootstrap.layout.TemplatePanel;

public class PanelListaMapper extends AbstractListaMapper {

    @Override
    @SuppressWarnings("unchecked")
    public void buildView(WicketBuildContext ctx, MView view, IModel<? extends MInstancia> model) {
        final IModel<MILista<MInstancia>> listaModel = $m.get(() -> (MILista<MInstancia>) model.getObject());
        final MILista<?> iLista = listaModel.getObject();
        final IModel<String> label = $m.ofValue(trimToEmpty(iLista.as(MPacoteBasic.aspect()).getLabel()));

        final BSContainer<?> parentCol = ctx.getContainer();

        final TemplatePanel template = parentCol.newTemplateTag(t -> ""
            + "<form wicket:id='_f'>"
            + "  <div class='panel panel-default'>"
            + "    <div wicket:id='_h' class='panel-heading'></div>"
            + "    <ul class='list-group'>"
            + "      <li wicket:id='_e' class='list-group-item'>"
            + "        <div wicket:id='_r'></div>"
            + "      </li>"
            + "    </ul>"
            + "    <div wicket:id='_f' class='panel-footer'></div>"
            + "  </div>"
            + "</form>");
        final Form<?> form = new Form<>("_f");
        final BSContainer<?> heading = new BSContainer<>("_h");
        final ElementsView elementsView = new PanelElementsView("_e", listaModel, ctx, view, form);
        final BSContainer<?> footer = new BSContainer<>("_f");

        form.setOutputMarkupId(true).setOutputMarkupPlaceholderTag(true);

        if (isNotBlank(label.getObject()))
            heading.appendTag("span", new Label("_title", label));

        if ((view instanceof MPanelListaView) && ((MPanelListaView) view).isPermiteAdicaoDeLinha()) {
            AdicionarButton btn = appendAdicionarButton(listaModel, form, footer);
            if (!((MPanelListaView) view).isPermiteInsercaoDeLinha())
                btn.add($b.classAppender("pull-right"));
        } else {
            footer.setVisible(false);
        }

        template
            .add(form
                .add(heading)
                .add(elementsView)
                .add(footer));
    }

    private static final class PanelElementsView extends ElementsView {
        private final WicketBuildContext ctx;
        private final MView              view;
        private final Form<?>            form;
        private PanelElementsView(String id, IModel<MILista<MInstancia>> model, WicketBuildContext ctx, MView view, Form<?> form) {
            super(id, model);
            this.ctx = ctx;
            this.view = view;
            this.form = form;
        }
        @Override
        protected void populateItem(Item<MInstancia> item) {
            final BSGrid grid = new BSGrid("_r");
            final BSRow row = grid.newRow();

            UIBuilderWicket.buildForEdit(ctx.createChild(row.newCol(11), true), item.getModel());

            final BSGrid btnGrid = row.newCol(1).newGrid();

            if ((view instanceof MPanelListaView) && (((MPanelListaView) view).isPermiteInsercaoDeLinha()))
                appendInserirButton(this, form, item, btnGrid.newColInRow())
                    .add($b.classAppender("pull-right"));

            if ((view instanceof MPanelListaView) && ((MPanelListaView) view).isPermiteExclusaoDeLinha())
                appendRemoverButton(this, form, item, btnGrid.newColInRow())
                    .add($b.classAppender("pull-right"));

            item.add(grid);
        }
    }
}
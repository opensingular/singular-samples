/*
 * Copyright (c) 2016, Mirante and/or its affiliates. All rights reserved.
 * Mirante PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package br.net.mirante.singular.form.wicket.mapper;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;

import com.google.common.base.Strings;

import br.net.mirante.singular.form.mform.SInstance;
import br.net.mirante.singular.form.mform.SIList;
import br.net.mirante.singular.form.mform.SType;
import br.net.mirante.singular.form.mform.basic.ui.SPackageBasic;
import br.net.mirante.singular.form.mform.basic.view.SViewListByForm;
import br.net.mirante.singular.form.mform.basic.view.SView;
import br.net.mirante.singular.form.wicket.UIBuilderWicket;
import br.net.mirante.singular.form.wicket.WicketBuildContext;
import br.net.mirante.singular.form.wicket.enums.ViewMode;
import br.net.mirante.singular.form.wicket.mapper.components.MetronicPanel;
import br.net.mirante.singular.util.wicket.bootstrap.layout.BSContainer;
import br.net.mirante.singular.util.wicket.bootstrap.layout.BSGrid;
import br.net.mirante.singular.util.wicket.bootstrap.layout.BSRow;
import br.net.mirante.singular.util.wicket.bootstrap.layout.TemplatePanel;
import static br.net.mirante.singular.util.wicket.util.Shortcuts.$b;
import static br.net.mirante.singular.util.wicket.util.Shortcuts.$m;
import static org.apache.commons.lang3.StringUtils.trimToEmpty;

public class PanelListaMapper extends AbstractListaMapper {

    public void buildView(WicketBuildContext ctx) {

        final IModel<SIList<SInstance>> listaModel = $m.get(ctx::getCurrentInstance);
        final SIList<?> iLista = listaModel.getObject();
        final IModel<String> label = $m.ofValue(trimToEmpty(iLista.as(SPackageBasic.aspect()).getLabel()));
        final SView view = ctx.getView();
        final BSContainer<?> parentCol = ctx.getContainer();
        final ViewMode viewMode = ctx.getViewMode();
        final SType<?> currentType = ctx.getCurrentInstance().getType();

        addMinimumSize(currentType, iLista);

        ctx.configureContainer(label);

        parentCol.appendComponent(id -> MetronicPanel.MetronicPanelBuilder.build(id,
                (heading, form) -> {

                    heading.appendTag("span", new Label("_title", label));
                    heading.add($b.visibleIf($m.get(() -> !Strings.isNullOrEmpty(label.getObject()))));

                    if ((view instanceof SViewListByForm)
                            && ((SViewListByForm) view).isNewEnabled()
                            && viewMode.isEdition()) {
                        appendAddButton(listaModel, form, heading, false);
                    }
                },
                (content, form) -> {

                    TemplatePanel list = content.newTemplateTag(t -> ""
                            + "    <ul class='list-group'>"
                            + "      <li wicket:id='_e' class='list-group-item'>"
                            + "        <div wicket:id='_r'></div>"
                            + "      </li>"
                            + "    </ul>");
                    list.add($b.onConfigure(c -> c.setVisible(!listaModel.getObject().isEmpty())));
                    list.add(new PanelElementsView("_e", listaModel, ctx.getUiBuilderWicket(), ctx, view, form));
                    content.add($b.attrAppender("style", "padding: 15px 15px 10px 15px", ";"));

                },
                (footer, form) -> {
                    footer.setVisible(false);
                }));
    }

    private static final class PanelElementsView extends ElementsView {

        private final SView view;
        private final Form<?> form;
        private final WicketBuildContext ctx;
        private final UIBuilderWicket wicketBuilder;

        private PanelElementsView(String id,
                                  IModel<SIList<SInstance>> model,
                                  UIBuilderWicket wicketBuilder,
                                  WicketBuildContext ctx,
                                  SView view,
                                  Form<?> form) {
            super(id, model);
            this.wicketBuilder = wicketBuilder;
            this.ctx = ctx;
            this.view = view;
            this.form = form;
        }

        @Override
        protected void populateItem(Item<SInstance> item) {
            final BSGrid grid = new BSGrid("_r");
            final BSRow row = grid.newRow();
            final ViewMode viewMode = ctx.getViewMode();

            wicketBuilder.build(ctx.createChild(row.newCol(11), true, item.getModel()), viewMode);

            final BSGrid btnGrid = row.newCol(1).newGrid();

            if ((view instanceof SViewListByForm) && (((SViewListByForm) view).isInsertEnabled())
                    && viewMode.isEdition()) {
                appendInserirButton(this, form, item, btnGrid.newColInRow())
                        .add($b.classAppender("pull-right"));
            }

            if ((view instanceof SViewListByForm) && ((SViewListByForm) view).isDeleteEnabled()
                    && viewMode.isEdition()) {
                appendRemoverButton(this, form, item, btnGrid.newColInRow())
                        .add($b.classAppender("pull-right"));
            }

            item.add(grid);
        }
    }
}

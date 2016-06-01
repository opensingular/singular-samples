package br.net.mirante.singular.studio.wicket;

import br.net.mirante.singular.commons.lambda.IFunction;
import br.net.mirante.singular.form.SInstance;
import br.net.mirante.singular.form.SType;
import br.net.mirante.singular.form.persistence.FormPersistence;
import br.net.mirante.singular.form.wicket.model.MInstanceRootModel;
import br.net.mirante.singular.studio.core.CollectionCanvas;
import br.net.mirante.singular.studio.core.CollectionEditorConfig;
import br.net.mirante.singular.studio.core.CollectionInfo;
import br.net.mirante.singular.studio.persistence.FormPersistenceFactory;
import br.net.mirante.singular.util.wicket.bootstrap.layout.BSContainer;
import br.net.mirante.singular.util.wicket.bootstrap.layout.BSGrid;
import br.net.mirante.singular.util.wicket.bootstrap.layout.IBSComponentFactory;
import br.net.mirante.singular.util.wicket.datatable.BSDataTable;
import br.net.mirante.singular.util.wicket.datatable.BSDataTableBuilder;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import javax.inject.Inject;
import java.util.Iterator;

import static br.net.mirante.singular.util.wicket.util.Shortcuts.$m;

@SuppressWarnings("serial")
public class SingularStudioListPanel extends Panel {

    private final IFunction<Class<SType<?>>, SType<?>> typeLoader;

    @Inject
    private FormPersistenceFactory formPersistenceFactory;

    public SingularStudioListPanel(String id,
                                   IFunction<Class<SType<?>>, SType<?>> typeLoader,
                                   SingularStudioCollectionPanel.PanelControl panelControl,
                                   CollectionCanvas canvas) {
        super(id);
        this.typeLoader = typeLoader;


        BSContainer portletBodyContainer = new BSContainer("portletBodyContainer");
        BSGrid grid = portletBodyContainer.newGrid();
        grid.newRow();
        //TODO adicionar filtro e etc.


        BSContainer listPanelContent = new BSContainer("listPanelContent");
        listPanelContent.appendTag("table", true, " class=\"table table-striped table-hover dataTable table-bordered\" ",
                (IBSComponentFactory<BSDataTable<SInstance, String>>) tableId ->
                        buildDataTable(tableId,
                                canvas.getCollectionInfo(),
                                canvas.getEditorConfigFunction(typeLoader.apply((Class<SType<?>>) canvas.getCollectionInfo().getSTypeClass()))));


        queue(new WebMarkupContainer("portletContainer"));
        queue(portletBodyContainer);
        portletBodyContainer.appendTag("div", true, "class=\"dataTables_wrapper no-footer table-responsive  \"", listPanelContent);


    }


    protected BSDataTable<SInstance, String> buildDataTable(String id, CollectionInfo collectionInfo, CollectionEditorConfig collectionEditorConfig) {
        BSDataTableBuilder<SInstance, String, ?> builder = new BSDataTableBuilder<>(dataProvider(collectionInfo));
        for (Pair<String, String> p : collectionEditorConfig.getColumns()) {
            builder.appendPropertyColumn($m.ofValue(p.getKey()), p.getValue(), SInstance::toStringDisplay);
        }
        builder.setRowsPerPage(10);
        return builder.build(id);
    }

    protected FormPersistence<?> getPersistence(CollectionInfo collectionInfo) {
        return formPersistenceFactory.get(typeLoader.apply((Class<SType<?>>) collectionInfo.getSTypeClass()));
    }

    protected SortableDataProvider<SInstance, String> dataProvider(CollectionInfo collectionInfo) {
        return new SortableDataProvider<SInstance, String>() {
            @Override
            public Iterator<? extends SInstance> iterator(long first, long count) {
                return getPersistence(collectionInfo).loadAllAsIterable().iterator();
            }

            @Override
            public long size() {
                return Integer.MAX_VALUE;
            }

            @Override
            public IModel<SInstance> model(SInstance object) {
                return new MInstanceRootModel<>(object);
            }
        };
    }
}

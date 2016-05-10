package br.net.mirante.singular.form.wicket.mapper.search;

import br.net.mirante.singular.form.SInstance;
import br.net.mirante.singular.form.STypeComposite;
import br.net.mirante.singular.form.converter.ValueToSICompositeConverter;
import br.net.mirante.singular.form.provider.Config;
import br.net.mirante.singular.form.provider.FilteredPagedProvider;
import br.net.mirante.singular.form.provider.ProviderContext;
import br.net.mirante.singular.form.type.core.STypeBoolean;
import br.net.mirante.singular.form.type.core.STypeString;
import br.net.mirante.singular.form.util.transformer.Value;
import br.net.mirante.singular.form.view.SViewSearchModal;
import br.net.mirante.singular.form.wicket.helpers.SingularFormBaseTest;
import br.net.mirante.singular.util.wicket.datatable.BSDataTable;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.wicket.markup.html.form.Button;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class SearchModalMapperTest extends SingularFormBaseTest {

    private List<Triple<String, String, Boolean>> carros    = new ArrayList<>();
    private int                                   PAGE_SIZE = 5;

    @Override
    public void setUp() {
        super.setUp();
        carros.add(Triple.of("Ford", "Focus", true));
        carros.add(Triple.of("Ford", "Fusion", true));
        carros.add(Triple.of("Ford", "Fiesta", true));
        carros.add(Triple.of("Ford", "Ká", true));
        carros.add(Triple.of("Fiat", "Palio", false));
        carros.add(Triple.of("Fiat", "Mobi", true));
        carros.add(Triple.of("Fiat", "Toro", true));
        carros.add(Triple.of("Chevrolet", "Celta", false));
        carros.add(Triple.of("Chevrolet", "Onix", true));
        carros.add(Triple.of("Chevrolet", "Cobalt", true));
        carros.add(Triple.of("Chevrolet", "Cruze", true));
        carros.add(Triple.of("Renault", "Sandeiro", true));
        carros.add(Triple.of("Renault", "Clio", false));
    }

    @Override
    protected void buildBaseType(STypeComposite<?> baseType) {
        final STypeComposite<?> carro         = baseType.addFieldComposite("carro");
        final STypeString       marca         = carro.addFieldString("marca");
        final STypeString       modelo        = carro.addFieldString("modelo");
        final STypeBoolean      conectividade = carro.addFieldBoolean("conectividade");
        carro.withView(new SViewSearchModal(), (Consumer<SViewSearchModal>) view -> {
            view.withPageSize(PAGE_SIZE);
        });
        carro.asAtrProvider().filteredProvider(new FilteredPagedProvider<Triple>() {

            @Override
            public void configureProvider(Config cfg) {
                cfg.getFilter().addFieldString("marca");
                cfg.getFilter().addFieldString("modelo");
                cfg.getFilter().addFieldBoolean("conectividade");
                cfg.result().addColumn("left", "Marca")
                        .addColumn("middle", "Modelo")
                        .addColumn("right", "Conectividade");
            }

            @Override
            public List<Triple> load(ProviderContext<SInstance> context) {
                return filterByInstance(context.getFilterInstance()).subList((int) context.getFirst(), (int) (context.getFirst() + context.getCount()));
            }

            @Override
            public long getSize(ProviderContext<SInstance> context) {
                return (long) filterByInstance(context.getFilterInstance()).size();
            }

            private List<Triple> filterByInstance(SInstance filter) {
                String  marca         = Value.of(filter, "marca");
                String  modelo        = Value.of(filter, "modelo");
                Boolean conectividade = Value.of(filter, "conectividade");
                return carros.stream().filter(triple -> {
                    boolean contains = true;
                    if (marca != null) {
                        contains = triple.getLeft().equalsIgnoreCase(marca);
                    }
                    if (marca != null) {
                        contains = triple.getMiddle().equalsIgnoreCase(modelo);
                    }
                    if (marca != null) {
                        contains = triple.getRight().equals(conectividade);
                    }
                    return contains;
                }).collect(Collectors.toList());
            }


        });
        carro.asAtrProvider().converter((ValueToSICompositeConverter<Triple<String, String, Boolean>>) (ins, triple) -> {
            ins.setValue(marca, triple.getLeft());
            ins.setValue(modelo, triple.getMiddle());
            ins.setValue(conectividade, triple.getRight());
        });
    }

    private void openModal() {
        final Button modalTrigger = findOnForm(
                Button.class,
                page.getForm(),
                (c) -> c.getId().equals(SearchModalPanel.MODAL_TRIGGER_ID)
        ).findFirst().orElse(null);
        tester.executeAjaxEvent(modalTrigger, "click");
    }

    @Test
    public void testPagination() {
        openModal();
        final BSDataTable table = findOnForm(
                BSDataTable.class,
                page.getForm(),
                (c) -> true
        ).findFirst().orElse(null);
        Assert.assertEquals(table.getRowsPerPage(), PAGE_SIZE);
        Assert.assertEquals(table.getPageCount(), Math.ceil((double) carros.size() / 5), 0);
    }

}
package br.net.mirante.singular.showcase.view.page.showcase;

import org.opensingular.singular.form.SIComposite;
import org.opensingular.singular.form.SIList;
import org.opensingular.singular.form.STypeComposite;
import org.opensingular.singular.form.STypeList;
import org.opensingular.singular.form.type.core.STypeInteger;
import org.opensingular.singular.form.type.core.STypeString;
import org.opensingular.singular.form.view.SViewListByForm;
import br.net.mirante.singular.form.wicket.helpers.SingularFormBaseTest;
import org.apache.wicket.markup.html.form.FormComponent;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

public class ListenerInitTest extends SingularFormBaseTest {

    private STypeString nome;

    @Override
    protected void buildBaseType(STypeComposite<?> baseType) {

        STypeList<STypeComposite<SIComposite>, SIComposite> itens = baseType.addFieldListOfComposite("itens", "itenm");
        itens.asAtr().label("Itens");
        itens.withView(new SViewListByForm().disableDelete().disableNew());

        final STypeComposite<SIComposite> item = itens.getElementsType();
        nome = item.addFieldString("nome");
        nome
                .asAtr().label("Nome").enabled(false)
                .asAtrBootstrap().colPreference(3);

        final STypeInteger quantidade = item.addFieldInteger("quantidade");
        quantidade
                .asAtr().label("Quantidade")
                .asAtrBootstrap().colPreference(2);

        baseType.withInitListener(i -> {
            for (String n : Arrays.asList("Mauro", "Laura")) {
                final Optional<SIList<SIComposite>> itensList = i.findNearest(itens);
                itensList.ifPresent(il -> initItem(il, n, nome));
            }
        });
    }

    private void initItem(SIList<SIComposite> list, String nomeItem, STypeString nome) {
        final SIComposite item = list.addNew();
        item.findNearest(nome)
                .ifPresent(n -> n.setValue(nomeItem));
    }

    @Test
    public void testarInitListener() {
        final List<FormComponent> inputs = findFormComponentsByType(nome).collect(Collectors.toList());

        List<String> nomesCarregados = inputs.stream()
                .map(FormComponent::getDefaultModelObjectAsString)
                .collect(Collectors.toList());

        final List<String> expected = Arrays.asList("Mauro", "Laura");
        assertThat("List equality without order",
                nomesCarregados, containsInAnyOrder(expected.toArray()));
    }

}

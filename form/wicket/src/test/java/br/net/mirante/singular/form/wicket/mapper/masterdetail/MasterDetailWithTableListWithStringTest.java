package br.net.mirante.singular.form.wicket.mapper.masterdetail;

import org.opensingular.singular.form.SIComposite;
import org.opensingular.singular.form.STypeComposite;
import org.opensingular.singular.form.STypeList;
import org.opensingular.singular.form.type.core.STypeString;
import org.opensingular.singular.form.view.SViewListByMasterDetail;
import org.opensingular.singular.form.view.SViewListByTable;
import br.net.mirante.singular.form.wicket.helpers.SingularFormBaseTest;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.fest.assertions.api.Assertions.assertThat;

public class MasterDetailWithTableListWithStringTest extends SingularFormBaseTest {

    STypeString simpleString;

    @Override
    protected void buildBaseType(STypeComposite<?> mockType) {

        final STypeList<STypeComposite<SIComposite>, SIComposite> mockMasterDetail
                = mockType.addFieldListOfComposite("mockList", "mockTypeMasterDetailComposite");

        final STypeComposite<SIComposite> mockTypeMasterDetailComposite = mockMasterDetail.getElementsType();

        mockMasterDetail.withView(SViewListByMasterDetail::new);
        mockMasterDetail.asAtr()
                .label("Mock Type Master Detail ");

        final STypeList<STypeComposite<SIComposite>, SIComposite> mockList
                = mockTypeMasterDetailComposite.addFieldListOfComposite("mockList", "mockTypeComposite");

        final STypeComposite<?> mockTypeComposite = mockList.getElementsType();

        mockList.withView(SViewListByTable::new);
        mockList.asAtr()
                .label("Mock Type Composite");

        simpleString = mockTypeComposite.addFieldString("mockTypeComposite");

    }

    @Test public void clickingTheButtonAddsNewItems() {
        clickMasterDetailLink();

        Assert.assertNotEquals(findMasterDetailLink(), findTableAddButton());

        Stream<FormComponent> stream = findFormComponentsByType(form.getForm(), simpleString);
        Assert.assertTrue(stream.collect(Collectors.toList()).isEmpty());

        clickAddButton();
        assertThat(componentsOfType(simpleString)).hasSize(1);

        clickAddButton();
        clickAddButton();
        assertThat(componentsOfType(simpleString)).hasSize(3);
    }

    @Test
    public void keepsFilledDataForAlreadyAddedItems() {

        clickMasterDetailLink();

        assertThat(componentsOfType(simpleString)).isEmpty();

        clickAddButton();

        assertThat(componentsOfType(simpleString)).hasSize(1);

        form.setValue(getSimpleStringField(), "123456");

        clickAddButton();

        assertThat(componentsOfType(simpleString)).hasSize(2);
        assertThat(getSimpleStringField().getValue()).isEqualTo("123456");
    }

    private void clickMasterDetailLink() {
        tester.executeAjaxEvent(findMasterDetailLink(), "click");
    }

    private List<FormComponent> componentsOfType(STypeString type) {
        return findFormComponentsByType(form.getForm(), type).collect(Collectors.toList());
    }

    private void clickAddButton() {
        tester.executeAjaxEvent(findTableAddButton(), "click");
    }

    private TextField getSimpleStringField() {
        return (TextField) findFormComponentsByType(form.getForm(), simpleString)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Não foi possivel encontrar o select simples"));
    }

    private AbstractLink findMasterDetailLink() {
        return findOnForm(AbstractLink.class, form.getForm(), (b) -> b.getId().equals("_add"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Não foi possivel encontrar o botão de adicionar do mestre detalhe"));
    }

    private Button findTableAddButton() {
        return findOnForm(Button.class, form.getForm(), b -> b.getClass().getName().contains("AddButton"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Não foi possivel encontrar o botão de adicionar do table list"));
    }
}

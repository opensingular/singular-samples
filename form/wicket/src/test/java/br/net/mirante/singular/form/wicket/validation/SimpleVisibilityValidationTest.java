package br.net.mirante.singular.form.wicket.validation;

import org.junit.Assert;
import org.junit.Test;

import br.net.mirante.singular.form.mform.STypeComposite;
import br.net.mirante.singular.form.mform.core.STypeString;
import br.net.mirante.singular.form.wicket.helpers.SingularFormBaseTest;

public class SimpleVisibilityValidationTest extends SingularFormBaseTest {

    STypeString fieldOne;
    STypeString fieldTwo;

    @Override
    protected void buildBaseType(STypeComposite<?> mockType) {

        fieldOne = mockType.addFieldString("fieldOne");
        fieldOne.asAtrBasic().required(true);

        fieldTwo = mockType.addFieldString("fieldTwo");
        fieldTwo.asAtrBasic().visivel(i -> false);
        fieldTwo.asAtrBasic().required(true);
    }

    @Test
    public void testIfContaisErrorOnlyForFieldOne() {
        form.submit(page.getSingularValidationButton());
        Assert.assertFalse(findFormComponentsByType(fieldOne).findFirst().get().getFeedbackMessages().isEmpty());
        Assert.assertTrue(findFormComponentsByType(fieldTwo).findFirst().get().getFeedbackMessages().isEmpty());
    }
}

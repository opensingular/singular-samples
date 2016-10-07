package org.opensingular.form.wicket.mapper.selection;

import org.opensingular.form.SIComposite;
import org.opensingular.form.SInstance;
import org.opensingular.form.STypeComposite;
import org.opensingular.form.STypeSimple;
import org.opensingular.form.provider.AtrProvider;
import org.opensingular.form.provider.SSimpleProvider;
import org.opensingular.form.type.core.STypeString;
import org.opensingular.form.wicket.helpers.SingularFormBaseTest;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.util.List;

import static org.opensingular.form.wicket.helpers.TestFinders.findId;
import static org.opensingular.form.wicket.helpers.TestFinders.findTag;
import static org.fest.assertions.api.Assertions.assertThat;

@RunWith(Enclosed.class)
public class STypeSelectItemSelectionFieldTest {

    private static class Base extends SingularFormBaseTest {
        STypeComposite selectType;
        STypeSimple    nomeUF;
        STypeString    idUF;

        @Override
        protected void buildBaseType(STypeComposite<?> baseType) {
            selectType = (STypeComposite) baseType.addFieldComposite("originUF");
            idUF = selectType.addFieldString("id");
            nomeUF = selectType.addFieldString("nome");
        }

        protected Pair newSelectItem(String id, String descricao) {
            return Pair.of(id, descricao);
        }

        protected SSimpleProvider newProviderFrom(Pair... pairs) {
            return builder -> {
                for (Pair p : pairs) {
                    builder.add().set(idUF, p.getKey()).set(nomeUF, p.getValue());
                }
            };
        }

        protected SIComposite currentSelectionInstance(SIComposite instance) {
            SIComposite value = (SIComposite) instance.getAllFields().iterator().next();
            return value;
        }

        protected Object getSelectKeyFromMInstancia(SInstance instancia) {
            final AtrProvider atrProvider = getInstanciaSelect().asAtrProvider();
            return atrProvider.getDisplayFunction().apply(atrProvider.getConverter().toObject(instancia));
        }

        protected SInstance getInstanciaSelect() {
            return page.getCurrentInstance().getField("originUF");
        }

        protected List<DropDownChoice> options() {
            return (List) findTag(form.getForm(), DropDownChoice.class);
        }
    }

    public static class WithProvider extends Base {
        @Override
        protected void buildBaseType(STypeComposite<?> baseType) {
            super.buildBaseType(baseType);
            selectType.selection()
                    .id(idUF)
                    .display(nomeUF)
                    .simpleProvider(newProviderFrom(newSelectItem("DF", "Distrito Federal"), newSelectItem("SP", "São Paulo")));
        }

        @Test
        public void rendersFiedl() {
            tester.assertEnabled(formField(form, "originUF"));
        }

        @Test
        public void rendersAnDropDownWithSpecifiedOptionsByName() {
            assertThat(options()).hasSize(1);
            DropDownChoice choices = options().get(0);
            assertThat(choices.getChoices()).hasSize(2);
            assertThat(choices.getChoiceRenderer().getIdValue(choices.getChoices().get(0), 0)).isEqualTo("DF");
            assertThat(choices.getChoiceRenderer().getDisplayValue(choices.getChoices().get(0))).isEqualTo("Distrito Federal");
            assertThat(choices.getChoiceRenderer().getIdValue(choices.getChoices().get(1), 1)).isEqualTo("SP");
            assertThat(choices.getChoiceRenderer().getDisplayValue(choices.getChoices().get(1))).isEqualTo("São Paulo");
        }


        @Test @Ignore("We have to figure out how to deal with this case of TypeAhead")
        public void submitsSelectedValue() {
            form.select(findId(form.getForm(), "originUF").get(), 0);
            form.submit();
            SIComposite value = currentSelectionInstance(page.getCurrentInstance());
            assertThat(value.getValue(idUF)).isEqualTo("DF");
        }

    }

    public static class WithDanglingOptions extends Base {
        @Override
        protected void buildBaseType(STypeComposite<?> baseType) {
            super.buildBaseType(baseType);
//            selectType.withSelectionFromProvider(nomeUF,
//                    newProviderFrom(newSelectItem("DF", "Distrito Federal"),
//                            newSelectItem("SP", "São Paulo")));
        }

        @Override
        protected void populateInstance(SIComposite instance) {
            SIComposite value = currentSelectionInstance(instance);
            value.setValue("id", "GO");
            value.setValue("nome", "Goias");
        }

        @Test
        public void rendersAnDropDownWithDanglingOptions() {
//            DropDownChoice choices = options().get(0);
//            Object         valueGO = getSelectKeyFromMInstancia(newSelectItem("GO", "Goias"));
//            Object         valueDF = getSelectKeyFromMInstancia(newSelectItem("DF", "Distrito Federal"));
//            Object         valueSP = getSelectKeyFromMInstancia(newSelectItem("SP", "São Paulo"));
//            assertThat(extractProperty("value").from(choices.getChoices()))
//                    .containsExactly(valueGO, valueDF, valueSP);
//            assertThat(extractProperty("selectLabel").from(choices.getChoices()))
//                    .containsExactly("Goias", "Distrito Federal", "São Paulo");
        }
    }

    public static class WithMandatoryFIeld extends Base {
        @Override
        protected void buildBaseType(STypeComposite<?> baseType) {
            super.buildBaseType(baseType);
            selectType.selection()
                    .id(idUF)
                    .display(nomeUF)
                    .simpleProvider(newProviderFrom(newSelectItem("DF", "Distrito Federal"), newSelectItem("SP", "São Paulo")));
            selectType.withRequired(true);
        }

        @Test @Ignore("We have to figure out how to deal with this case of TypeAhead")
        public void alsoWorksWhenFieldIsMandatory() {
            form.select(findId(form.getForm(), "originUF").get(), 0);
            form.submit();
            SIComposite value = currentSelectionInstance(page.getCurrentInstance());
            assertThat(value.getValue(idUF)).isEqualTo("DF");
        }
    }

    public static class WithIncorrectLabel extends Base {
        @Override
        protected void buildBaseType(STypeComposite<?> baseType) {
            super.buildBaseType(baseType);
            selectType.selection()
                    .id(idUF)
                    .display(nomeUF)
                    .simpleProvider(newProviderFrom(newSelectItem("DF", "Distrito Federal"), newSelectItem("SP", "São Paulo")));
        }

        @Test @Ignore("We have to figure out how to deal with this case of TypeAhead")
        public void verifiyIfSelectLabelIsCorrect() {
            form.select(findId(form.getForm(), "originUF").get(), 0);
            form.submit();
            SIComposite value = currentSelectionInstance(page.getCurrentInstance());
//            assertThat(value.getSelectLabel()).isEqualTo("Distrito Federal");
        }
    }

}
package br.net.mirante.singular.form.view;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import br.net.mirante.singular.commons.base.SingularUtil;
import br.net.mirante.singular.form.SDictionary;
import br.net.mirante.singular.form.SInstance;
import br.net.mirante.singular.form.SType;
import br.net.mirante.singular.form.STypeComposite;
import br.net.mirante.singular.form.STypeSimple;
import br.net.mirante.singular.form.type.core.STypeDate;
import br.net.mirante.singular.form.type.core.STypeInteger;
import br.net.mirante.singular.form.type.core.STypeString;
import br.net.mirante.singular.form.type.country.brazil.STypeCEP;
import br.net.mirante.singular.form.type.country.brazil.STypeCNPJ;
import br.net.mirante.singular.form.type.country.brazil.STypeCPF;

public class TestViewMapperRegistry {

    private ViewMapperRegistry<String> mapper;

    @Before
    public void setup() {
        mapper = new ViewMapperRegistry<>();
    }

    @Test
    public void testBuscaHierarquiaTipo() {
        mapper.register(STypeSimple.class, () -> "A");
        mapper.register(STypeString.class, () -> "B");
        mapper.register(STypeDate.class, () -> "C");

        assertResult("A", STypeInteger.class, SView.class);
        assertResult("B", STypeString.class, SView.class);
        assertResult("B", STypeCPF.class, SView.class);
        assertResult("C", STypeDate.class, SView.class);
        assertResult(null, STypeComposite.class, SView.class);
    }

    @Test
    public void testBuscaEspecificoDepoisDefault() {
        mapper.register(STypeSimple.class, () -> "A");
        mapper.register(STypeString.class, () -> "B");
        mapper.register(STypeString.class, ViewX.class, () -> "D");
        mapper.register(STypeCNPJ.class, ViewX.class, () -> "E");
        mapper.register(STypeDate.class, () -> "C");

        assertResult("A", STypeInteger.class, SView.class);
        assertResult("B", STypeString.class, SView.class);
        assertResult("B", STypeCPF.class, SView.class);
        assertResult("B", STypeCNPJ.class, SView.class);
        assertResult("C", STypeDate.class, SView.class);
        assertResult(null, STypeComposite.class, SView.class);

        assertResult("A", STypeInteger.class, ViewX.class);
        assertResult("D", STypeString.class, ViewX.class);
        assertResult("D", STypeCPF.class, ViewX.class);
        assertResult("E", STypeCNPJ.class, ViewX.class);
        assertResult("C", STypeDate.class, ViewX.class);
        assertResult(null, STypeComposite.class, ViewX.class);

        assertResult("A", STypeInteger.class, ViewY.class);
        assertResult("B", STypeString.class, ViewY.class);
        assertResult("B", STypeCPF.class, ViewY.class);
        assertResult("B", STypeCNPJ.class, ViewY.class);
        assertResult("C", STypeDate.class, ViewY.class);
        assertResult(null, STypeComposite.class, ViewY.class);
    }

    @Test
    public void testAceitarViewDerivada() {
        mapper.register(STypeSimple.class, () -> "A");
        mapper.register(STypeString.class, () -> "B");
        mapper.register(STypeString.class, ViewY.class, () -> "D");
        mapper.register(STypeCNPJ.class, ViewY.class, () -> "E");
        mapper.register(STypeDate.class, () -> "C");

        assertResult("A", STypeInteger.class, SView.class);
        assertResult("B", STypeString.class, SView.class);
        assertResult("B", STypeCPF.class, SView.class);
        assertResult("B", STypeCNPJ.class, SView.class);
        assertResult("C", STypeDate.class, SView.class);
        assertResult(null, STypeComposite.class, SView.class);

        assertResult("A", STypeInteger.class, ViewX.class);
        assertResult("D", STypeString.class, ViewX.class);
        assertResult("D", STypeCPF.class, ViewX.class);
        assertResult("E", STypeCNPJ.class, ViewX.class);
        assertResult("C", STypeDate.class, ViewX.class);
        assertResult(null, STypeComposite.class, ViewX.class);

        assertResult("A", STypeInteger.class, ViewY.class);
        assertResult("D", STypeString.class, ViewY.class);
        assertResult("D", STypeCPF.class, ViewY.class);
        assertResult("E", STypeCNPJ.class, ViewY.class);
        assertResult("C", STypeDate.class, ViewY.class);
        assertResult(null, STypeComposite.class, ViewY.class);
    }

    @Test
    public void testPrioridadeDeAcordoComDerivacao() {
        mapper.register(STypeSimple.class, () -> "A");
        mapper.register(STypeString.class, () -> "B");
        mapper.register(STypeString.class, ViewY.class, () -> "C");
        mapper.register(STypeString.class, ViewX.class, () -> "D");
        // Adiciona em ondem invertida do anterior para ver se dá diferença
        mapper.register(STypeCNPJ.class, ViewX.class, () -> "E");
        mapper.register(STypeCNPJ.class, ViewY.class, () -> "F");
        mapper.register(STypeCEP.class, ViewY.class, () -> "G");

        assertResult("B", STypeString.class, SView.class);
        assertResult("B", STypeCPF.class, SView.class);
        assertResult("B", STypeCNPJ.class, SView.class);
        assertResult("B", STypeCEP.class, SView.class);

        assertResult("D", STypeString.class, ViewX.class);
        assertResult("D", STypeCPF.class, ViewX.class);
        assertResult("E", STypeCNPJ.class, ViewX.class);
        assertResult("G", STypeCEP.class, ViewX.class);

        assertResult("C", STypeString.class, ViewY.class);
        assertResult("C", STypeCPF.class, ViewY.class);
        assertResult("F", STypeCNPJ.class, ViewY.class);
        assertResult("G", STypeCEP.class, ViewY.class);
    }

    public static class ViewX extends SView {
    }

    public static class ViewY extends ViewX {

    }

    private void assertResult(String expected, Class<? extends SType> type, Class<? extends SView> view) {
        try {
            SDictionary dicionario = SDictionary.create();
            assertResult(expected, dicionario.newInstance(type), view.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            throw SingularUtil.propagate(e);
        }
    }

    private void assertResult(String expected, SInstance instance, SView view) {
        assertEquals(expected, mapper.getMapper(instance, view).orElse(null));

    }
}


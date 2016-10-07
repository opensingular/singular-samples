package org.opensingular.form;

import org.opensingular.lib.commons.lambda.IPredicate;
import org.opensingular.form.SInstances.IVisitor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class TestMInstances extends TestCaseForm {

    private SPackageTesteContatos pacote;

    public TestMInstances(TestFormConfig testFormConfig) {
        super(testFormConfig);
    }

    @Before
    public void setup() {
        pacote = createTestDictionary().loadPackage(SPackageTesteContatos.class);
    }

    @Test
    public void test() {
        SIComposite contato = pacote.contato.newInstance();

        SInstances.getDescendant(contato, pacote.nome).getValue();
        SInstances.listDescendants(contato, pacote.enderecoEstado).stream();

        Assert.assertTrue(SInstances.findCommonAncestor(contato, pacote.contato).get().getType() == pacote.contato);
    }

    @Test
    public void testVisit() {
        SIComposite contato = pacote.contato.newInstance();
        Assert.assertEquals(contato,
            SInstances.visit(contato, findFirst(it -> it.getType() == pacote.contato))
                .orElse(null));

        Assert.assertEquals(pacote.nome,
            SInstances.visit(contato, findFirst(it -> it.getType() == pacote.nome))
                .map(it -> it.getType())
                .orElse(null));
        Assert.assertEquals(pacote.sobrenome,
            SInstances.visit(contato, findFirst(it -> it.getType() == pacote.sobrenome))
                .map(it -> it.getType())
                .orElse(null));
    }

    @Test
    public void testVisitOrder() {
        SIComposite contato = pacote.contato.newInstance();
        Assert.assertEquals(pacote.identificacao,
            SInstances.visit(contato, findFirst(it -> it.getType() == pacote.nome || it.getType() == pacote.identificacao))
                .map(it -> it.getType())
                .orElse(null));
    }

    @Test
    public void testVisitPostOrder() {
        SIComposite contato = pacote.contato.newInstance();
        Assert.assertEquals(pacote.nome,
            SInstances.visitPostOrder(contato, findFirst(it -> it.getType() == pacote.nome || it.getType() == pacote.identificacao))
                .map(it -> it.getType())
                .orElse(null));
    }

    private static IVisitor<SInstance, SInstance> findFirst(IPredicate<SInstance> test) {
        return (i, v) -> {
            if (test.test(i))
                v.stop(i);
        };
    }
}
package br.net.mirante.singular.form.provider;

import org.junit.Assert;
import org.junit.Test;


public class FreemarkerUtilTest {

    @Test
    public void safeWrapSingle() throws Exception {
        String template = "${teste}";
        Assert.assertEquals("${(teste)!''}", FreemarkerUtil.safeWrap(template));
    }

    @Test
    public void safeWrapComplex() throws Exception {
        String template = "${teste.foo} - ${teste}";
        Assert.assertEquals("${(teste.foo)!''} - ${(teste)!''}", FreemarkerUtil.safeWrap(template));
    }

}
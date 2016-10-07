package org.opensingular.flow.test.mssql;

import org.junit.BeforeClass;
import org.springframework.test.context.ActiveProfiles;

import org.opensingular.lib.commons.base.SingularPropertiesImpl;
import org.opensingular.flow.test.ProcessVersoesTest;

@ActiveProfiles("mssql")
public class ProcessVersoesMssqlTest extends ProcessVersoesTest {

    @BeforeClass
    public static void configProperties() {
        SingularPropertiesImpl.get().reloadAndOverrideWith(ClassLoader.getSystemClassLoader().getResource(
                "singular-mssql.properties"));
    }
}

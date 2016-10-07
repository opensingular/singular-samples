package org.opensingular.form.io.definition;

import org.opensingular.form.SInfoType;
import org.opensingular.form.STypeComposite;
import org.opensingular.form.TypeBuilder;

@SInfoType(spackage = SPackageDefinitionPersitence.class, name = "package")
public class STypePersistencePackage extends STypeComposite<SIPersistencePackage> {

    public static final String FIELD_PACKAGE_NAME = "packageName";
    public static final String FIELD_TYPES = "types";

    public STypePersistencePackage() {
        super(SIPersistencePackage.class);
    }

    @Override
    protected void onLoadType(TypeBuilder tb) {
        addFieldString(FIELD_PACKAGE_NAME);
        addFieldListOf(FIELD_TYPES, STypePersistenceType.class);
    }

}
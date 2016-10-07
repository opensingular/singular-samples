/*
 * Copyright (c) 2016, Singular and/or its affiliates. All rights reserved.
 * Singular PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.opensingular.singular.form.showcase.component.form.file;

import org.opensingular.form.PackageBuilder;
import org.opensingular.form.SPackage;
import org.opensingular.form.STypeComposite;
import org.opensingular.form.type.basic.AtrBootstrap;
import org.opensingular.form.type.core.attachment.STypeAttachment;
import org.opensingular.singular.form.showcase.component.CaseItem;
import org.opensingular.singular.form.showcase.component.Group;
import org.opensingular.singular.form.showcase.component.Resource;

/**
 * Campo para anexar arquivos
 */
@CaseItem(componentName = "Attachment", group = Group.FILE,
resources = @Resource(PageWithAttachment.class))
public class CaseFileAttachmentPackage extends SPackage {

    @Override
    protected void onLoadPackage(PackageBuilder pb) {
        STypeComposite<?> tipoMyForm = pb.createCompositeType("testForm");

        STypeAttachment anexo = tipoMyForm.addField("anexo", STypeAttachment.class);
        anexo.asAtr().label("Anexo");
        anexo.asAtr().required(true);
        anexo.as(AtrBootstrap.class).colPreference(3);

//        tipoMyForm.addField("a1", STypeAttachment.class).asAtr().label("a1");
//        tipoMyForm.addField("a2", STypeAttachment.class).asAtr().label("a2");
//        tipoMyForm.addField("a3", STypeAttachment.class).asAtr().label("a3");

    }
}
/*
 * Copyright (c) 2016, Singular and/or its affiliates. All rights reserved.
 * Singular PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.opensingular.form.type.core;

import org.opensingular.form.PackageBuilder;
import org.opensingular.form.SDictionary;
import org.opensingular.form.SInfoPackage;
import org.opensingular.form.SPackage;
import org.opensingular.form.SType;
import org.opensingular.form.STypeAttachmentList;
import org.opensingular.form.STypeCode;
import org.opensingular.form.STypeComposite;
import org.opensingular.form.STypeList;
import org.opensingular.form.STypePredicate;
import org.opensingular.form.STypeSimple;
import org.opensingular.form.type.core.annotation.STypeAnnotation;
import org.opensingular.form.type.core.annotation.STypeAnnotationList;
import org.opensingular.form.type.core.attachment.STypeAttachment;

@SInfoPackage(name = SPackageCore.NAME)
public class SPackageCore extends SPackage {

    public static final String NAME = SDictionary.SINGULAR_PACKAGES_PREFIX + "core";

    @Override
    protected void onLoadPackage(PackageBuilder pb) {

        pb.createType(SType.class);
        pb.createType(STypeSimple.class);
        pb.createType(STypeList.class);
        pb.createType(STypeCode.class);
        pb.createType(STypePredicate.class);
        pb.createType(STypeString.class);
        pb.createType(STypeInteger.class);
        pb.createType(STypeLong.class);
        pb.createType(STypeBoolean.class);
        pb.createType(STypeDate.class);
        pb.createType(STypeDecimal.class);
        pb.createType(STypeMonetary.class);
        pb.createType(STypeDateTime.class);
        pb.createType(STypeTime.class);
        pb.createType(STypeComposite.class);
        pb.createType(STypeAnnotation.class);
        pb.createType(STypeAnnotationList.class);
        pb.createType(STypeFormula.class);
        pb.createType(STypeAttachment.class);
        pb.createType(STypeAttachmentList.class);
        pb.createType(STypeHTML.class);

        pb.createAttributeIntoType(STypeAttachment.class, STypeAttachment.ATR_ORIGINAL_ID);
        pb.createAttributeIntoType(STypeAttachment.class, STypeAttachment.ATR_IS_TEMPORARY);


    }

}
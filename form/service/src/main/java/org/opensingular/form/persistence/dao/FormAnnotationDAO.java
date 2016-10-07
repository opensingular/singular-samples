/*
 * Copyright (c) 2016, Singular and/or its affiliates. All rights reserved.
 * Singular PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.opensingular.form.persistence.dao;


import org.opensingular.form.persistence.entity.FormAnnotationEntity;
import org.opensingular.form.persistence.entity.FormAnnotationPK;
import org.opensingular.lib.support.persistence.BaseDAO;

public class FormAnnotationDAO extends BaseDAO<FormAnnotationEntity, FormAnnotationPK> {

    public FormAnnotationDAO() {
        super(FormAnnotationEntity.class);
    }

}
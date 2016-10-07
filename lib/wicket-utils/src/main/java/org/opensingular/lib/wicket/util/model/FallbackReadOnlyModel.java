/*
 * Copyright (c) 2016, Singular and/or its affiliates. All rights reserved.
 * Singular PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.opensingular.lib.wicket.util.model;

import java.util.Arrays;

import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;

@SuppressWarnings("serial")
public class FallbackReadOnlyModel<T>
        extends AbstractReadOnlyModel<T> {

    private final IModel<T>[] models;

    @SuppressWarnings("unchecked")
    public FallbackReadOnlyModel(IModel<T>... models) {
        this.models = Arrays.copyOf(models, models.length);
    }

    @Override
    public void detach() {
        for (IModel<T> m : models) {
            m.detach();
        }
    }

    @Override
    public T getObject() {
        for (IModel<T> m : models) {
            T object = m.getObject();
            if (object != null) {
                return object;
            }
        }
        return null;
    }

}
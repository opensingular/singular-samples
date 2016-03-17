/*
 * Copyright (c) 2016, Mirante and/or its affiliates. All rights reserved.
 * Mirante PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package br.net.mirante.singular.flow.core.builder;

import br.net.mirante.singular.commons.base.SingularUtil;

@FunctionalInterface
public interface ITaskDefinition {

    String getName();

    default String getKey() {
        return SingularUtil.convertToJavaIdentity(getName(), true).toUpperCase();
    }

    default boolean isNameEquals(String name) {
        return getName().equals(name);
    }
}

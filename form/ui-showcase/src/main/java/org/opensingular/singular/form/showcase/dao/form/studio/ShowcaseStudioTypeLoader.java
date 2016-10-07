/*
 * Copyright (c) 2016, Singular and/or its affiliates. All rights reserved.
 * Singular PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.opensingular.singular.form.showcase.dao.form.studio;

import org.opensingular.form.SDictionary;
import org.opensingular.form.SFormUtil;
import org.opensingular.form.SPackage;
import org.opensingular.form.SType;
import org.opensingular.form.spring.SpringTypeLoader;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class ShowcaseStudioTypeLoader extends SpringTypeLoader<Class<SType<?>>> {

    private Map<Class<? extends SPackage>, SDictionary> dictionaries = new HashMap<>();

    @Override
    protected Optional<SType<?>> loadTypeImpl(Class<SType<?>> typeClass) {
        Class<? extends SPackage> packageClass = SFormUtil.getTypePackage(typeClass);
        String typeName = SFormUtil.getTypeName(typeClass);
        if (!dictionaries.containsKey(packageClass)) {
            SDictionary d = SDictionary.create();
            d.loadPackage(packageClass);
            dictionaries.put(packageClass, d);
        }
        return Optional.ofNullable(dictionaries.get(packageClass).getType(typeName));
    }
}
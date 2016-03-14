/*
 * Copyright (c) 2016, Mirante and/or its affiliates. All rights reserved.
 * Mirante PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package br.net.mirante.singular.dsl;

import br.net.mirante.singular.flow.core.MTask;
import br.net.mirante.singular.flow.core.MTaskJava;

import java.util.function.Consumer;

public class JavaBuilder2 {

    public JavaBuilder2(JavaBuilder1 javaBuilder1) {
    }

    public TaskBuilder2 extraConfig(Consumer<MTaskJava> task) {
        task.accept(null);
        return new TaskBuilder2(this);
    }

}

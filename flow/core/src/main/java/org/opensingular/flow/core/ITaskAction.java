/*
 * Copyright (c) 2016, Singular and/or its affiliates. All rights reserved.
 * Singular PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.opensingular.flow.core;


public interface ITaskAction {

    void execute(TaskInstance taskInstance);

    String getName();

    default String getCompleteDescription() {
        return getName();
    }

}
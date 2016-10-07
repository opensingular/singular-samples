/*
 * Copyright (c) 2016, Singular and/or its affiliates. All rights reserved.
 * Singular PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.opensingular.flow.core.renderer;

import org.opensingular.flow.core.ProcessDefinition;
import org.opensingular.flow.core.property.MetaDataRef;

public interface IFlowRenderer {
    
    public static final MetaDataRef<Boolean> SEND_EMAIL = new MetaDataRef<>("SEND_EMAIL", Boolean.class);

    byte[] generateImage(ProcessDefinition<?> definicao);
}
/*
 * Copyright (c) 2016, Mirante and/or its affiliates. All rights reserved.
 * Mirante PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package br.net.mirante.singular.flow.core;

import java.io.Serializable;

import br.net.mirante.singular.flow.core.view.Lnk;

@FunctionalInterface
public interface IProcessCreationPageStrategy extends Serializable {

    public Lnk getCreatePageFor(ProcessDefinition<?> definicaoProcesso, MUser user);

}

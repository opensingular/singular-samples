/*
 * Copyright (c) 2016, Mirante and/or its affiliates. All rights reserved.
 * Mirante PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package br.net.mirante.singular.form.context;

import br.net.mirante.singular.form.document.ServiceRegistry;

/**
 * Interface de uso interno para acessar os valores configurados no SingularFormConfig
 * @param <T>
 * @param <K>
 */
public interface InternalSingularFormConfig extends SingularFormConfig {


    public ServiceRegistry getServiceRegistry();

}
/*
 * Copyright (c) 2016, Mirante and/or its affiliates. All rights reserved.
 * Mirante PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package br.net.mirante.singular.bamclient.portlet.filter;

import org.apache.commons.lang3.StringUtils;

public @interface RestOptionsProvider {

    RestReturnType returnType() default RestReturnType.VALUE;
    String endpoint() default StringUtils.EMPTY;
}

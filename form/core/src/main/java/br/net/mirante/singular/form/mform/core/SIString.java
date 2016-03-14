/*
 * Copyright (c) 2016, Mirante and/or its affiliates. All rights reserved.
 * Mirante PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package br.net.mirante.singular.form.mform.core;

public class SIString extends SIComparable<String> {

    public SIString() {
    }

    @Override
    public STypeString getType() {
        return (STypeString) super.getType();
    }
    
    @Override
    public String toString() {
        return String.format("MIString('%s')", getValue());
    }

}

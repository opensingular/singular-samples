/*
 * Copyright (c) 2016, Mirante and/or its affiliates. All rights reserved.
 * Mirante PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package br.net.mirante.singular.form.io;

import br.net.mirante.singular.form.SInstance;
import br.net.mirante.singular.form.internal.xml.MElement;

public class PersistenceBuilderXML {

    private boolean persistId = true;
    private boolean persistNull = false;
    private boolean persistAttributes = false;

    public PersistenceBuilderXML withPersistId(boolean v) {
        persistId = v;
        return this;
    }

    public PersistenceBuilderXML withPersistNull(boolean v) {
        persistNull = v;
        return this;
    }

    public PersistenceBuilderXML withPersistAttributes(boolean v) {
        persistAttributes = v;
        return this;
    }

    public boolean isPersistId() {
        return persistId;
    }

    public boolean isPersistNull() {
        return persistNull;
    }

    public boolean isPersistAttributes() {
        return persistAttributes;
    }

    public MElement toXML(SInstance instancia) {
        return MformPersistenciaXML.toXML(null, null, instancia, this);
    }

}
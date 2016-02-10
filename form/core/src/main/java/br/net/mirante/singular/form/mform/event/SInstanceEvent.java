package br.net.mirante.singular.form.mform.event;

import br.net.mirante.singular.form.mform.SInstance;

public abstract class SInstanceEvent {

    private final SInstance source;

    protected SInstanceEvent(SInstance source) {
        this.source = source;
    }

    public SInstance getSource() {
        return source;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ": " + getSource();
    }
}
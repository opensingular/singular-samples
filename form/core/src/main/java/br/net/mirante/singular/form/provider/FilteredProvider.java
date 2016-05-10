package br.net.mirante.singular.form.provider;

import br.net.mirante.singular.form.SInstance;

import java.io.Serializable;

public interface FilteredProvider<R extends Serializable> extends Provider<R, SInstance> {

    void configureProvider(Config cfg);

}
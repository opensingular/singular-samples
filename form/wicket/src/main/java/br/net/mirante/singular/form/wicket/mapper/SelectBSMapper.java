/*
 * Copyright (c) 2016, Mirante and/or its affiliates. All rights reserved.
 * Mirante PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package br.net.mirante.singular.form.wicket.mapper;

import org.apache.wicket.model.IModel;

import br.net.mirante.singular.form.mform.SInstance;
import br.net.mirante.singular.form.wicket.mapper.selection.SelectMapper;

@SuppressWarnings("serial")
public class SelectBSMapper extends SelectMapper {

    @Override
    protected boolean isBSSelect(IModel<? extends SInstance> model) {
        return true;
    }
}

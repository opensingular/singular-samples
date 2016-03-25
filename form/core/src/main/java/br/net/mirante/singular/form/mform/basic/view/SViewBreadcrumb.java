/*
 * Copyright (c) 2016, Mirante and/or its affiliates. All rights reserved.
 * Mirante PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package br.net.mirante.singular.form.mform.basic.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.net.mirante.singular.commons.lambda.IFunction;
import br.net.mirante.singular.form.mform.SInstance;
import br.net.mirante.singular.form.mform.STypeSimple;

public class SViewBreadcrumb extends AbstractSViewListWithControls<SViewBreadcrumb> {

    private boolean editEnabled = true;
    private boolean showTable = true;
    private List<Column> columns = new ArrayList<>();

    public SViewBreadcrumb col(STypeSimple<?, ?> type) {
        columns.add(new Column(type.getName(), null, null));
        return this;
    }

    public SViewBreadcrumb col(STypeSimple<?, ?> type, String customLabel) {
        columns.add(new Column(type.getName(), customLabel, null));
        return this;
    }

    public SViewBreadcrumb col(STypeSimple<?, ?> type, IFunction<SInstance, String> displayFunction) {
        columns.add(new Column(type.getName(), null, displayFunction));
        return this;
    }

    public SViewBreadcrumb disableEdit() {
        this.editEnabled = false;
        return this;
    }

    public boolean isEditEnabled() {
        return editEnabled;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public class Column implements Serializable {

        private String typeName;
        private String customLabel;
        private IFunction<SInstance, String> displayValueFunction;

        public Column() {
        }

        public Column(String typeName, String customLabel, IFunction<SInstance, String> displayValueFunction) {
            this.typeName = typeName;
            this.customLabel = customLabel;
            this.displayValueFunction = displayValueFunction;
        }

        public String getTypeName() {
            return typeName;
        }

        public String getCustomLabel() {
            return customLabel;
        }

        public IFunction<SInstance, String> getDisplayValueFunction() {
            return displayValueFunction;
        }
    }

    public boolean isShowTable() {
        return showTable;
    }

    public void setShowTable(boolean showTable) {
        this.showTable = showTable;
    }
}

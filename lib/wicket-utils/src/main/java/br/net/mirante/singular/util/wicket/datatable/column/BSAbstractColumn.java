package br.net.mirante.singular.util.wicket.datatable.column;

import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.model.IModel;

import br.net.mirante.singular.util.wicket.lambda.IFunction;

public abstract class BSAbstractColumn<T, S>
    extends AbstractColumn<T, S>
    implements IRowMergeableColumn<T> {

    private IFunction<T, ?> rowMergeIdFunction = it -> null;

    public BSAbstractColumn(IModel<String> displayModel, S sortProperty) {
        super(displayModel, sortProperty);
    }

    public BSAbstractColumn(IModel<String> displayModel) {
        super(displayModel);
    }

    public IFunction<T, ?> getRowMergeIdFunction() {
        return rowMergeIdFunction;
    }
    public BSAbstractColumn<T, S> setRowMergeIdFunction(IFunction<T, ?> rowMergeIdFunction) {
        this.rowMergeIdFunction = (rowMergeIdFunction != null) ? rowMergeIdFunction : it -> null;
        return this;
    }
    @Override
    public Object getRowMergeId(IModel<T> rowModel) {
        return (rowMergeIdFunction == null) ? null : rowMergeIdFunction.apply(rowModel.getObject());
    }

}
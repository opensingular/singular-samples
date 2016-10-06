package br.net.mirante.singular.form.wicket.mapper.selection;

import org.opensingular.singular.form.SInstance;
import org.opensingular.singular.form.converter.SInstanceConverter;
import org.opensingular.singular.form.view.SView;
import org.opensingular.singular.form.view.SViewAutoComplete;
import org.opensingular.singular.form.view.SViewSelectionBySelect;
import br.net.mirante.singular.form.wicket.WicketBuildContext;
import br.net.mirante.singular.form.wicket.mapper.AbstractControlsFieldComponentMapper;
import br.net.mirante.singular.util.wicket.bootstrap.layout.BSControls;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;

import java.io.Serializable;

/**
 * Mapper responsible for rendering the SViewAutoComplete withing wicket.
 *
 * @author Fabricio Buzeto
 */
public class AutocompleteMapper extends AbstractControlsFieldComponentMapper {

    @Override
    public Component appendInput(WicketBuildContext ctx, BSControls formGroup, IModel<String> labelModel) {
        final IModel<? extends SInstance> model = ctx.getModel();
        final SView view = ctx.getView();
        
        validateView(view);
        SViewAutoComplete.Mode fetch = SViewAutoComplete.Mode.STATIC;
        if(view instanceof SViewAutoComplete){
            fetch = ((SViewAutoComplete) view).fetch();
        }
        final TypeaheadComponent comp = new TypeaheadComponent(model.getObject().getName(), model, fetch);
        formGroup.appendDiv(comp);
        return comp.getValueField();
    }

    private void validateView(SView view) {
        if (!isAValidView(view)) {
            throw new RuntimeException("AutocompleteMapper only accepts SViewAutoComplete as its view");
        }
    }

    private boolean isAValidView(SView view) {
        return view instanceof SViewAutoComplete ||
                view instanceof SViewSelectionBySelect;
    }

    @Override
    public String getReadOnlyFormattedText(IModel<? extends SInstance> model) {
        final SInstance mi = model.getObject();
        if ((mi != null) && (mi.getValue() != null)) {
            final SInstanceConverter converter = mi.asAtrProvider().getConverter();
            if (converter != null) {
                final Serializable converted = converter.toObject(mi);
                if (converted != null) {
                    return mi.asAtrProvider().getDisplayFunction().apply(converted);
                }
            }
        }
        return StringUtils.EMPTY;
    }

    @Override
    public void adjustJSEvents(Component comp) {
    }

}
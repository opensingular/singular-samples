package br.net.mirante.singular.showcase.component.validation;

import java.util.Optional;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;

import br.net.mirante.singular.form.mform.SIComposite;
import br.net.mirante.singular.form.mform.SInstance;
import br.net.mirante.singular.form.validation.InstanceValidationContext;
import br.net.mirante.singular.form.wicket.model.MInstanceRootModel;
import br.net.mirante.singular.form.wicket.util.WicketFormProcessing;

public class PartialValidationButton extends AjaxButton {

    private final IModel<? extends SInstance> currentInstance;

    public PartialValidationButton(String id, IModel<? extends SInstance> currentInstance) {
        super(id);
        this.currentInstance = currentInstance;
    }

    //@destacar:bloco
    protected void addValidationErrors(Form<?> form, SInstance instance) {
        final SInstance obrigatorio1 = ((SIComposite) instance).getField("obrigatorio_1");
        InstanceValidationContext validationContext = new InstanceValidationContext(obrigatorio1);
        validationContext.validateSingle();
        WicketFormProcessing.associateErrorsToComponents(validationContext, form, new MInstanceRootModel<>(obrigatorio1));
    }
    //@destacar:fim

    @Override
    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
        super.onSubmit(target, form);
        addValidationErrors(form, currentInstance.getObject());
        target.add(form);
    }

    @Override
    protected void onError(AjaxRequestTarget target, Form<?> form) {
        super.onError(target, form);
        WicketFormProcessing.onFormError(form, Optional.of(target), currentInstance);
    }
}

package br.net.mirante.singular.form.wicket.validator;

import br.net.mirante.singular.form.mform.MInstancia;
import br.net.mirante.singular.form.validation.IInstanceValidatable;
import br.net.mirante.singular.form.validation.IValidationError;
import br.net.mirante.singular.form.validation.ValidationErrorLevel;
import br.net.mirante.singular.form.wicket.model.IMInstanciaAwareModel;

final class InstanceValidatableAdapter<V> implements IInstanceValidatable<V> {
    private final org.apache.wicket.Component                  component;
    private final org.apache.wicket.validation.IValidator<V>   wicketValidator;
    private final org.apache.wicket.validation.IValidatable<V> wicketValidatable;
    private final IMInstanciaAwareModel<V>                     model;
    private ValidationErrorLevel                               defaultLevel = ValidationErrorLevel.ERROR;
    public InstanceValidatableAdapter(
        org.apache.wicket.Component component,
        org.apache.wicket.validation.IValidator<V> wValidator,
        org.apache.wicket.validation.IValidatable<V> wValidatable,
        IMInstanciaAwareModel<V> model) {
        this.component = component;
        this.wicketValidator = wValidator;
        this.wicketValidatable = wValidatable;
        this.model = model;
    }
    public void setDefaultLevel(ValidationErrorLevel defaultLevel) {
        this.defaultLevel = defaultLevel;
    }
    @Override
    public MInstancia getInstance() {
        return model.getMInstancia();
    }
    @Override
    public void error(IValidationError singularError) {
        errorInternal(defaultLevel, singularError.getMessage());
    }
    @Override
    public IValidationError error(String msg) {
        return errorInternal(defaultLevel, msg);
    }
    @Override
    public void error(ValidationErrorLevel level, IValidationError singularError) {
        errorInternal(level, singularError.getMessage());
    }
    @Override
    public IValidationError error(ValidationErrorLevel level, String msg) {
        return errorInternal(level, msg);
    }
    private IValidationError errorInternal(ValidationErrorLevel level, String msg) {
        org.apache.wicket.validation.ValidationError wicketError = new org.apache.wicket.validation.ValidationError(wicketValidator);
        wicketError.setMessage(msg);
        if (ValidationErrorLevel.ERROR == level) {
            wicketValidatable.error(wicketError);
        } else {
            component.warn(msg);
        }
        return new ValidationErrorAdapter(wicketError);
    }
    @Override
    public boolean isValid() {
        return wicketValidatable.isValid();
    }
}
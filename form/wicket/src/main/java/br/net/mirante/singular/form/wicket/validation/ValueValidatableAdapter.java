package br.net.mirante.singular.form.wicket.validation;

import br.net.mirante.singular.form.mform.MInstancia;
import br.net.mirante.singular.form.validation.IValueValidatable;
import br.net.mirante.singular.form.validation.IValidationError;
import br.net.mirante.singular.form.validation.ValidationErrorLevel;
import br.net.mirante.singular.form.wicket.model.IMInstanciaAwareModel;

final class ValueValidatableAdapter<V> implements IValueValidatable<V> {
    private final org.apache.wicket.Component                  component;
    private final org.apache.wicket.validation.IValidator<V>   wicketValidator;
    private final org.apache.wicket.validation.IValidatable<V> wicketValidatable;
    private final IMInstanciaAwareModel<V>                     model;
    private ValidationErrorLevel                               defaultLevel = ValidationErrorLevel.ERROR;
    public ValueValidatableAdapter(
        org.apache.wicket.Component component,
        org.apache.wicket.validation.IValidator<V> wValidator,
        org.apache.wicket.validation.IValidatable<V> wValidatable,
        IMInstanciaAwareModel<V> model) {
        this.component = component;
        this.wicketValidator = wValidator;
        this.wicketValidatable = wValidatable;
        this.model = model;
    }
    @Override
    public void setDefaultLevel(ValidationErrorLevel defaultLevel) {
        this.defaultLevel = defaultLevel;
    }
    @Override
    public V getValue() {
        return wicketValidatable.getValue();
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
        return new ValidationErrorAdapter(getInstance(), level, wicketError);
    }
    @Override
    public boolean isValid() {
        return wicketValidatable.isValid();
    }
}
package br.net.mirante.singular.form.mform.core.annotation;

import br.net.mirante.singular.form.mform.*;
import br.net.mirante.singular.form.mform.core.MIString;
import br.net.mirante.singular.form.mform.core.MPacoteCore;
import br.net.mirante.singular.form.mform.core.MTipoString;
import br.net.mirante.singular.form.mform.core.attachment.MIAttachment;

/**
 * This type represents an Annotation of a field.
 * For now only composite fields can be anotated but this type does not enforce such rule.
 *
 * @author Fabricio Buzeto
 */
@MInfoTipo(nome = "Annotation", pacote = MPacoteCore.class)
public class MTipoAnnotation extends MTipoComposto<MIAnnotation> {

    public static final String          FIELD_TEXT          = "text",
                                        FIELD_TARGET_ID     = "targetId",
                                        FIELD_APPROVED      = "isApproved"
                                        ;

    public MTipoAnnotation() {
        super(MIAnnotation.class);
    }

    @Override
    protected void onCargaTipo(TipoBuilder tb) {
        super.onCargaTipo(tb);

        addCampoString(FIELD_TEXT);
        addCampoBoolean(FIELD_APPROVED);
        addCampoInteger(FIELD_TARGET_ID);
    }

    @Override
    public <T extends Object> T converter(Object valor, Class<T> classeDestino) {
        return (T) valor;
    }
}
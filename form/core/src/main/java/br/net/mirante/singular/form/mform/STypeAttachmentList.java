package br.net.mirante.singular.form.mform;

import br.net.mirante.singular.form.mform.core.SPackageCore;
import br.net.mirante.singular.form.mform.core.attachment.SIAttachment;
import br.net.mirante.singular.form.mform.core.attachment.STypeAttachment;

@SInfoType(name = "STypeAttachmentList", spackage = SPackageCore.class)
public class STypeAttachmentList extends STypeList<STypeAttachment, SIAttachment> {

    void setElementsTypeFieldName(String fieldName) {
        setElementsType(extendType(fieldName, STypeAttachment.class));
    }

    @Override
    protected void onLoadType(TypeBuilder tb) {
        super.onLoadType(tb);
        tb.getType().asAtrBasic().displayString(context -> {
            final StringBuilder displayString = new StringBuilder();
            if (context.instance() instanceof SIList) {
                ((SIList<?>) context.instance()).getChildren()
                        .stream()
                        .map(i -> (SIAttachment) i)
                        .map(SIAttachment::toStringDisplayDefault)
                        .forEach(name -> {
                            if (!displayString.toString().isEmpty()) {
                                displayString.append(", ");
                            }
                            displayString.append(name);
                        });
            }
            return displayString.toString();
        });
    }
}

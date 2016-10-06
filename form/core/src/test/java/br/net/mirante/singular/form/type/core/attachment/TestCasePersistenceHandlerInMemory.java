package br.net.mirante.singular.form.type.core.attachment;

import org.opensingular.singular.form.type.core.attachment.IAttachmentPersistenceHandler;
import org.opensingular.singular.form.type.core.attachment.handlers.InMemoryAttachmentPersitenceHandler;

public class TestCasePersistenceHandlerInMemory extends TestCasePersistenceHandlerBase {

    @Override
    protected IAttachmentPersistenceHandler setupHandler() {
        return new InMemoryAttachmentPersitenceHandler();
    }

}

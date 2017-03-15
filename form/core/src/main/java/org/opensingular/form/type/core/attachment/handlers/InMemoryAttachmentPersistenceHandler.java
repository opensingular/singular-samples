/*
 * Copyright (C) 2016 Singular Studios (a.k.a Atom Tecnologia) - www.opensingular.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.opensingular.form.type.core.attachment.handlers;

import org.opensingular.form.document.SDocument;
import org.opensingular.form.type.core.attachment.AttachmentCopyContext;
import org.opensingular.lib.commons.base.SingularException;
import org.opensingular.form.type.core.attachment.IAttachmentRef;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * Implementação manipulador de anexo que guarda tudo em arquivos temporários
 * </p>>
 *
 * @author Daniel C. Bordin
 */
@SuppressWarnings("serial")
public class InMemoryAttachmentPersistenceHandler extends FileSystemAttachmentPersistenceHandler {

    private final Map<String, FileSystemAttachmentRef> attachments = new HashMap<>();

    public InMemoryAttachmentPersistenceHandler() {
        super(StringUtils.isEmpty(System.getProperty("java.io.tmpdir")) ? "./tmp" : System.getProperty("java.io.tmpdir"));
    }

    @Override
    public AttachmentCopyContext<FileSystemAttachmentRef> copy(IAttachmentRef attachmentRef, SDocument document) {
        AttachmentCopyContext<FileSystemAttachmentRef> acr = super.copy(attachmentRef, document);
        attachments.put(acr.getNewAttachmentRef().getId(), acr.getNewAttachmentRef());
        return acr;
    }

    @Override
    public FileSystemAttachmentRef addAttachment(File file, long length, String name) {
        FileSystemAttachmentRef ref = super.addAttachment(file, length, name);
        attachments.put(ref.getId(), ref);
        return ref;
    }

    @Override
    public void deleteAttachment(String key, SDocument document) {
        super.deleteAttachment(key, document);
        attachments.remove(key);
    }

    @Override
    public FileSystemAttachmentRef getAttachment(String fileId) {
        return attachments.get(fileId);
    }

    @Override
    public Collection<FileSystemAttachmentRef> getAttachments() {
        return Collections.unmodifiableCollection(attachments.values());
    }

    @Override
    protected File findFileFromId(String fileId) {
        try {
            File f = File.createTempFile("InMemoryAttachmentPersitenceHandler_", fileId);
            f.deleteOnExit();
            return f;
        } catch (Exception e) {
            throw SingularException.rethrow(e);
        }
    }
}
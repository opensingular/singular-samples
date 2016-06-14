/*
 * Copyright (c) 2016, Mirante and/or its affiliates. All rights reserved.
 * Mirante PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package br.net.mirante.singular.form.type.core.attachment.handlers;

import java.io.IOException;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.google.common.io.ByteStreams;
import com.google.common.io.CountingInputStream;

import br.net.mirante.singular.form.SingularFormException;
import br.net.mirante.singular.form.io.HashUtil;
import br.net.mirante.singular.form.type.core.attachment.IAttachmentRef;

/**
 * <p>
 * Implementação manipulador de anexo que guarda tudo em memória como array de
 * bytes.
 * </p>
 * <p>
 * Deve ser evitado usado dessa implementação devido ao risco de estouro de
 * memória do servidor.
 * </p>
 *
 * @author Daniel C. Bordin
 */
@SuppressWarnings("serial")
public class InMemoryAttachmentPersitenceHandler extends AbstractAttachmentPersistenceHandler {

    private Map<String, InMemoryAttachmentRef> attachments = new HashMap<>();

    @Override
    protected IAttachmentRef addAttachmentCompressed(InputStream deflateInputStream, String hashSHA16Hex, int originalLength) {
        return add(new InMemoryAttachmentRef(toByteArray(deflateInputStream), originalLength, hashSHA16Hex));
    }

    private static byte[] toByteArray(InputStream in) {
        try {
            return ByteStreams.toByteArray(in);
        } catch (IOException e) {
            throw new SingularFormException("Erro lendo origem de dados", e);
        }
    }

    private IAttachmentRef add(InMemoryAttachmentRef novo) {
        attachments.put(novo.getHashSHA1(), novo);
        return novo;
    }

    @Override
    protected IAttachmentRef addAttachmentCompressed(InputStream deflateInputStream, CountingInputStream inCounting,
            DigestInputStream hashCalculatorStream) {
        return add(new InMemoryAttachmentRef(toByteArray(deflateInputStream), (int) inCounting.getCount(),
                HashUtil.toSHA1Base16(hashCalculatorStream)));
    }

    @Override
    public Collection<? extends IAttachmentRef> getAttachments() {
        return (attachments == null) ? Collections.emptyList() : attachments.values();
    }

    @Override
    public IAttachmentRef getAttachment(String hashId) {
        return (attachments == null) ? null : attachments.get(hashId);
    }

    @Override
    public void deleteAttachment(String hashId) {
        if(hashId == null) return ; 
        attachments.remove(hashId);
    }
}
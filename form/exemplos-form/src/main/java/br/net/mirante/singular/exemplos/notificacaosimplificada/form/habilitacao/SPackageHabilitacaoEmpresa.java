/*
 * Copyright (c) 2016, Mirante and/or its affiliates. All rights reserved.
 * Mirante PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package br.net.mirante.singular.exemplos.notificacaosimplificada.form.habilitacao;

import br.net.mirante.singular.exemplos.notificacaosimplificada.form.SPackageNotificacaoSimplificada;
import org.opensingular.singular.form.PackageBuilder;
import org.opensingular.singular.form.SInfoPackage;
import org.opensingular.singular.form.SPackage;

@SInfoPackage(name = SPackageHabilitacaoEmpresa.PACOTE)
public class SPackageHabilitacaoEmpresa extends SPackage {

    public static final String PACOTE        = "mform.peticao.notificacaosimplificada.habilitacao";

    public SPackageHabilitacaoEmpresa() {
        super(PACOTE);
    }


    @Override
    protected void onLoadPackage(PackageBuilder pb) {
        pb.loadPackage(SPackageNotificacaoSimplificada.class);
        pb.createType(STypeHabilitacaoEmpresa.class);
    }

}


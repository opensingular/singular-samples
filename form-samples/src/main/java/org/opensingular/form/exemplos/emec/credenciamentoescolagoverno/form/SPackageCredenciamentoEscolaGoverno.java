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
package org.opensingular.form.exemplos.emec.credenciamentoescolagoverno.form;

import org.opensingular.form.PackageBuilder;
import org.opensingular.form.SInfoPackage;
import org.opensingular.form.SPackage;

@SInfoPackage(name = "mform.peticao.emec.credenciamento.escolagoverno")
public class SPackageCredenciamentoEscolaGoverno extends SPackage {


    @Override
    protected void onLoadPackage(PackageBuilder pb) {
        super.onLoadPackage(pb);
        pb.createType(STypeEstado.class);
        pb.createType(STypeSexo.class);
        pb.createType(STypeMunicipio.class);

        pb.createType(STypeCurso.class);
        pb.createType(STypePDI.class);
        pb.createType(STypePDIProjetoPedagogico.class);
        pb.createType(STypePDIDocumentos.class);
        pb.createType(STypeMantenedora.class);
        pb.createType(STypeCorpoDirigente.class);
        pb.createType(STypeCredenciamentoEscolaGoverno.class);
    }
}

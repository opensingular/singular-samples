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

package org.opensingular.sample.studio.form;


import org.opensingular.form.SIComposite;
import org.opensingular.form.SInfoType;
import org.opensingular.form.STypeComposite;
import org.opensingular.form.STypeList;
import org.opensingular.form.TypeBuilder;
import org.opensingular.form.type.core.STypeBoolean;
import org.opensingular.form.type.core.STypeInteger;
import org.opensingular.form.type.core.STypeString;
import org.opensingular.form.view.list.SViewListByMasterDetail;
import org.opensingular.sample.studio.entity.SimNaoConverter;

import javax.annotation.Nonnull;

@SInfoType(name = "EstudoResiduo", spackage = ResiduoPackage.class)
public class EstudoResiduo extends STypeComposite<SIComposite> {

    public CulturaRef cultura;
    public ModalidadeEmpregoRef modalidadeDeEmprego;
    public TipoDoseRef tipoDose;
    public NormaRef norma;
    public STypeBoolean parteComestivel;
    public STypeBoolean adjuvante;
    public STypeInteger intervaloSeguranca;
    public STypeInteger numeroAplicacoes;
    public STypeString observacao;
    public STypeList<Ensaio, SIComposite> ensaios;

    @Override
    protected void onLoadType(@Nonnull TypeBuilder tb) {

        cultura = addField("cultura", CulturaRef.class);
        modalidadeDeEmprego = addField("modalidadeDeEmprego", ModalidadeEmpregoRef.class);
        tipoDose = addField("tipoDose", TipoDoseRef.class);
        norma = addField("norma", NormaRef.class);
        parteComestivel = addField("parteComestivel", STypeBoolean.class);
        adjuvante = addField("adjuvante", STypeBoolean.class);
        intervaloSeguranca = addField("intervaloSeguranca", STypeInteger.class);
        numeroAplicacoes = addField("numeroAplicacoes", STypeInteger.class);
        observacao = addField("observacao", STypeString.class);
        ensaios = addFieldListOf("ensaios", Ensaio.class);

        cultura.asAtr().required().label("Cultura").asAtrBootstrap().colPreference(6);
        modalidadeDeEmprego.asAtr().required().label("Modalidade de emprego").asAtrBootstrap().colPreference(6);
        tipoDose.asAtr().required().label("Tipo de dose").asAtrBootstrap().colPreference(6);
        norma.asAtr().required().label("Norma").asAtrBootstrap().colPreference(6);

        parteComestivel.asAtr().label("Parte comestivel").asAtrBootstrap().colPreference(3);
        adjuvante.asAtr().label("Adjuvante").asAtrBootstrap().colPreference(3);

        intervaloSeguranca.asAtr().required().label("Intervalo de segurança pretendido (em dias)").asAtrBootstrap().newRow().colPreference(3);
        numeroAplicacoes.asAtr().required().label("Nº de aplicações").asAtrBootstrap().colPreference(3);

        observacao.asAtr().label("Observação").asAtrBootstrap().newRow().colPreference(12);
        observacao.withTextAreaView();

        Ensaio ensaio = ensaios.getElementsType();
        ensaios.asAtr().label("Ensaio").asAtrBootstrap().colPreference(12);
        ensaios.withView(new SViewListByMasterDetail(), view -> view
                .col(ensaio.codigo, "Código")
                .col(ensaio.cidade, "Cidade"));
		// relational mapping
        this.asSQL()
        		.table("TB_ESTUDO_RESIDUOS_TOXICOS")
        		.tablePK("CO_SEQ_ESTUDO_RESIDUOS")
		        .addTableFK("CO_CULTURA", Cultura.class)
		        .addTableFK("CO_MODALIDADE_EMPREGO", ModalidadeDeEmprego.class)
		        .addTableFK("CO_TIPO_DOSE", TipoDose.class)
		        .addTableFK("CO_NORMA", Norma.class);
        parteComestivel.asSQL()
				.column("ST_PARTE_COMESTIVEL")
				.columnConverter(SimNaoConverter::new);
        adjuvante.asSQL()
				.column("ST_ADJUVANTE")
				.columnConverter(SimNaoConverter::new);
        intervaloSeguranca.asSQL()
				.column("QT_DIAS_INTERVALO_SEGURANCA");
        numeroAplicacoes.asSQL()
				.column("NU_APLICACOES");
        observacao.asSQL()
				.column("DS_OBSERVACAO");
    }
}

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

package org.opensingular.form.exemplos.notificacaosimplificada.domain;
// Generated 16/03/2010 08:00:26

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.opensingular.form.exemplos.notificacaosimplificada.domain.enums.TipoEstadoFisico;
import org.opensingular.lib.support.persistence.enums.SimNao;
import org.opensingular.form.exemplos.notificacaosimplificada.domain.generic.VocabularioControlado;
import org.opensingular.lib.support.persistence.util.GenericEnumUserType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

/**
 * FormaFarmaceuticaBasica generated by Vinicius Uriel
 */
@XmlRootElement(name = "forma-farmaceutica-basica", namespace = "http://www.anvisa.gov.br/reg-med/schema/domains")
@XmlType(name = "forma-farmaceutica-basica", namespace = "http://www.anvisa.gov.br/reg-med/schema/domains")
@Entity
@Table(name = "TB_FORMA_FARMACEUTICA_BASICA", schema = "DBMEDICAMENTO")
@PrimaryKeyJoinColumn(name = "CO_FORMA_FARMA_BASICA", referencedColumnName = "CO_SEQ_VOCABULARIO_CONTROLADO")
@NamedQueries({
        @NamedQuery(name = "FormaFarmaceuticaBasica.findAll", query = "Select formaFarmaceuticaBasica From FormaFarmaceuticaBasica as formaFarmaceuticaBasica where formaFarmaceuticaBasica.ativa = 'S'  Order by formaFarmaceuticaBasica.descricao  ")})
public class FormaFarmaceuticaBasica extends VocabularioControlado {

    private static final long serialVersionUID = -8195769859634924112L;

    @Column(name = "TP_ESTADO_FISICO", length = 1)
    @Type(type = GenericEnumUserType.CLASS_NAME, parameters = {
            @Parameter(name = "enumClass", value = TipoEstadoFisico.ENUM_CLASS_NAME),
            @Parameter(name = "identifierMethod", value = "getCodigo"),
            @Parameter(name = "valueOfMethod", value = "valueOfEnum")})
    private TipoEstadoFisico tipoEstadoFisico;

    @Column(name = "DS_CONCEITO", nullable = false, length = 400)
    private String conceito;

    public FormaFarmaceuticaBasica() {
    }

    public FormaFarmaceuticaBasica(Long id, String descricao, TipoEstadoFisico tipoEstadoFisico, String conceito, SimNao ativa) {
        this.id = id;
        this.descricao = descricao;
        this.tipoEstadoFisico = tipoEstadoFisico;
        this.conceito = conceito;
        this.ativa = ativa;
    }

    public TipoEstadoFisico getTipoEstadoFisico() {
        return this.tipoEstadoFisico;
    }

    public void setTipoEstadoFisico(TipoEstadoFisico tipoEstadoFisico) {
        this.tipoEstadoFisico = tipoEstadoFisico;
    }

    public String getConceito() {
        return this.conceito;
    }

    public void setConceito(String conceito) {
        this.conceito = conceito;
    }

}

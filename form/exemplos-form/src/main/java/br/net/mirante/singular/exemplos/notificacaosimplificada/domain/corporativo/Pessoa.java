/*
 * Copyright (c) 2016, Mirante and/or its affiliates. All rights reserved.
 * Mirante PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package br.net.mirante.singular.exemplos.notificacaosimplificada.domain.corporativo;


import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import br.net.mirante.singular.exemplos.notificacaosimplificada.domain.Schemas;
import br.net.mirante.singular.exemplos.notificacaosimplificada.domain.corporativo.enums.TipoPessoa;
import br.net.mirante.singular.exemplos.notificacaosimplificada.domain.geral.Cidade;
import br.net.mirante.singular.exemplos.notificacaosimplificada.domain.geral.Pais;
import br.net.mirante.singular.exemplos.notificacaosimplificada.domain.geral.UnidadeFederacao;
import br.net.mirante.singular.persistence.entity.BaseEntity;
import br.net.mirante.singular.support.persistence.util.GenericEnumUserType;

@Entity
@Table(schema = Schemas.DBCORPORATIVO, name = "TB_PESSOA")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "TP_PESSOA")
public abstract class Pessoa extends BaseEntity {

    @Id
    @Column(name = "ID_PESSOA")
    private String cod;

    @Type(type = GenericEnumUserType.CLASS_NAME, parameters = {
            @Parameter(name = "enumClass", value = TipoPessoa.CLASS_NAME),
            @Parameter(name = "identifierMethod", value = "getCod"),
            @Parameter(name = "valueOfMethod", value = "valueOfEnum")})
    @Column(name = "TP_PESSOA", insertable = false, updatable = false)
    private TipoPessoa tipoPessoa;

    @JoinColumn(name = "CO_CIDADE", nullable = false)
    @ManyToOne
    private Cidade cidade;

    @Column(name = "DS_QUALIFICACAO_ENDERECO", length = 60)
    private String qualificacaoEndereco;

    @Column(name = "DS_ENDERECO", length = 150)
    private String endereco;

    @Column(name = "NO_BAIRRO", length = 60)
    private String bairro;

    @Column(name = "NU_CEP", length = 12)
    private String cep;

    @JoinColumn(name = "CO_PAIS", nullable = false)
    @ManyToOne
    private Pais pais;

    @Override
    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public TipoPessoa getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(TipoPessoa tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public abstract String getNome();

    public abstract String getCpfCnpj();

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public String getQualificacaoEndereco() {
        return qualificacaoEndereco;
    }

    public void setQualificacaoEndereco(String qualificacaoEndereco) {
        this.qualificacaoEndereco = qualificacaoEndereco;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public String getEnderecoCompleto() {
        return String.format("%s, %s, %s - %s",
                getEndereco(),
                getBairro(),
                Optional.ofNullable(getCidade()).map(Cidade::getNome).orElse(""),
                Optional.ofNullable(getPais()).map(Pais::getNome).orElse(""));
    }
}

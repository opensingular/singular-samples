/*
 * Copyright (c) 2016, Mirante and/or its affiliates. All rights reserved.
 * Mirante PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package br.net.mirante.singular.exemplos.notificacaosimplificada.domain.corporativo;


import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import br.net.mirante.singular.exemplos.notificacaosimplificada.domain.Schemas;


@Entity
@Table(schema = Schemas.DBCORPORATIVO, name = "TB_PESSOA_FISICA")
@DiscriminatorValue("F")
@PrimaryKeyJoinColumn(name = "ID_PESSOA_FISICA", referencedColumnName = "ID_PESSOA")
public class PessoaFisica extends Pessoa {


    @Column(name = "NO_PESSOA_FISICA")
    private String nome;

    @Column(name = "NU_CPF")
    private String cpf;


    @Override
    public String getCpfCnpj() {
        return cpf;
    }

    @Override
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
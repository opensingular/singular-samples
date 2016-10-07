/*
 * Copyright (c) 2016, Singular and/or its affiliates. All rights reserved.
 * Singular PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.opensingular.bam.dto;

import java.math.BigDecimal;

import org.opensingular.flow.core.dto.IFeedDTO;

public class FeedDTO implements IFeedDTO {

    private String descricaoInstancia;
    private String nomeProcesso;
    private BigDecimal tempoAtraso;
    private BigDecimal media;

    public FeedDTO(String nomeProcesso, String descricaoInstancia, BigDecimal tempoAtraso, BigDecimal media) {
        this.descricaoInstancia = descricaoInstancia;
        this.nomeProcesso = nomeProcesso;
        this.tempoAtraso = tempoAtraso;
        this.media = media;
    }

    @Override
    public String getDescricaoInstancia() {
        return descricaoInstancia;
    }

    @Override
    public void setDescricaoInstancia(String descricaoInstancia) {
        this.descricaoInstancia = descricaoInstancia;
    }

    @Override
    public String getNomeProcesso() {
        return nomeProcesso;
    }

    @Override
    public void setNomeProcesso(String nomeProcesso) {
        this.nomeProcesso = nomeProcesso;
    }

    @Override
    public BigDecimal getTempoDecorrido() {
        return tempoAtraso;
    }

    @Override
    public void setTempoAtraso(BigDecimal tempoAtraso) {
        this.tempoAtraso = tempoAtraso;
    }

    @Override
    public BigDecimal getMedia() {
        return media;
    }

    @Override
    public void setMedia(BigDecimal media) {
        this.media = media;
    }
}
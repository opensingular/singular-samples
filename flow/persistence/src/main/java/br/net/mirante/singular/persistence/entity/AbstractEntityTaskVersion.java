/*
 * Copyright (c) 2016, Mirante and/or its affiliates. All rights reserved.
 * Mirante PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package br.net.mirante.singular.persistence.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import br.net.mirante.singular.flow.core.IEntityTaskType;
import br.net.mirante.singular.flow.core.entity.IEntityProcessVersion;
import br.net.mirante.singular.flow.core.entity.IEntityTaskDefinition;
import br.net.mirante.singular.flow.core.entity.IEntityTaskTransitionVersion;
import br.net.mirante.singular.flow.core.entity.IEntityTaskVersion;

/**
 * The base persistent class for the TB_VERSAO_TAREFA database table.
 * <p>
 * Must declare a {@link GenericGenerator} with name
 * {@link AbstractEntityTaskVersion#PK_GENERATOR_NAME}.
 * </p>
 * <code>@GenericGenerator(name = AbstractEntityTaskVersion.PK_GENERATOR_NAME, strategy = "org.hibernate.id.IdentityGenerator")</code>
 *
 * @param <PROCESS_VERSION>
 * @param <TASK_DEF>
 * @param <TASK_TRANSITION_VERSION>
 */
@MappedSuperclass
@Table(name = "TB_VERSAO_TAREFA")
public abstract class AbstractEntityTaskVersion<PROCESS_VERSION extends IEntityProcessVersion, TASK_DEF extends IEntityTaskDefinition, TASK_TRANSITION_VERSION extends IEntityTaskTransitionVersion, TASK_TYPE extends Enum<?> & IEntityTaskType> extends BaseEntity<Integer> implements IEntityTaskVersion {

    public static final String PK_GENERATOR_NAME = "GENERATED_CO_VERSAO_TAREFA";

    @Id
    @Column(name = "CO_VERSAO_TAREFA")
    @GeneratedValue(generator = PK_GENERATOR_NAME)
    private Integer cod;

    @ManyToOne
    @JoinColumn(name = "CO_VERSAO_PROCESSO", nullable = false)
    private PROCESS_VERSION processVersion;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "CO_DEFINICAO_TAREFA", nullable = false)
    private TASK_DEF taskDefinition;

    @Column(name = "NO_TAREFA", length = 300, nullable = false)
    private String name;

    @Column(name = "CO_TIPO_TAREFA", nullable = false)
    private TASK_TYPE type;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "originTask")
    private List<TASK_TRANSITION_VERSION> transitions = new ArrayList<>();

    public Integer getCod() {
        return cod;
    }

    public void setCod(Integer cod) {
        this.cod = cod;
    }

    public PROCESS_VERSION getProcessVersion() {
        return processVersion;
    }

    public void setProcessVersion(PROCESS_VERSION processVersion) {
        this.processVersion = processVersion;
    }

    public TASK_DEF getTaskDefinition() {
        return taskDefinition;
    }

    public void setTaskDefinition(TASK_DEF taskDefinition) {
        this.taskDefinition = taskDefinition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TASK_TYPE getType() {
        return type;
    }

    public void setType(TASK_TYPE type) {
        this.type = type;
    }

    public List<TASK_TRANSITION_VERSION> getTransitions() {
        return transitions;
    }

    public void setTransitions(List<TASK_TRANSITION_VERSION> transitions) {
        this.transitions = transitions;
    }

}

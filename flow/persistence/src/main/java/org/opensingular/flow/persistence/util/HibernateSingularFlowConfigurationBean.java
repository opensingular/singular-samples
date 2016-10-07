/*
 * Copyright (c) 2016, Singular and/or its affiliates. All rights reserved.
 * Singular PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.opensingular.flow.persistence.util;

import org.opensingular.flow.core.SingularFlowConfigurationBean;
import org.opensingular.flow.core.service.IPersistenceService;
import org.opensingular.flow.core.service.IProcessDefinitionEntityService;
import org.opensingular.flow.core.service.IUserService;
import org.opensingular.flow.persistence.entity.util.SessionLocator;
import org.opensingular.flow.persistence.service.DefaultHibernateProcessDefinitionService;
import org.opensingular.flow.persistence.service.DefaultHibernatePersistenceService;
import org.hibernate.SessionFactory;
import org.springframework.util.Assert;

import javax.inject.Inject;
import java.util.Arrays;

public class HibernateSingularFlowConfigurationBean extends SingularFlowConfigurationBean {

    private String[] definitionsPackages;

    @Inject
    private IUserService userService;

    @Inject
    private SessionFactory sessionFactory;

    private SessionLocator sessionLocator = () -> sessionFactory.getCurrentSession();

    public HibernateSingularFlowConfigurationBean() {
        super(null);
    }

    /**
     * @param processGroupCod - chave do sistema cadastrado no em <code>TB_GRUPO_PROCESSO</code>
     */
    protected HibernateSingularFlowConfigurationBean(String processGroupCod) {
        super(processGroupCod);
    }

    @Override
    protected String[] getDefinitionsPackages() {
        return this.definitionsPackages;
    }

    public void setDefinitionsPackages(String[] definitionsPackages) {
        Arrays.stream(definitionsPackages).forEach(dbp -> Assert.hasLength(dbp, "O pacote base onde estao as classe de definicao nao pode ser nulo ou vazio"));
        this.definitionsPackages = definitionsPackages;
    }

    @Override
    public IUserService getUserService() {
        return this.userService;
    }

    public void setUserService(IUserService userService) {
        this.userService = userService;
    }


    @Override
    protected IPersistenceService<?, ?, ?, ?, ?, ?, ?, ?, ?, ?> getPersistenceService() {
        return new DefaultHibernatePersistenceService(getSessionLocator());
    }

    @Override
    protected IProcessDefinitionEntityService<?, ?, ?, ?, ?, ?, ?, ?> getProcessEntityService() {
        return new DefaultHibernateProcessDefinitionService(getSessionLocator());
    }

    public SessionLocator getSessionLocator() {
        return this.sessionLocator;
    }

    public void setSessionLocator(SessionLocator sessionLocator) {
        Assert.notNull(sessionLocator, "O SessionLocator  pode ser nulo");
        this.sessionLocator = sessionLocator;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        Assert.notNull(sessionFactory, "A session factory pode ser nula");
        this.sessionFactory = sessionFactory;
    }
}

/*
 * Copyright (c) 2016, Mirante and/or its affiliates. All rights reserved.
 * Mirante PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package br.net.mirante.singular.ws.client;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "SingularWS", targetNamespace = "http://ws.core.flow.singular.mirante.net.br/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface SingularWS {


    /**
     * 
     * @param processAbbreviation
     * @param codProcessInstance
     */
    @WebMethod(action = "executeDefaultTransition")
    @RequestWrapper(localName = "executeDefaultTransition", targetNamespace = "http://ws.core.flow.singular.mirante.net.br/", className = "br.net.mirante.singular.ws.client.ExecuteDefaultTransition")
    @ResponseWrapper(localName = "executeDefaultTransitionResponse", targetNamespace = "http://ws.core.flow.singular.mirante.net.br/", className = "br.net.mirante.singular.ws.client.ExecuteDefaultTransitionResponse")
    public void executeDefaultTransition(
        @WebParam(name = "processAbbreviation", targetNamespace = "")
        String processAbbreviation,
        @WebParam(name = "codProcessInstance", targetNamespace = "")
        Long codProcessInstance,
        @WebParam(name = "username", targetNamespace = "")
        String username);

    /**
     * 
     * @param processAbbreviation
     * @param codProcessInstance
     * @param transitionName
     */
    @WebMethod(action = "executeTransition")
    @RequestWrapper(localName = "executeTransition", targetNamespace = "http://ws.core.flow.singular.mirante.net.br/", className = "br.net.mirante.singular.ws.client.ExecuteTransition")
    @ResponseWrapper(localName = "executeTransitionResponse", targetNamespace = "http://ws.core.flow.singular.mirante.net.br/", className = "br.net.mirante.singular.ws.client.ExecuteTransitionResponse")
    public void executeTransition(
        @WebParam(name = "processAbbreviation", targetNamespace = "")
        String processAbbreviation,
        @WebParam(name = "codProcessInstance", targetNamespace = "")
        Long codProcessInstance,
        @WebParam(name = "transitionName", targetNamespace = "")
        String transitionName,
        @WebParam(name = "username", targetNamespace = "") String username);

    /**
     * 
     * @return
     *     returns java.lang.String
     */
    @WebMethod(action = "ping")
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "ping", targetNamespace = "http://ws.core.flow.singular.mirante.net.br/", className = "br.net.mirante.singular.ws.client.Ping")
    @ResponseWrapper(localName = "pingResponse", targetNamespace = "http://ws.core.flow.singular.mirante.net.br/", className = "br.net.mirante.singular.ws.client.PingResponse")
    public String ping();

    /**
     * 
     * @param processAbbreviation
     * @param codProcessInstance
     * @param username
     */
    @WebMethod(action = "relocateTask")
    @RequestWrapper(localName = "relocateTask", targetNamespace = "http://ws.core.flow.singular.mirante.net.br/", className = "br.net.mirante.singular.ws.client.RelocateTask")
    @ResponseWrapper(localName = "relocateTaskResponse", targetNamespace = "http://ws.core.flow.singular.mirante.net.br/", className = "br.net.mirante.singular.ws.client.RelocateTaskResponse")
    public void relocateTask(
        @WebParam(name = "processAbbreviation", targetNamespace = "")
        String processAbbreviation,
        @WebParam(name = "codProcessInstance", targetNamespace = "")
        Long codProcessInstance,
        @WebParam(name = "username", targetNamespace = "")
        String username);

    /**
     * 
     * @param processAbbreviation
     * @return
     *     returns java.lang.Long
     */
    @WebMethod(action = "startInstance")
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "startInstance", targetNamespace = "http://ws.core.flow.singular.mirante.net.br/", className = "br.net.mirante.singular.ws.client.StartInstance")
    @ResponseWrapper(localName = "startInstanceResponse", targetNamespace = "http://ws.core.flow.singular.mirante.net.br/", className = "br.net.mirante.singular.ws.client.StartInstanceResponse")
    public Long startInstance(
        @WebParam(name = "processAbbreviation", targetNamespace = "")
        String processAbbreviation);

}

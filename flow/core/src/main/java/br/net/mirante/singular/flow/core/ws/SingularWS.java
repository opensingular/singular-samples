package br.net.mirante.singular.flow.core.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import br.net.mirante.singular.flow.core.Flow;
import br.net.mirante.singular.flow.core.ProcessDefinition;
import br.net.mirante.singular.flow.core.ProcessInstance;

@WebService
public class SingularWS {

    @WebMethod(action = "ping")
    public String ping() {
        return "pong";
    }

    @WebMethod(action = "startInstance")
    public Long startInstance(@WebParam(name = "processAbbreviation") String processAbbreviation) {
        ProcessDefinition processo = Flow.getProcessDefinitionWith(processAbbreviation);
        ProcessInstance processInstance = processo.newInstance();
        processInstance.start();
        return processInstance.getEntityCod().longValue();
    }

    @WebMethod(action = "executeDefaultTransition")
    public void executeDefaultTransition(@WebParam(name = "processAbbreviation") String processAbbreviation, @WebParam(name = "codProcessInstance") Long codProcessInstance) {
        ProcessInstance processInstance = getProcessInstance(processAbbreviation, codProcessInstance);
        processInstance.executeTransition();
    }

    @WebMethod(action = "executeTransition")
    public void executeTransition(@WebParam(name = "processAbbreviation") String processAbbreviation, @WebParam(name = "codProcessInstance") Long codProcessInstance, @WebParam(name = "transitionName") String transitionName) {
        ProcessInstance processInstance = getProcessInstance(processAbbreviation, codProcessInstance);
        processInstance.executeTransition(transitionName);
    }

    private ProcessInstance getProcessInstance(String processAbbreviation, Long codProcessInstance) {
        return Flow.getProcessDefinitionWith(processAbbreviation).getDataService().retrieveInstance(codProcessInstance.intValue());
    }
}
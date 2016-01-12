package br.net.mirante.singular.flow.core.service;

import java.time.LocalDate;
import java.util.Set;

import br.net.mirante.singular.commons.base.SingularUtil;
import br.net.mirante.singular.flow.core.Flow;
import br.net.mirante.singular.flow.core.SingularFlowException;
import br.net.mirante.singular.flow.core.authorization.AccessLevel;

public interface IFlowMetadataREST {

    static final String PATH_PROCESS_INSTANCE_HAS_ACCESS = "/process/instance/has-access";
    static final String PATH_PROCESS_DEFINITION_HAS_ACCESS = "/process/definition/has-access";
    static final String PATH_PROCESS_DEFINITION_WITH_ACCESS = "/process/definition/with-access";
    static final String PATH_PROCESS_DEFINITION_DIAGRAM = "/process/definition/diagram";

    /**
     * REST path: {@link IFlowMetadataREST#PATH_PROCESS_DEFINITION_WITH_ACCESS}
     * 
     * @param groupToken - request
     * @param userCod - request
     * @param accessLevel - request
     * @return set of process definition key with accessLevel required
     */
    Set<String> listProcessDefinitionsWithAccess(String groupToken, String userCod, AccessLevel accessLevel);

    /**
     * REST path: {@link IFlowMetadataREST#PATH_PROCESS_DEFINITION_HAS_ACCESS}
     * 
     * @param groupToken - request
     * @param processDefinitionKey - request
     * @param userCod - request
     * @param accessLevel - request
     * @return true if has the accessLevel required
     */
    boolean hasAccessToProcessDefinition(String groupToken, String processDefinitionKey, String userCod, AccessLevel accessLevel);

    /**
     * REST path: {@link IFlowMetadataREST#PATH_PROCESS_INSTANCE_HAS_ACCESS}
     * 
     * @param groupToken - request
     * @param processInstanceFullId - request
     * @param userCod - request
     * @param accessLevel - request
     * @return true if has the accessLevel required
     */
    boolean hasAccessToProcessInstance(String groupToken, String processInstanceFullId, String userCod, AccessLevel accessLevel);

    /**
     * REST path {@link IFlowMetadataREST#PATH_PROCESS_DEFINITION_DIAGRAM}
     * 
     * @param groupToken - request
     * @param processDefinitionKey - request
     * @return
     */
    byte[] processDefinitionDiagram(String groupToken, String processDefinitionKey);
    
    static String generateGroupToken(String groupCod){
        return SingularUtil.toSHA1(groupCod+LocalDate.now().toEpochDay());
    }
    
    static void checkGroupToken(String groupToken) {
        String localGroupToken = generateGroupToken(Flow.getConfigBean().getProcessGroupCod());
        if (!localGroupToken.equals(groupToken)) {
            throw new SingularFlowException("Group token inválido");
        }
    }
}
/*
 * Copyright (c) 2016, Singular and/or its affiliates. All rights reserved.
 * Singular PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.opensingular.flow.core;

import java.util.List;

public interface ProcessNotifier {

    //TODO sugestão: responsibleUser -> userInCharge
    public default void notifyUserTaskRelocation(TaskInstance taskInstance, MUser responsibleUser, MUser userToNotify, MUser allocatedUser,
                                                 MUser removedUser) {

    }

    //TODO sugestão: responsibleUser -> userInCharge
    public default void notifyUserTaskAllocation(TaskInstance taskInstance, MUser responsibleUser, MUser userToNotify, MUser allocatedUser,
                                                 MUser removedUser, String justification) {

    }

    public default void notifyStartToResponsibleUser(TaskInstance taskInstance, ExecutionContext executionContext) {

    }

    public default void notifyStartToInterestedUser(TaskInstance taskInstance, ExecutionContext executionContext) {

    }

    public default <X extends MUser> void notifyLogToUsers(TaskHistoricLog taskHistoricLog, List<X> usersToNotify) {

    }

    public default void notifyStateUpdate(ProcessInstance instanciaProcessoMBPM) {

    }
}
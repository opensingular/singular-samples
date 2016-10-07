/*
 * Copyright (c) 2016, Singular and/or its affiliates. All rights reserved.
 * Singular PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.opensingular.flow.core.entity;

import java.util.Date;

import org.opensingular.flow.core.MUser;

public interface IEntityTaskInstanceHistory extends IEntityByCod<Integer> {

    IEntityTaskInstance getTaskInstance();

    Date getBeginDateAllocation();

    void setBeginDateAllocation(Date begin);

    Date getEndDateAllocation();

    void setEndDateAllocation(Date endDateAllocation);

    MUser getAllocatedUser();

    MUser getAllocatorUser();

    String getDescription();

    void setDescription(String description);

    IEntityTaskHistoricType getType();
}
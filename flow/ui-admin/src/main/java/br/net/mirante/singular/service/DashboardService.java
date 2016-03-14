/*
 * Copyright (c) 2016, Mirante and/or its affiliates. All rights reserved.
 * Mirante PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package br.net.mirante.singular.service;

import java.util.List;

import br.net.mirante.singular.persistence.entity.Dashboard;

public interface DashboardService {

    List<Dashboard> retrieveCustomDashboards();

    Dashboard retrieveDashboardById(String customDashboardCode);

}

/*
 * Copyright (C) 2016 Singular Studios (a.k.a Atom Tecnologia) - www.opensingular.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.opensingular.singular.form.showcase.component.form.map;

import javax.annotation.Nonnull;

import org.opensingular.form.SIComposite;
import org.opensingular.form.SInfoType;
import org.opensingular.form.STypeComposite;
import org.opensingular.form.TypeBuilder;
import org.opensingular.form.type.util.STypeLatitudeLongitudeMultipleMarkable;
/*hidden*/import org.opensingular.singular.form.showcase.component.CaseItem;
/*hidden*/import org.opensingular.singular.form.showcase.component.Group;

/**
 * Componente com multiplos marcadores e que aceita arquivo KML.
 */
@CaseItem(componentName = "Google Maps com multiplos marcadores", group = Group.MAPS)
@SInfoType(spackage = CaseMapsPackage.class, name = "GoogleMapsMultipleMarkers")
public class CaseGoogleMapsMultipleMarkersSType extends STypeComposite<SIComposite> {

    public STypeLatitudeLongitudeMultipleMarkable coordenadas;

    @Override
    protected void onLoadType(@Nonnull TypeBuilder tb) {
        coordenadas = this.addField("coordenadas", STypeLatitudeLongitudeMultipleMarkable.class);
        coordenadas
                .asAtr().required();
    }
}

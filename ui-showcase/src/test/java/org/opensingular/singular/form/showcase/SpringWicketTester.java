/*
 *
 *  * Copyright (C) 2016 Singular Studios (a.k.a Atom Tecnologia) - www.opensingular.com
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  *  you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package org.opensingular.singular.form.showcase;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.wicket.util.tester.WicketTester;
import org.opensingular.singular.form.showcase.wicket.ShowcaseApplication;
import org.springframework.stereotype.Component;

@Component
public class SpringWicketTester {

    @Inject
    private ShowcaseApplication app;

    private WicketTester wt;

    @PostConstruct
    public void init(){
        wt = new WicketTester(app, true);
    }

    public WicketTester wt() {
        return wt;
    }
}

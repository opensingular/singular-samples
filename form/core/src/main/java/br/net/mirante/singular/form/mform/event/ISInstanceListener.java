/*
 * Copyright (c) 2016, Mirante and/or its affiliates. All rights reserved.
 * Mirante PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package br.net.mirante.singular.form.mform.event;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import com.google.common.collect.ImmutableList;

public interface ISInstanceListener extends Serializable {

    void onInstanceEvent(SInstanceEvent evt);

    public static class EventCollector implements ISInstanceListener {
        private final List<SInstanceEvent> events = new ArrayList<>();
        private Predicate<SInstanceEvent>  filter;
        public EventCollector() {
            this.filter = e -> true;
        }
        public EventCollector(Predicate<SInstanceEvent> filter) {
            this.filter = filter;
        }
        @Override
        public void onInstanceEvent(SInstanceEvent evt) {
            if (filter.test(evt)) {
                events.add(evt);
            }
        }
        public List<SInstanceEvent> getEvents() {
            return ImmutableList.copyOf(events);
        }
        public void clear() {
            this.events.clear();
        }
    }
}

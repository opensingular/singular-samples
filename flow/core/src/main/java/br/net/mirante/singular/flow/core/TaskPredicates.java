package br.net.mirante.singular.flow.core;

import java.util.Date;
import java.util.function.Predicate;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;

public class TaskPredicates {

    public static ITaskPredicate disabledCreator() {
        return new TaskPredicateImpl("Criador Demanda Inativado", (taskInstance) -> !MBPM.canBeAllocated(taskInstance.getProcessInstance().getPessoaCriadora()));
    }

    public static ITaskPredicate timeLimitInDays(final int numberOfDays) {
        TaskPredicateImpl taskPredicateImpl = new TaskPredicateImpl("Prazo Extrapolado", (taskInstance) -> {
            Date date = taskInstance.getDataAlvoFim();
            if (date != null) {
                return Days.daysBetween(new DateTime(date), DateTime.now()).getDays() > numberOfDays;
            }
            return false;
        });
        taskPredicateImpl.setFullDescription("Prazo Extrapolado em " + numberOfDays + " dias");
        taskPredicateImpl.setEventType(EventType.Timer);
        return taskPredicateImpl;
    }

    public static class TaskPredicateImpl implements ITaskPredicate {

        private final String name;
        private final Predicate<TaskInstance> predicate;

        private EventType eventType = EventType.Conditional;

        private String description;

        private String fullDescription;

        public TaskPredicateImpl(String name, Predicate<TaskInstance> predicate) {
            this.name = name;
            this.predicate = predicate;
        }

        @Override
        public boolean test(TaskInstance task) {
            return predicate.test(task);
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public EventType getEventType() {
            return eventType;
        }

        @Override
        public String getDescription(TaskInstance taskInstance) {
            return StringUtils.defaultIfBlank(description, ITaskPredicate.super.getDescription(taskInstance));
        }

        @Override
        public String getFullDescription() {
            return StringUtils.defaultIfBlank(fullDescription, ITaskPredicate.super.getFullDescription());
        }

        public TaskPredicateImpl setDescription(String description) {
            this.description = description;
            return this;
        }

        public TaskPredicateImpl setEventType(EventType eventType) {
            this.eventType = eventType;
            return this;
        }

        public TaskPredicateImpl setFullDescription(String fullDescription) {
            this.fullDescription = fullDescription;
            return this;
        }

        @Override
        public String toString() {
            return "TaskPredicateImpl [getName()=" + getName() + ", getEventType()=" + getEventType() + ", getFullDescription()=" + getFullDescription() + "]";
        }

    }
}

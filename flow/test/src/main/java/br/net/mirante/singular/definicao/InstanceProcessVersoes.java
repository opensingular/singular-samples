package br.net.mirante.singular.definicao;


import br.net.mirante.singular.defaults.DefaultPageStrategy;
import br.net.mirante.singular.definicao.role.strategy.EmptyUserRoleSettingStrategy;
import br.net.mirante.singular.flow.core.FlowMap;
import br.net.mirante.singular.flow.core.ProcessDefinition;
import br.net.mirante.singular.flow.core.builder.BEnd;
import br.net.mirante.singular.flow.core.builder.BJava;
import br.net.mirante.singular.flow.core.builder.BPeople;
import br.net.mirante.singular.flow.core.builder.BProcessRole;
import br.net.mirante.singular.flow.core.builder.FlowBuilderImpl;

public class InstanceProcessVersoes extends ProcessDefinition<ProcessVersoes> {

    public InstanceProcessVersoes() {
        super(ProcessVersoes.class);
    }

    public static InstanceProcessVersao flow = InstanceProcessVersao.VERSAO_1;

    @Override
    protected FlowMap createFlowMap() {
        return flow.createFlowMap(this);
    }

    public static void changeFlowToVersao1() {
        flow = InstanceProcessVersao.VERSAO_1;
    }

    public static void changeFlowToVersao1ComPapeis() {
        flow = InstanceProcessVersao.VERSAO_1_COM_PAPEIS;
    }

    public static void changeFlowToVersao2() {
        flow = InstanceProcessVersao.VERSAO_2;
    }

    private enum InstanceProcessVersao {
        VERSAO_1() {
            @Override
            public FlowMap createFlowMap(InstanceProcessVersoes instanceProcessVersoes) {
                instanceProcessVersoes.setName("Versão", "Usando versões");

                FlowBuilderImpl flow = new FlowBuilderImpl(instanceProcessVersoes);

                BProcessRole<?> papelTecnico = flow.addRoleDefinition("TECNICO", "TECNICO", new EmptyUserRoleSettingStrategy(), false);

                BJava<?> start = flow.addJava(() -> "Start");
                start.call((ProcessVersoes p) -> {});
                BJava<?> task = flow.addJava(() -> "Task");
                task.call((ProcessVersoes p) -> {});
                BPeople<?> people = flow.addPeopleTask(() -> "People", papelTecnico);
                people.withExecutionPage(new DefaultPageStrategy());
                BEnd<?> end = flow.addEnd(() -> "End");

                flow.setStartTask(start);
                flow.addTransition(start, task);
                flow.addTransition(task, people);
                flow.addTransition(people, end);

                return flow.build();
            }
        },

        VERSAO_1_COM_PAPEIS() {
            @Override
            public FlowMap createFlowMap(InstanceProcessVersoes instanceProcessVersoes) {
                instanceProcessVersoes.setName("Versão", "Usando versões");

                FlowBuilderImpl flow = new FlowBuilderImpl(instanceProcessVersoes);

                BProcessRole<?> papelAnalista = flow.addRoleDefinition("ANALISTA", "ANALISTA", new EmptyUserRoleSettingStrategy(), false);

                BJava<?> start = flow.addJava(() -> "Start");
                start.call((ProcessVersoes p) -> {});
                BJava<?> task = flow.addJava(() -> "Task");
                task.call((ProcessVersoes p) -> {});
                BPeople<?> people = flow.addPeopleTask(() -> "People", papelAnalista);
                people.withExecutionPage(new DefaultPageStrategy());
                BEnd<?> end = flow.addEnd(() -> "End");

                flow.setStartTask(start);
                flow.addTransition(start, task);
                flow.addTransition(task, people);
                flow.addTransition(people, end);

                return flow.build();
            }
        },

        VERSAO_2() {
            @Override
            public FlowMap createFlowMap(InstanceProcessVersoes instanceProcessVersoes) {
                instanceProcessVersoes.setName("Versão", "Usando versões");

                FlowBuilderImpl flow = new FlowBuilderImpl(instanceProcessVersoes);

                BProcessRole<?> papelTecnico = flow.addRoleDefinition("TECNICO", "TECNICO", new EmptyUserRoleSettingStrategy(), false);

                BJava<?> start = flow.addJava(() -> "Start 2");
                start.call((ProcessVersoes p) -> {});
                BJava<?> task = flow.addJava(() -> "Task 2");
                task.call((ProcessVersoes p) -> {});
                BPeople<?> people = flow.addPeopleTask(() -> "People 2", papelTecnico);
                people.withExecutionPage(new DefaultPageStrategy());
                BEnd<?> end = flow.addEnd(() -> "End 2");

                flow.setStartTask(start);
                flow.addTransition(start, task);
                flow.addTransition(task, people);
                flow.addTransition(people, end);

                return flow.build();
            }
        };

        public abstract FlowMap createFlowMap(InstanceProcessVersoes instanceProcessVersoes);
    }

}

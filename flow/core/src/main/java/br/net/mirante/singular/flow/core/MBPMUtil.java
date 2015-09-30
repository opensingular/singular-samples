package br.net.mirante.singular.flow.core;

import java.awt.HeadlessException;
import java.text.Normalizer;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import br.net.mirante.singular.flow.core.entity.IEntityTask;
import br.net.mirante.singular.flow.core.renderer.IFlowRenderer;
import br.net.mirante.singular.flow.core.renderer.YFilesFlowRenderer;

public class MBPMUtil {

    private static final int PESO_TASK_JAVA = 100;
    private static final int PESO_TASK_WAIT = 300;
    private static final int PESO_TASK_PESSOA = 1000;
    private static final int PESO_TASK_FIM = 100000;

    public static void sortInstancesByDistanceFromBeginning(List<? extends ProcessInstance> instancias,
            final ProcessDefinition<?> definicao) {
        instancias.sort((s1, s2) -> compareByDistanceFromBeginning(s1.getEntity().getCurrentTask().getTask(),
                s2.getEntity().getCurrentTask().getTask(), definicao));
    }

    private static int compareByDistanceFromBeginning(IEntityTask s1, IEntityTask s2, ProcessDefinition<?> definicao) {
        int ordem1 = calculateTaskOrder(s1, definicao);
        int ordem2 = calculateTaskOrder(s2, definicao);
        if (ordem1 != ordem2) {
            return ordem1 - ordem2;
        }
        return s1.getName().compareTo(s2.getName());
    }

    public static <T> void sortByDistanceFromBeginning(List<? extends T> lista, Function<T, IEntityTask> conversor,
                                                       ProcessDefinition<?> definicao) {
        lista.sort(getDistanceFromBeginningComparator(conversor, definicao));
    }

    private static <T> Comparator<T> getDistanceFromBeginningComparator(Function<T, IEntityTask> conversor,
                                                                        ProcessDefinition<?> definicao) {
        return (o1, o2) -> compareByDistanceFromBeginning(conversor.apply(o1), conversor.apply(o2), definicao);
    }

    public static <X extends IEntityTask> List<X> getSortedByDistanceFromBeginning(List<X> situacoes,
            ProcessDefinition<?> definicao) {
        List<X> novo = new ArrayList<>(situacoes);
        novo.sort((s1, s2) -> compareByDistanceFromBeginning(s1, s2, definicao));
        return novo;
    }

    public static List<MTask<?>> getSortedTasksByDistanceFromBeginning(ProcessDefinition<?> definicao) {
        calculateTaskOrder(definicao.getFlowMap());
        List<MTask<?>> novo = new ArrayList<>(definicao.getFlowMap().getTasks());
        Collections.sort(novo, (t1, t2) -> {
            if (t1.getOrder() != t2.getOrder()) {
                return t1.getOrder() - t2.getOrder();
            }
            return t1.getName().compareTo(t2.getName());
        });
        return novo;
    }

    static void calculateTaskOrder(FlowMap flowMap) {
        for (MTask<?> task : flowMap.getTasks()) {
            task.setOrder(0);
        }
        Deque<MTask<?>> deque = new ArrayDeque<>();
        orderedVisit(0, flowMap.getStartTask(), deque);
        flowMap.getTasks().stream().filter(task -> task.getOrder() == 0)
                .forEach(task -> task.setOrder(calculateWeight(task) + 1000000));
    }

    private static void orderedVisit(int previousValue, MTask<?> task, Deque<MTask<?>> deque) {
        int valor = previousValue + calculateWeight(task);
        if (task.getOrder() == 0 || (task.getOrder() < valor && !deque.contains(task))) {
            task.setOrder(valor);
            deque.add(task);
            for (MTransition transicao : task.getTransitions()) {
                if (task.getDefaultTransition() == transicao) {
                    orderedVisit(valor, transicao.getDestination(), deque);
                } else {
                    orderedVisit(valor + 1, transicao.getDestination(), deque);
                }
            }
            deque.removeLast();
        }
    }

    private static int calculateTaskOrder(IEntityTask entityTaskDefinition, ProcessDefinition<?> processDefinition) {
        if (!processDefinition.getEntity().getProcessDefinition()
                .equals(entityTaskDefinition.getProcess().getProcessDefinition())) {
            throw new SingularFlowException("Mistura de situações de definições diferrentes");
        }
        MTask<?> task = processDefinition.getFlowMap().getTaskBybbreviation(entityTaskDefinition.getAbbreviation());
        if (task != null) {
            return task.getOrder();
        }
        if (entityTaskDefinition.isPeople()) {
            return 10000000 + PESO_TASK_PESSOA;
        } else if (entityTaskDefinition.isWait()) {
            return 10000000 + PESO_TASK_WAIT;
        } else if (entityTaskDefinition.isEnd()) {
            return 10000000 + PESO_TASK_FIM;
        } else {
            return 10000000 + PESO_TASK_JAVA;
        }
    }

    private static int calculateWeight(MTask<?> task) {
        IEntityTaskType tt = task.getTaskType();
        if (tt.isPeople()) {
            return PESO_TASK_PESSOA;
        } else if (tt.isJava()) {
            return PESO_TASK_JAVA;
        } else if (tt.isWait()) {
            return PESO_TASK_WAIT;
        } else if (tt.isEnd()) {
            return PESO_TASK_FIM;
        }
        throw new SingularFlowException(task.getTaskType() + " não tratado");
    }

    public static String convertToJavaIdentity(String original, boolean normalize) {
        return convertToJavaIdentity(original, false, normalize);
    }

    public static String convertToJavaIdentity(String original, boolean firstCharacterUpperCase, boolean normalize) {
        if (normalize) {
            original = normalize(original);
        }
        StringBuilder sb = new StringBuilder(original.length());
        boolean nextUpper = false;
        for (char c : original.toCharArray()) {
            if (sb.length() == 0) {
                if (Character.isJavaIdentifierStart(c)) {
                    if (firstCharacterUpperCase) {
                        sb.append(Character.toUpperCase(c));
                    } else {
                        sb.append(Character.toLowerCase(c));
                    }
                }
            } else if (Character.isJavaIdentifierPart(c)) {
                if (nextUpper) {
                    c = Character.toUpperCase(c);
                    nextUpper = false;
                }
                sb.append(c);
            } else if (Character.isWhitespace(c)) {
                nextUpper = true;
            }
        }
        return sb.toString();
    }

    public static String normalize(String original) {
        return Normalizer.normalize(original, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }

    /**
     * Apresenta o diagrama BPMN do processo especificado em uma janela. Usa por
     * padrão a API yFiles para gerar o diagrama.
     *
     * Em caso de falha a janela não é mostrada e um LOG é gerado contendo a
     * descrição do problema.
     *
     * Exemplo de código de uso:
     *
     * <pre>
     * public static void main(String[] args) {
     *     MBPMUtil.showSwingDiagram((Class&lt;ProcessDefinition&lt;?>>) new Object() {
     *     }.getClass().getEnclosingClass());
     * }
     * </pre>
     *
     *
     * @param definitionClass
     *            a definição do processo especificado.
     */
    public static void showSwingDiagram(Class<? extends ProcessDefinition<?>> definitionClass) {
        showSwingDiagram(definitionClass, YFilesFlowRenderer.getInstance());
    }

    /**
     * Apresenta o diagrama BPMN do processo especificado em uma janela. Usa o
     * diagramador especificado para gerar a imagem.
     *
     * Em caso de falha a janela não é mostrada e um LOG é gerado contendo a
     * descrição do problema.
     *
     * @param definitionClass
     *            a definição do processo especificado.
     * @param renderer
     *            o diagramador especificado.
     */
    public static void showSwingDiagram(Class<? extends ProcessDefinition<?>> definitionClass, IFlowRenderer renderer) {
        try {
            ProcessDefinition<?> definicao = definitionClass.cast(definitionClass.newInstance());

            new ImageViewer("Diagrama: " + definicao.getName(), renderer.generateImage(definicao));
        } catch (InstantiationException | IllegalAccessException e) {
            Logger.getLogger(MBPMUtil.class.getName()).log(Level.WARNING, e.getMessage(), e);
        }
    }

    private static class ImageViewer extends JFrame {

        public ImageViewer(String title, byte[] image) throws HeadlessException {
            super(title);
            getRootPane().setContentPane(getImageComponent(image));
            pack();
            setLocationRelativeTo(null);
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setVisible(true);
        }

        private JComponent getImageComponent(byte[] image) {
            JPanel panel = new JPanel();
            ImageIcon icon = new ImageIcon(image);
            JLabel label = new JLabel();
            label.setIcon(icon);
            panel.add(label);
            return panel;
        }
    }
}

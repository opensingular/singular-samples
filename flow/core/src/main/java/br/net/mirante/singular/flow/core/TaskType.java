package br.net.mirante.singular.flow.core;

public enum TaskType {

    Java("J", "design/imagens/execute.png"),
    People("P", "design/imagens/pessoa.png"),
    Wait("E", "design/imagens/wait.png"),
    End("F", "design/imagens/jbpm_end.png");

    private final String image;
    private final String abbreviation;

    private TaskType(String abbreviation, String image) {
        this.abbreviation = abbreviation;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public final boolean isEnd() {
        return this == TaskType.End;
    }

    public final boolean isJava() {
        return this == TaskType.Java;
    }

    public final boolean isPeople() {
        return this == TaskType.People;
    }

    public final boolean isWait() {
        return this == TaskType.Wait;
    }

    public String getAbbreviation() {
        return abbreviation;
    }
}
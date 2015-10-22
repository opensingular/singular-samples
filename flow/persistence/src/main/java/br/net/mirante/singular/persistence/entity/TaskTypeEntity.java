package br.net.mirante.singular.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.net.mirante.singular.flow.core.IEntityTaskType;
import br.net.mirante.singular.persistence.util.Constants;
import br.net.mirante.singular.persistence.util.HybridIdentityOrSequenceGenerator;
import org.hibernate.annotations.GenericGenerator;

/**
 * The persistent class for the TB_TIPO_TAREFA database table.
 */
@Entity
@Table(name = "TB_TIPO_TAREFA", schema = Constants.SCHEMA)
public class TaskTypeEntity implements IEntityTaskType {

    public static final Long AUTOMATICA = 1L;
    public static final Long TAREFA_DE_USUARIO = 2L;
    public static final Long ESPERA = 3L;
    public static final Long FIM = 4L;

    @Id
    @Column(name = "CO_TIPO_TAREFA")
    @GeneratedValue(generator = "singular")
    @GenericGenerator(name = "singular", strategy = HybridIdentityOrSequenceGenerator.CLASS_NAME)
    private Long cod;

    @Column(name = "DS_TIPO_TAREFA", nullable = false)
    private String name;

    public TaskTypeEntity() {
    }

    public TaskTypeEntity(Long cod) {
        this.cod = cod;
    }

    public Long getCod() {
        return this.cod;
    }

    public void setCod(Long cod) {
        this.cod = cod;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getImage() {
        return br.net.mirante.singular.flow.core.TaskType.valueOfAbbreviation(getAbbreviation()).getImage();
    }

    @Override
    public boolean isEnd() {
        return br.net.mirante.singular.flow.core.TaskType.valueOfAbbreviation(getAbbreviation()).isEnd();
    }

    @Override
    public boolean isJava() {
        return br.net.mirante.singular.flow.core.TaskType.valueOfAbbreviation(getAbbreviation()).isJava();
    }

    @Override
    public boolean isPeople() {
        return br.net.mirante.singular.flow.core.TaskType.valueOfAbbreviation(getAbbreviation()).isPeople();
    }

    @Override
    public boolean isWait() {
        return br.net.mirante.singular.flow.core.TaskType.valueOfAbbreviation(getAbbreviation()).isWait();
    }

    @Override
    public String getAbbreviation() {
        if (getCod().equals(AUTOMATICA)) {
            return br.net.mirante.singular.flow.core.TaskType.Java.getAbbreviation();
        } else if (getCod().equals(TAREFA_DE_USUARIO)) {
            return br.net.mirante.singular.flow.core.TaskType.People.getAbbreviation();
        } else if (getCod().equals(ESPERA)) {
            return br.net.mirante.singular.flow.core.TaskType.Wait.getAbbreviation();
        } else if (getCod().equals(FIM)) {
            return br.net.mirante.singular.flow.core.TaskType.End.getAbbreviation();
        }

        return null;
    }
}
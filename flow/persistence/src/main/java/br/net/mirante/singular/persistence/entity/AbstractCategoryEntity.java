package br.net.mirante.singular.persistence.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import br.net.mirante.singular.flow.core.entity.IEntityCategory;
import br.net.mirante.singular.flow.core.entity.IEntityProcessDefinition;

/**
 * The base persistent class for the TB_CATEGORIA database table.
 * <p>
 * Must declare a {@link GenericGenerator} with name
 * {@link AbstractCategoryEntity#PK_GENERATOR_NAME}.
 * </p>
 * <code>@GenericGenerator(name = AbstractCategoryEntity.PK_GENERATOR_NAME, strategy = "org.hibernate.id.IdentityGenerator")</code>
 *
 * @param <PROCESS_DEF>
 */
@MappedSuperclass
@Table(name = "TB_CATEGORIA")
public abstract class AbstractCategoryEntity<PROCESS_DEF extends IEntityProcessDefinition> extends BaseEntity implements IEntityCategory {

    public static final String PK_GENERATOR_NAME = "GENERATED_CO_CATEGORIA";
    
    @Id
    @Column(name = "CO_CATEGORIA")
    @GeneratedValue(generator = PK_GENERATOR_NAME)
    private Integer cod;

    @Column(name = "NO_CATEGORIA", length = 100, nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private List<PROCESS_DEF> processDefinitions;

    public Integer getCod() {
        return cod;
    }

    public void setCod(Integer cod) {
        this.cod = cod;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PROCESS_DEF> getProcessDefinitions() {
        return processDefinitions;
    }

    public void setProcessDefinitions(List<PROCESS_DEF> processDefinitions) {
        this.processDefinitions = processDefinitions;
    }

}

package org.opensingular.sample.studio.definition;

import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.opensingular.form.SIComposite;
import org.opensingular.lib.wicket.util.datatable.BSDataTableBuilder;
import org.opensingular.studio.core.definition.StudioDefinition;

public class TipoDoseStudioDefinition implements StudioDefinition {
    @Override
    public String getRepositoryBeanName() {
        return "tipoDoseRepository";
    }

    @Override
    public void configureDatatableColumns(BSDataTableBuilder<SIComposite, String, IColumn<SIComposite, String>> dataTableBuilder) {
        dataTableBuilder.appendPropertyColumn("Tipo de Dose", ins -> ins.getValue("nome"));
    }

    @Override
    public String getTitle() {
        return "Cadastro de Tipos de Doses";
    }
}

package br.net.mirante.singular.bamclient.chart;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.net.mirante.singular.bamclient.builder.AmChartGraph;
import br.net.mirante.singular.bamclient.builder.ChartDataProvider;

public class ColumnSerialChart extends AbstractSerialChart {

    public ColumnSerialChart(ChartDataProvider dataProvider, List<ChartValueField> values, String category) {
        super(dataProvider, values, category);
    }

    @Override
    protected Collection<AmChartGraph> getGraphs() {
        final List<AmChartGraph> graphs = new ArrayList<>();
        values.forEach(v -> {
            graphs.add(new AmChartGraph()
                    .balloonText(String.format("[[category]]: <b>[[value]] %s </b>", v.getSuffix()))
                    .type("column")
                    .valueField(v.getPropertyName())
                    .title(v.getTitle())
                    .lineAlpha(0.2)
                    .fillAlphas(0.8));
        });
        return graphs;
    }
}

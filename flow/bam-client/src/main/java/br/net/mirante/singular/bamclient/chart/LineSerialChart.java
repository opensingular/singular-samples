package br.net.mirante.singular.bamclient.chart;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import br.net.mirante.singular.bamclient.builder.AmChartGraph;
import br.net.mirante.singular.bamclient.builder.ChartDataProvider;

public class LineSerialChart extends AbstractSerialChart {

    public LineSerialChart(ChartDataProvider dataProvider, List<ChartValueField> values, String category) {
        super(dataProvider, values, category);
    }

    @Override
    protected Collection<AmChartGraph> getGraphs() {
        final List<AmChartGraph> graphs = new ArrayList<>();
        values.forEach(v -> {
            graphs.add(new AmChartGraph()
                    .balloonText(String.format("[[category]]: <b>[[value]] %s </b>", v.getSuffix()))
                    .type("smoothedLine")
                    .valueField(v.getPropertyName())
                    .title(v.getTitle())
                    .bullet("square"));
        });
        return graphs;
    }

}

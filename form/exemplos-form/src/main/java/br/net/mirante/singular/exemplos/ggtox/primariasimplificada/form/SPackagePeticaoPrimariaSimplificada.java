package br.net.mirante.singular.exemplos.ggtox.primariasimplificada.form;


import br.net.mirante.singular.exemplos.ggtox.primariasimplificada.common.*;
import br.net.mirante.singular.form.PackageBuilder;
import br.net.mirante.singular.form.SIComposite;
import br.net.mirante.singular.form.SPackage;
import br.net.mirante.singular.form.STypeComposite;
import br.net.mirante.singular.form.provider.SSimpleProvider;
import br.net.mirante.singular.form.type.core.STypeInteger;
import br.net.mirante.singular.form.type.core.STypeString;
import br.net.mirante.singular.form.util.transformer.Value;
import br.net.mirante.singular.form.view.SViewByBlock;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;


public class SPackagePeticaoPrimariaSimplificada extends SPackage {


    public static final String PACOTE        = "mform.peticao";
    public static final String TIPO          = "PeticaoPrimariaSimplificada";
    public static final String NOME_COMPLETO = PACOTE + "." + TIPO;

    public SPackagePeticaoPrimariaSimplificada() {
        super(PACOTE);
    }


    @Override
    protected void onLoadPackage(PackageBuilder pb) {
        super.onLoadPackage(pb);

        pb.getDictionary().loadPackage(SPackagePPSCommon.class);

        final STypeComposite<SIComposite>                      peticaoSimplificada  = pb.createCompositeType(TIPO);
        final STypeComposite<SIComposite>                      tipoPeticao          = peticaoSimplificada.addFieldComposite("tipoPeticao");
        final STypeInteger                                     idTipoPeticao        = tipoPeticao.addFieldInteger("id");
        final STypeString                                      descricaoTipoPeticao = tipoPeticao.addFieldString("nome");
        final STypeString                                      nivel                = peticaoSimplificada.addFieldString("nivel");
        final STypeDadosGeraisPeticaoPrimariaSimplificada      dadosGerais          = peticaoSimplificada.addField("dadosGerais", STypeDadosGeraisPeticaoPrimariaSimplificada.class);
        final STypeEntidadesPeticaoPrimariaSimplificada        entidades            = peticaoSimplificada.addField("entidades", STypeEntidadesPeticaoPrimariaSimplificada.class);
        final STypeProdutoTecnicoPeticaoPrimariaSimplificada   produtoTecnico       = peticaoSimplificada.addField("produtoTecnico", STypeProdutoTecnicoPeticaoPrimariaSimplificada.class);
        final STypeProdutoFormuladoPeticaoPrimariaSimplificada produtoFormulado     = peticaoSimplificada.addField("produtoFormulado", STypeProdutoFormuladoPeticaoPrimariaSimplificada.class);
        final STypeAnexosPeticaoPrimariaSimplificada           anexos               = peticaoSimplificada.addField("anexos", STypeAnexosPeticaoPrimariaSimplificada.class);

        tipoPeticao
                .selection()
                .id(idTipoPeticao)
                .display(descricaoTipoPeticao)
                .simpleProvider((SSimpleProvider) builder -> {
                    builder.add().set(idTipoPeticao, 1).set(descricaoTipoPeticao, "Feromônio, produtos biológicos, bioquímicos e outros");
                    builder.add().set(idTipoPeticao, 2).set(descricaoTipoPeticao, "Pré-mistura");
                    builder.add().set(idTipoPeticao, 3).set(descricaoTipoPeticao, "Preservativo de madeira");
                    builder.add().set(idTipoPeticao, 4).set(descricaoTipoPeticao, "Produto de uso não agrícola");
                    builder.add().set(idTipoPeticao, 5).set(descricaoTipoPeticao, "Produto formulado novo (PFN)");
                    builder.add().set(idTipoPeticao, 6).set(descricaoTipoPeticao, "Produto formulado com base em produto técnico equivalente (PTN)");
                    builder.add().set(idTipoPeticao, 7).set(descricaoTipoPeticao, "Produto técnico novo (PTN)");
                    builder.add().set(idTipoPeticao, 8).set(descricaoTipoPeticao, "Produto técnico equivalente (PTE)");
                });


        List<Integer> apenasNivel1           = Arrays.asList(3, 7, 8);
        List<Integer> peticaoProdutoTecnicos = Arrays.asList(7, 8);

        tipoPeticao
                .withUpdateListener(sic -> sic.findNearest(nivel).ifPresent(i -> {
                    if (!apenasNivel1.contains(Value.of(sic, idTipoPeticao))) {
                        i.asAtr().enabled(true);
                        i.clearInstance();
                    }
                }))
                .asAtr()
                .label(" Tipo de Petição ");

        nivel
                .selectionOf("I", "II", "III", "IV")
                .withRadioView()
                .asAtr()
                .dependsOn(tipoPeticao)
                .visible(si -> Value.notNull(si, tipoPeticao));

        nivel
                .withUpdateListener(si -> {
                    if (apenasNivel1.contains(Value.of(si, idTipoPeticao))) {
                        si.clearInstance();
                        si.asAtr().enabled(false);
                        si.setValue("I");
                    }
                })
                .asAtr()
                .required()
                .label("Nível");

        dadosGerais
                .asAtr()
                .dependsOn(tipoPeticao)
                .visible(si -> Value.notNull(si, tipoPeticao));

        produtoTecnico
                .asAtr()
                .dependsOn(nivel)
                .visible(si -> StringUtils.isNotEmpty(Value.of(si, nivel)));

        produtoTecnico
                .fabricante
                .asAtr()
                .dependsOn(nivel)
                .visible(si -> "I".equals(Value.of(si, nivel)) || "II".equals(Value.of(si, nivel)));

        produtoTecnico
                .fabricantes
                .asAtr()
                .dependsOn(nivel)
                .visible(si -> !("I".equals(Value.of(si, nivel)) || "II".equals(Value.of(si, nivel))));

        produtoFormulado
                .asAtr()
                .dependsOn(nivel)
                .visible(si -> StringUtils.isNotEmpty(Value.of(si, nivel)) && !peticaoProdutoTecnicos.contains(Value.of(si, idTipoPeticao)));

        produtoFormulado
                .formulador
                .asAtr()
                .dependsOn(nivel)
                .visible(si -> "I".equals(Value.of(si, nivel)));

        produtoFormulado
                .formuladores
                .asAtr()
                .dependsOn(nivel)
                .visible(si -> !"I".equals(Value.of(si, nivel)));

        anexos
                .asAtr()
                .dependsOn(nivel)
                .visible(si -> Value.notNull(si, nivel));
        anexos
                .documentacaoI
                .asAtr()
                .dependsOn(nivel)
                .visible(si -> "I".equals(Value.of(si, nivel)));
        anexos
                .documentacaoII
                .asAtr()
                .dependsOn(nivel)
                .visible(si -> "II".equals(Value.of(si, nivel)));

        anexos
                .documentacaoIII
                .asAtr()
                .dependsOn(nivel)
                .visible(si -> "III".equals(Value.of(si, nivel)));

        anexos
                .documentacaoIV
                .asAtr()
                .dependsOn(nivel)
                .visible(si -> "IV".equals(Value.of(si, nivel)));


        peticaoSimplificada.withView(new SViewByBlock(), blocks -> {
            blocks
                    .newBlock().add(tipoPeticao).add(nivel)
                    .newBlock().add(dadosGerais)
                    .newBlock().add(entidades)
                    .newBlock().add(produtoTecnico)
                    .newBlock().add(produtoFormulado)
                    .newBlock().add(anexos);

        });
        
    }

}


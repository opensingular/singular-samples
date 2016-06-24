/*
 * Copyright (c) 2016, Mirante and/or its affiliates. All rights reserved.
 * Mirante PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package br.net.mirante.singular.exemplos.emec.credenciamentoescolagoverno.form;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.google.common.base.Predicates;

import br.net.mirante.singular.form.SIComposite;
import br.net.mirante.singular.form.SIList;
import br.net.mirante.singular.form.SInfoType;
import br.net.mirante.singular.form.SInstance;
import br.net.mirante.singular.form.STypeComposite;
import br.net.mirante.singular.form.STypeList;
import br.net.mirante.singular.form.TypeBuilder;
import br.net.mirante.singular.form.type.core.STypeDecimal;
import br.net.mirante.singular.form.type.core.STypeInteger;
import br.net.mirante.singular.form.type.core.STypeString;
import br.net.mirante.singular.form.type.country.brazil.STypeCEP;
import br.net.mirante.singular.form.view.SViewByBlock;
import br.net.mirante.singular.form.view.SViewListByMasterDetail;
import br.net.mirante.singular.form.view.SViewListByTable;

@SInfoType(spackage = SPackageCredenciamentoEscolaGoverno.class)
public class STypePDI extends STypeComposite<SIComposite>{

    private static final List<String> TIPOS_RECEITA = Arrays.asList("Anuidade / Mensalidade(+)", "Bolsas(-)", "Diversos(+)", "Financiamentos(+)", "Inadimplência(-)", "Serviços(+)", "Taxas(+)");

    @Override
    protected void onLoadType(TypeBuilder tb) {
        super.onLoadType(tb);
        
        addPerfilInstitucional();
        addProjetoPedagogico();
        addImplantacaoInstituicao();
        addOrganizacaoDidaticopedagogica();
        addPerfilCorpoDocente();
        addOrganizacaoAdministrativa();
        addInfraestruturaInstalacoesAcademicas();
        addAtendimentoPessoasNecessidadesEspeciais();
        addAtoAutorizativoCriacao();
        addDemonstrativoCapacidadeSustentabilidadeFinanceira();
        addOutros();
        
        // cria um bloco por campo
        setView(SViewByBlock::new)
            .newBlock("1 Perfil Institucional").add("perfilInstitucional")
            .newBlock("2 Projeto Pedagógico da Instituição").add("projetoPedagogicoInstituicao")
            .newBlock("3 Implantação de Desenvolvimento da Instituição - Programa de Abertura de Cursos de Pós Graduação").add("implantacaoInstituicao")
            .newBlock("4 Organização didatico-pedagógica da Instituição").add("organizacaoDidaticopedagogicaInstituicao")
            .newBlock("5 Perfil do corpo docente e técnico-administrativo").add("perfilCorpoDocenteETecnicoAdministrativo")
            .newBlock("6 Organização Administrativa da Instituição").add("organizacaoAdministrativa")
            .newBlock("7 Infra-estrutura e Instalações Acadêmicas").add("infraestruturaInstalacoesAcademicas")
            .newBlock("8 Atendimento de Pessoas com Necessidades Especiais").add("atendimentoPessoasNecessidadesEspeciais")
            .newBlock("9 Ato autorizativo anterior ou ato de criação").add("atoAutorizativoCriacao")
            .newBlock("10 Demonstrativo de Capacidade e Sustentabilidade Financeira").add("demonstrativoCapacidadeSustentabilidadeFinanceira")
            .newBlock("11 Outros").add("outros");
    }
    
    private void addPerfilInstitucional() {
        final STypeComposite<SIComposite> perfilInstitucional = this.addFieldComposite("perfilInstitucional");
        perfilInstitucional.addFieldInteger("anoInicioPDI", true).asAtr().label("Ano de Início do PDI");
        perfilInstitucional.addFieldInteger("anoFimPDI", true).asAtr().label("Ano de Fim do PDI");
        perfilInstitucional.addFieldString("historicoDesenvolvimentoInstituicao", true)
            .withTextAreaView().asAtr().label("Histórico e desenvolvimento da Instituição de Ensino");
        perfilInstitucional.addFieldString("missaoObjetivosMetas", true)
            .withTextAreaView().asAtr().label("Missão, objetivos e metas da Instituição, na sua área de atuação");
    }
    
    private void addProjetoPedagogico() {
        this.addFieldString("projetoPedagogicoInstituicao", true)
            .withTextAreaView()
            .asAtrBootstrap().maxColPreference();
    }

    private void addImplantacaoInstituicao() {
        final STypeComposite<SIComposite> implantacaoInstituicao = this.addFieldComposite("implantacaoInstituicao");
        implantacaoInstituicao.addFieldListOf("cursosPrevistos", STypeCurso.class)
            .withView(SViewListByMasterDetail::new)
            .asAtr().label("Cursos Previstos").itemLabel("Curso Previsto");
    }
    
    private void addOrganizacaoDidaticopedagogica() {
        this.addFieldString("organizacaoDidaticopedagogicaInstituicao", true)
            .withTextAreaView().asAtr().label("Organização didatico-pedagógica da Instituição")
            .asAtrBootstrap().maxColPreference();
    }

    private void addPerfilCorpoDocente() {
        final STypeComposite<SIComposite> perfilCorpoDocenteETecnicoAdministrativo = this.addFieldComposite("perfilCorpoDocenteETecnicoAdministrativo");
        perfilCorpoDocenteETecnicoAdministrativo.addFieldString("corpoTecnicoAdministrativo", true)
            .withTextAreaView().asAtr().label("Corpo técnico-administrativo");
        perfilCorpoDocenteETecnicoAdministrativo.addFieldString("cronogramaExpansaoCorpoTecnicoAdministrativo", true)
            .withTextAreaView().asAtr().label("Crongrama de expansão do corpo técnico-administrativo");
        perfilCorpoDocenteETecnicoAdministrativo.addFieldString("cronogramaExpansaoCorpoDocente", true)
            .withTextAreaView().asAtr().label("Crongrama de expansão do corpo docente");
        perfilCorpoDocenteETecnicoAdministrativo.addFieldString("criteriosSelecaoContratacaoProfessores", true)
            .withTextAreaView().asAtr().label("Critérios de seleção e contratação dos professores");
        perfilCorpoDocenteETecnicoAdministrativo.addFieldString("politicasQualificacaoPlanoCarreira", true)
            .withTextAreaView().asAtr().label("Políticas de qualificação e plano de carreira do corpo docente");
        perfilCorpoDocenteETecnicoAdministrativo.addFieldString("requisitosTitulacaoExperienciaProfissional", true)
            .withTextAreaView().asAtr().label("Requisitos de titulação e experiência profissional do corpo docente");
        perfilCorpoDocenteETecnicoAdministrativo.addFieldString("regimeTrabalhoProcedimentosSubstituicaoEventualProfessores", true)
            .withTextAreaView().asAtr().label("Regime de trabalho e procedimentos de substituição eventual de professores");
    }

    private void addOrganizacaoAdministrativa() {
        final STypeComposite<SIComposite> organizacaoAdministrativa = this.addFieldComposite("organizacaoAdministrativa");
        organizacaoAdministrativa.addFieldString("estruturaOrganizacionalIES", true)
            .withTextAreaView().asAtr().label("Estrutura OrganizacionalIES");
        organizacaoAdministrativa.addFieldString("procedimentosAtendimentosAlunos", true)
            .withTextAreaView().asAtr().label("Procedimentos de atendimento dos alunos");
        organizacaoAdministrativa.addFieldString("procedimentoAutoavaliacaoInstitucional", true)
            .withTextAreaView().asAtr().label("Procedimento de auto-avaliação Institucional");
    }
    
    private void addInfraestruturaInstalacoesAcademicas() {
        final STypeComposite<SIComposite> infraestruturaInstalacoesAcademicas = this.addFieldComposite("infraestruturaInstalacoesAcademicas");
        
        final STypeList<STypeComposite<SIComposite>, SIComposite> enderecoes = infraestruturaInstalacoesAcademicas.addFieldListOfComposite("enderecoes", "encereco");
        enderecoes.withView(SViewListByMasterDetail::new)
            .asAtr().label("Endereços").itemLabel("Endereço");
        final STypeComposite<SIComposite> endereco = enderecoes.getElementsType();
        endereco.addFieldString("endereco").asAtr().required().label("Endereço");
        endereco.addField("cep", STypeCEP.class);
    }
    
    private void addAtendimentoPessoasNecessidadesEspeciais() {
        this.addFieldString("atendimentoPessoasNecessidadesEspeciais", true)
            .withTextAreaView().asAtr().label("Plano de promoção de acessibilidade e atendimento prioritário, imediato e diferenciado para utilização, "
                + "com segurança e autonomia, total ou assistida, dos espaços, mobiliários e equipamentos urbanos, das edificações, dos serviços de transporte, dos dispositivos, "
                + "sistemas e meios de comunicação e informação, serviços de tradutor e intérprete de Língua Brasileira de Sinais - LIBRAS")
            .asAtrBootstrap().maxColPreference();
    }

    private void addAtoAutorizativoCriacao() {
        final STypeComposite<SIComposite> atoAutorizativoCriacao = this.addFieldComposite("atoAutorizativoCriacao");
        atoAutorizativoCriacao.addFieldString("tipoDocumento", true)
            .withRadioView().selectionOf("Ata", "Decreto", "Decreto-lei", "Lei", "Medida Provisória", "Parecer", "Portaria", "Resolução")
            .asAtr().label("Tipo de Documento")
            .asAtrBootstrap().maxColPreference();
        atoAutorizativoCriacao.addFieldInteger("numeroDocumento", true)
            .asAtr().label("Nº do Documento")
            .asAtrBootstrap().colPreference(3);
        atoAutorizativoCriacao.addFieldDate("dataDocumento", true)
            .asAtr().label("Data do Documento")
            .asAtrBootstrap().colPreference(3);
        atoAutorizativoCriacao.addFieldDate("dataPublicacao", true)
            .asAtr().label("Data de Publicação")
            .asAtrBootstrap().colPreference(3);
        atoAutorizativoCriacao.addFieldDate("dataCriacao", true)
            .asAtr().label("Data de Criação")
            .asAtrBootstrap().colPreference(3);
        atoAutorizativoCriacao.addFieldAttachment("atoAutorizativoAnterior").asAtr().label("Ato autorizativo anterior");
    }
    
    private void addDemonstrativoCapacidadeSustentabilidadeFinanceira() {
        final STypeComposite<SIComposite> demonstrativoCapacidadeSustentabilidadeFinanceira = this.addFieldComposite("demonstrativoCapacidadeSustentabilidadeFinanceira");
        
        final STypeList<STypeComposite<SIComposite>, SIComposite> demonstrativos = demonstrativoCapacidadeSustentabilidadeFinanceira.addFieldListOfComposite("demonstrativos", "demonstrativo");
        final STypeComposite<SIComposite> demonstrativo = demonstrativos.getElementsType();
        
        final STypeInteger ano = demonstrativo.addFieldInteger("ano", true);
        ano.asAtr().displayString("Demonstrativo Financeiro ${ano}").enabled(false);
        
        final STypeList<STypeComposite<SIComposite>, SIComposite> receitas = demonstrativo.addFieldListOfComposite("receitas", "receita");
        receitas.setView(SViewListByTable::new).disableNew().disableDelete();
        final STypeString tipoReceita = receitas.getElementsType().addFieldString("tipo", true);
        tipoReceita.asAtr().enabled(false);
        final STypeDecimal valorReceita = receitas.getElementsType().addFieldDecimal("valor", true);
        demonstrativo.withInitListener(ins -> {
            final Optional<SIList<SIComposite>> lista = ins.findNearest(receitas);
            for (String tipo : TIPOS_RECEITA) {
                lista.get().addNew().findNearest(tipoReceita).get().setValue(tipo);
            }
        });
        
        demonstrativoCapacidadeSustentabilidadeFinanceira.withInitListener(ins -> {
            final Optional<SIList<SIComposite>> lista = ins.findNearest(demonstrativos);
            for (int i = 0; i < 5; i++) {
                final SIComposite siComposite = lista.get().addNew();
                siComposite.findNearest(ano).get().setValue(LocalDate.now().getYear() + i);
            }
        });
        
        demonstrativos.withView(
            new SViewListByMasterDetail()
                .col("Ano", "${ano}")
                .col("Receitas", ins -> {
                    final Optional<SIList<SIComposite>> lista = ins.findNearest(receitas);
                    BigDecimal total = lista.get().stream().map(siComposite -> siComposite.findNearest(valorReceita).get().getValue())
                        .filter(Objects::nonNull)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                    return total.toString();
                })
                .col("Despesas", "Tipo de referência")
                .col("Total Geral", "Tipo de referência")
                .disableNew().disableDelete())
            .asAtr().label("Demonstrativos").itemLabel("Demonstrativo");
    }
    
    private void addOutros() {
        this.addFieldString("outros", true)
            .withTextAreaView()
            .asAtrBootstrap().maxColPreference();
    }
    
}

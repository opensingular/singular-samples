package br.net.mirante.singular.exemplos.notificacaosimpliciada.domain.enums;

public enum SituacaoNecessitaAnaliseComplementar {

	SIM('S', "Sim"),
	NAO('N', "Não");
	
	public static final String ENUM_CLASS_NAME = "br.gov.anvisa.reg.medicamento.domain.enums.SituacaoNecessitaAnaliseComplementar";
	
	private Character codigo;
	private String descricao;

	private SituacaoNecessitaAnaliseComplementar(Character codigo,
			String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public Character getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static SituacaoNecessitaAnaliseComplementar valueOfEnum(Character codigo) {

		SituacaoNecessitaAnaliseComplementar tipos[] = SituacaoNecessitaAnaliseComplementar.values();

		for (SituacaoNecessitaAnaliseComplementar tipo : tipos) {
			if (codigo != null && tipo.getCodigo().equals( codigo) ){
				return tipo;
			}
		}
		return null;
	}
}

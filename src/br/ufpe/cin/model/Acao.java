package br.ufpe.cin.model;

public class Acao {
	private Variavel variavelAlvo;
	private String tipo;
	private long valorNovo;
	private long valorVelho;
	
	public Acao(Variavel variavelAlvo , long valorNovo) {
		this.variavelAlvo = variavelAlvo;
		this.tipo = StringVariables.ACAO_WRITE.getValue();
		this.valorNovo = valorNovo;
		this.valorVelho = variavelAlvo.getValor();
	}
	
	public Acao(Variavel variavelAlvo) {
		this.variavelAlvo = variavelAlvo;
		this.tipo = StringVariables.ACAO_READ.getValue();
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Variavel getVariavelAlvo() {
		return variavelAlvo;
	}

	public long getValorNovo() {
		return valorNovo;
	}

	public long getValorVelho() {
		return valorVelho;
	}
	
	
}

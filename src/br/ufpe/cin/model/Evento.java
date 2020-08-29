package br.ufpe.cin.model;

public class Evento {
	
	private String tipo;
	private Transacao transacao;
	private CheckPoint checkPoint;
	private Acao acao;
	private Variavel variavel;
	
	public Evento(Transacao transacao) {
		super();
		this.tipo = StringVariables.EVENTO_TRANSACAO.getValue();
		this.transacao = transacao;
	}

	
	public Evento(Transacao transacao, Acao acao) {
		super();
		this.tipo = StringVariables.EVENTO_ACAO.getValue();
		this.transacao = transacao;
		this.acao = acao;
	}


	public Evento(CheckPoint checkPoint) {
		super();
		this.tipo = StringVariables.EVENTO_CHECKPOINT.getValue();
		this.checkPoint = checkPoint;
	}
	
	public Evento(Variavel variavel) {
		super();
		this.tipo = StringVariables.EVENTO_VARIAVEL.getValue();
		this.variavel = variavel;
	}


	public String getTipo() {
		return tipo;
	}


	public Transacao getTransacao() {
		return transacao;
	}


	public CheckPoint getCheckPoint() {
		return checkPoint;
	}


	public Acao getAcao() {
		return acao;
	}


	public Variavel getVariavel() {
		return variavel;
	}
	

}

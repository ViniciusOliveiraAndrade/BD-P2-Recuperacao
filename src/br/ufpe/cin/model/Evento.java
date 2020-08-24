package br.ufpe.cin.model;

public class Evento {
	
	private String tipo;
	private Transacao transacao;
	private CheckPoint checkPoint;
	private Acao acao;
	
	public Evento(Transacao transacao) {
		super();
		this.tipo = "transacao";
		this.transacao = transacao;
	}

	
	public Evento(Transacao transacao, Acao acao) {
		super();
		this.tipo = "acao";
		this.transacao = transacao;
		this.acao = acao;
	}


	public Evento(CheckPoint checkPoint) {
		super();
		this.tipo = "checkpoint";
		this.checkPoint = checkPoint;
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
	
	
	

}

package br.ufpe.cin.model;

public class Acao {
	private Object dadoAlvo;
	private Object tipo;
	private Object valor;
	
	public Acao(Object dadoAlvo, Object tipo, Object valor) {
		super();
		this.dadoAlvo = dadoAlvo;
		this.tipo = tipo;
		this.valor = valor;
	}

	public Object getDadoAlvo() {
		return dadoAlvo;
	}

	public void setDadoAlvo(Object dadoAlvo) {
		this.dadoAlvo = dadoAlvo;
	}

	public Object getTipo() {
		return tipo;
	}

	public void setTipo(Object tipo) {
		this.tipo = tipo;
	}

	public Object getValor() {
		return valor;
	}

	public void setValor(Object valor) {
		this.valor = valor;
	}
	
}

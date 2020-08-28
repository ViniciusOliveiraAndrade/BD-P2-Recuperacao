package br.ufpe.cin.model;

public class Variavel {
	private String nome;
	
	private int valor;
	
	private boolean locked;

	public Variavel(String nome, int valor) {
		super();
		this.nome = nome;
		this.valor = valor;
		this.locked = false;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	
	
	
	
}

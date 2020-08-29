package br.ufpe.cin.model;

public class Variavel {
	
	private String nome;
	
	private long valor;
	
	private boolean locked;
	
	private int tcod;

	public Variavel(String nome, long valor) {
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

	public long getValor() {
		return valor;
	}

	public void setValor(long valor) {
		this.valor = valor;
	}

	public boolean isLocked() {
		return locked;
	}
	
	public void unlock() {
		this.locked = false;
	}

	public void locked(int tcod) {
		this.locked = true;
		this.tcod = tcod;
	}
	
	
	
	
}

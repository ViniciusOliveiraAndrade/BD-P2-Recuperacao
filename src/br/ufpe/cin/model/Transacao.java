package br.ufpe.cin.model;

import java.util.ArrayList;

public class Transacao {
	
	private int cod;
	
	private long tempoInicio;
	private long tempoAtualizacao;
	private long tempoCommit;
	
	private ArrayList<Acao> acoes;
	
	public Transacao(int cod) {
		this.cod = cod;
		this.tempoInicio = System.currentTimeMillis();
		this.acoes = new ArrayList<Acao>();
	}
	
	public boolean addAcao(Acao acao) {
		try {
			this.acoes.add(acao);
			return true;
		}catch (Exception e) {
			return false;
		}
	}
	
	public void updateT(){
		this.tempoAtualizacao = System.currentTimeMillis();
	}
	
	public void commitT(){
		this.tempoCommit = System.currentTimeMillis();
	}

	public int getCod() {
		return cod;
	}

	public void setCod(int cod) {
		this.cod = cod;
	}

	public long getTempoInicio() {
		return tempoInicio;
	}

	public void setTempoInicio(long tempoInicio) {
		this.tempoInicio = tempoInicio;
	}

	public long getTempoAtualizacao() {
		return tempoAtualizacao;
	}

	public void setTempoAtualizacao(long tempoAtualizacao) {
		this.tempoAtualizacao = tempoAtualizacao;
	}

	public long getTempoCommit() {
		return tempoCommit;
	}

	public void setTempoCommit(long tempoCommit) {
		this.tempoCommit = tempoCommit;
	}

	public ArrayList<Acao> getAcoes() {
		return acoes;
	}

	public void setAcoes(ArrayList<Acao> acoes) {
		this.acoes = acoes;
	}

}

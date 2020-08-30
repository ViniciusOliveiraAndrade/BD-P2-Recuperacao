package br.ufpe.cin.model;

public class Checkpoint {
	
	private long tempo;
	private int cod;

	public Checkpoint(int cod) {
		this.tempo = System.currentTimeMillis();
		this.cod = cod;
	}

	public long getTempo() {
		return tempo;
	}

	public void setTempo(long tempo) {
		this.tempo = tempo;
	}

	public int getCod() {
		return cod;
	}

	public void setCod(int cod) {
		this.cod = cod;
	}
	
	
}

package br.ufpe.cin.model;

public class ChekPoint {
	
	private long tempo;

	public ChekPoint() {
		this.tempo = System.currentTimeMillis();
	}

	public long getTempo() {
		return tempo;
	}

	public void setTempo(long tempo) {
		this.tempo = tempo;
	}
	
	
}

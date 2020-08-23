package br.ufpe.cin.view;

import javax.swing.JTabbedPane;

public class TabHolder extends JTabbedPane {

	private static final long serialVersionUID = 1L;
	
	private GerenciadoTransacaoPanel adiada;
	private GerenciadoTransacaoPanel imediataA;
	private GerenciadoTransacaoPanel imediataB;
	
	public TabHolder() {
		this.adiada = new GerenciadoTransacaoPanel();
		this.imediataA = new GerenciadoTransacaoPanel();
		this.imediataB = new GerenciadoTransacaoPanel();
		
		this.addTab("Adiada", this.adiada);
		this.addTab("ImediataA", this.imediataA);
		this.addTab("Imediata B", this.imediataB);
	}
}

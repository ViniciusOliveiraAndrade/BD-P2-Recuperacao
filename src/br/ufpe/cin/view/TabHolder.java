package br.ufpe.cin.view;

import javax.swing.JTabbedPane;

public class TabHolder extends JTabbedPane {

	private static final long serialVersionUID = 1L;
	
	private GerenciadoTransacaoPanel adiada;
	private GerenciadoTransacaoPanel imediataA;
	private GerenciadoTransacaoPanel imediataB;
	
	public TabHolder() {
		adiada = new GerenciadoTransacaoPanel();
		imediataA = new GerenciadoTransacaoPanel();
		imediataB = new GerenciadoTransacaoPanel();
		
		addTab("Adiada", adiada);
		addTab("ImediataA", imediataA);
		addTab("Imediata B", imediataB);
	}
}

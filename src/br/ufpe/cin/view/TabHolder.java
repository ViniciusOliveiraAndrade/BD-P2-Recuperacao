package br.ufpe.cin.view;

import javax.swing.JTabbedPane;

import br.ufpe.cin.control.handlers.AbstractHandler;
import br.ufpe.cin.control.handlers.AdiadaMenuHandler;
import br.ufpe.cin.control.handlers.ImediataAMenuHandler;
import br.ufpe.cin.control.handlers.ImediataBMenuHandler;

public class TabHolder extends JTabbedPane {

	private static final long serialVersionUID = 1L;
	
	private GerenciadoTransacaoPanel adiada;
	private GerenciadoTransacaoPanel imediataA;
	private GerenciadoTransacaoPanel imediataB;
	
	@SuppressWarnings("unused")
	private AbstractHandler adiadaMunuHandler;
	
	@SuppressWarnings("unused")
	private AbstractHandler imediataAMunuHandler;
	
	@SuppressWarnings("unused")
	private AbstractHandler imediataBMunuHandler;
	
	
	public TabHolder() {
		this.adiada = new GerenciadoTransacaoPanel();
		this.imediataA = new GerenciadoTransacaoPanel();
		this.imediataB = new GerenciadoTransacaoPanel();
		
		this.addTab("Adiada", this.adiada);
		this.addTab("ImediataA", this.imediataA);
		this.addTab("Imediata B", this.imediataB);
	
		this.adiadaMunuHandler = new AdiadaMenuHandler(this.adiada);
		this.adiadaMunuHandler = new ImediataAMenuHandler(this.imediataA);
		this.adiadaMunuHandler = new ImediataBMenuHandler(this.imediataB);
		
	}
}

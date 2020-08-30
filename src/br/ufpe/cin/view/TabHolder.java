package br.ufpe.cin.view;

import javax.swing.JTabbedPane;

import br.ufpe.cin.control.handlers.AbstractHandler;
import br.ufpe.cin.control.handlers.AdiadaMenuHandler;
//import br.ufpe.cin.control.handlers.ImediataAMenuHandler;
//import br.ufpe.cin.control.handlers.ImediataBMenuHandler;
import br.ufpe.cin.control.handlers.ImediataAMenuHandler;

public class TabHolder extends JTabbedPane {

	private static final long serialVersionUID = 1L;
	
	private GerenciadorTransacaoPanel adiada;
	private GerenciadorTransacaoPanel imediataA;
	private GerenciadorTransacaoPanel imediataB;
	
	@SuppressWarnings("unused")
	private AbstractHandler adiadaMunuHandler;
	
	@SuppressWarnings("unused")
	private AbstractHandler imediataAMunuHandler;
	
	@SuppressWarnings("unused")
	private AbstractHandler imediataBMunuHandler;
	
	
	public TabHolder() {
		this.adiada = new GerenciadorTransacaoPanel();
		this.imediataA = new GerenciadorTransacaoPanel();
		this.imediataB = new GerenciadorTransacaoPanel();
		
		this.addTab("Adiada", this.adiada);
		this.addTab("ImediataA", this.imediataA);
		this.addTab("Imediata B", this.imediataB);
	
		this.adiadaMunuHandler = new AdiadaMenuHandler(this.adiada);
		this.adiadaMunuHandler = new ImediataAMenuHandler(this.imediataA);
//		this.adiadaMunuHandler = new ImediataBMenuHandler(this.imediataB);
		
	}
}

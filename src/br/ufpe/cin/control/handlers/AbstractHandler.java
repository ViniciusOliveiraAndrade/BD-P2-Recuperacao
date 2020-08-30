package br.ufpe.cin.control.handlers;

import java.awt.event.ActionListener;
import java.util.Observer;

import javax.swing.event.ChangeListener;

import br.ufpe.cin.view.AdicionarAcaoWindow;
import br.ufpe.cin.view.AdicionarVariavelWindow;
import br.ufpe.cin.view.GerenciadorTransacaoPanel;

public abstract class AbstractHandler implements ActionListener, Observer,ChangeListener  {
	
	private GerenciadorTransacaoPanel gerenciadorTransacaoPanel;
	
	private AdicionarAcaoWindow adicionarAcaoWindor;
	
	private AdicionarVariavelWindow adicionarVariavelWindow;
	
	public AbstractHandler(GerenciadorTransacaoPanel gerenciadorTransacaoPanel) {
		this.gerenciadorTransacaoPanel = gerenciadorTransacaoPanel;
	}

	public GerenciadorTransacaoPanel getGerenciadorTransacaoPanel() {
		return gerenciadorTransacaoPanel;
	}

	public void setGerenciadorTransacaoPanel(GerenciadorTransacaoPanel gerenciadorTransacaoPanel) {
		this.gerenciadorTransacaoPanel = gerenciadorTransacaoPanel;
	}

	public AdicionarAcaoWindow getAdicionarAcaoWindow() {
		return adicionarAcaoWindor;
	}

	public void setAdicionarAcaoWindow(AdicionarAcaoWindow adiconarAcaoWindow) {
		this.adicionarAcaoWindor = adiconarAcaoWindow;
	}

	public AdicionarVariavelWindow getAdicionarVariavelWindow() {
		return adicionarVariavelWindow;
	}

	public void setAdicionarVariavelWindow(AdicionarVariavelWindow adicionarVariavelWindow) {
		this.adicionarVariavelWindow = adicionarVariavelWindow;
	}
	
	
}

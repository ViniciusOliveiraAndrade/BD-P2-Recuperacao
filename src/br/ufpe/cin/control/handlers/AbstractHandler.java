package br.ufpe.cin.control.handlers;

import java.awt.event.ActionListener;
import java.util.Observer;

import br.ufpe.cin.view.GerenciadoTransacaoPanel;

public abstract class AbstractHandler implements ActionListener, Observer {
	
	private GerenciadoTransacaoPanel gtp;

	public AbstractHandler(GerenciadoTransacaoPanel gtp) {
		this.gtp = gtp;
	}

	public GerenciadoTransacaoPanel getGtp() {
		return gtp;
	}

	public void setGtp(GerenciadoTransacaoPanel gtp) {
		this.gtp = gtp;
	}
	
	
}

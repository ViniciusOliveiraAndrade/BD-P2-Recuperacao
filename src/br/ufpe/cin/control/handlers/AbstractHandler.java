package br.ufpe.cin.control.handlers;

import java.awt.event.ActionListener;
import java.util.Observer;

import br.ufpe.cin.view.AdicionarAcaoWindow;
import br.ufpe.cin.view.AdicionarVariavelWindow;
import br.ufpe.cin.view.GerenciadoTransacaoPanel;

public abstract class AbstractHandler implements ActionListener, Observer {
	
	private GerenciadoTransacaoPanel gtp;
	
	private AdicionarAcaoWindow aaw;
	
	private AdicionarVariavelWindow avw;
	
	
	public AbstractHandler(GerenciadoTransacaoPanel gtp) {
		this.gtp = gtp;
	}

	public GerenciadoTransacaoPanel getGtp() {
		return gtp;
	}

	public void setGtp(GerenciadoTransacaoPanel gtp) {
		this.gtp = gtp;
	}

	public AdicionarAcaoWindow getAaw() {
		return aaw;
	}

	public void setAaw(AdicionarAcaoWindow aaw) {
		this.aaw = aaw;
	}

	public AdicionarVariavelWindow getAvw() {
		return avw;
	}

	public void setAvw(AdicionarVariavelWindow avw) {
		this.avw = avw;
	}
	
	
}

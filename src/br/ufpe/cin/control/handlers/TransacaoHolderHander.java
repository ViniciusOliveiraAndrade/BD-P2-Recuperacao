package br.ufpe.cin.control.handlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import br.ufpe.cin.view.TransacaoHolder;

public class TransacaoHolderHander extends Observable implements ActionListener{

	private TransacaoHolder transa��oHolder;
	
	public TransacaoHolderHander(AbstractHandler ah, TransacaoHolder th) {
		
		this.addObserver(ah);
		this.transa��oHolder = th;
		this.transa��oHolder.getIniciarTransacaoButton().addActionListener(this);
		this.transa��oHolder.getAdicionarAcaoButton().addActionListener(this);
		this.transa��oHolder.getAbortarTransacaoButton().addActionListener(this);
		this.transa��oHolder.getCommitTransacaoButton().addActionListener(this);
	}
	
	public TransacaoHolder getTransa��oHolder() {
		return transa��oHolder;
	}

	private void notificar(String tipo) {
		this.setChanged();
		this.notifyObservers(tipo);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.transa��oHolder.getIniciarTransacaoButton()) {
			this.notificar("iniciar");
		}
		if(e.getSource() == this.transa��oHolder.getAdicionarAcaoButton()) {
			this.notificar("acao");
		}
		if(e.getSource() == this.transa��oHolder.getAbortarTransacaoButton()) {
			this.notificar("abortar");
		}
		if(e.getSource() == this.transa��oHolder.getCommitTransacaoButton()) {
			this.notificar("commit");
		}
		
	}

}

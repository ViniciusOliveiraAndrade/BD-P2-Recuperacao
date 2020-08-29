package br.ufpe.cin.control.handlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import br.ufpe.cin.view.TransacaoHolder;

public class TransacaoHolderHander extends Observable implements ActionListener{

	private TransacaoHolder transaçãoHolder;
	
	public TransacaoHolderHander(AbstractHandler ah, TransacaoHolder th) {
		
		this.addObserver(ah);
		this.transaçãoHolder = th;
		this.transaçãoHolder.getIniciarTransacaoButton().addActionListener(this);
		this.transaçãoHolder.getAdicionarAcaoButton().addActionListener(this);
		this.transaçãoHolder.getAbortarTransacaoButton().addActionListener(this);
		this.transaçãoHolder.getCommitTransacaoButton().addActionListener(this);
	}
	
	public TransacaoHolder getTransaçãoHolder() {
		return transaçãoHolder;
	}

	private void notificar(String tipo) {
		this.setChanged();
		this.notifyObservers(tipo);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.transaçãoHolder.getIniciarTransacaoButton()) {
			this.notificar("iniciar");
		}
		if(e.getSource() == this.transaçãoHolder.getAdicionarAcaoButton()) {
			this.notificar("acao");
		}
		if(e.getSource() == this.transaçãoHolder.getAbortarTransacaoButton()) {
			this.notificar("abortar");
		}
		if(e.getSource() == this.transaçãoHolder.getCommitTransacaoButton()) {
			this.notificar("commit");
		}
		
	}

}

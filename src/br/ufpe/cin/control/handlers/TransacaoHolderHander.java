package br.ufpe.cin.control.handlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import br.ufpe.cin.model.StringVariables;
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
		
		this.transaçãoHolder.getAdicionarAcaoButton().setVisible(false);
		this.transaçãoHolder.getAbortarTransacaoButton().setVisible(false);
		this.transaçãoHolder.getCommitTransacaoButton().setVisible(false);
	}
	
	public TransacaoHolder getTransaçãoHolder() {
		return transaçãoHolder;
	}

	private void notificar(String tipo) {
		this.setChanged();
		this.notifyObservers(tipo);
	}
	
	private void iniciarTransacao() {
		this.notificar(StringVariables.TRANSACAO_INICIO.getValue());
		this.transaçãoHolder.getIniciarTransacaoButton().setVisible(false);
		this.transaçãoHolder.getAdicionarAcaoButton().setVisible(true);
		this.transaçãoHolder.getAbortarTransacaoButton().setVisible(true);
		this.transaçãoHolder.getCommitTransacaoButton().setVisible(true);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.transaçãoHolder.getIniciarTransacaoButton()) {
			this.iniciarTransacao();
		}
		if(e.getSource() == this.transaçãoHolder.getAdicionarAcaoButton()) {
			this.notificar(StringVariables.TRANSACAO_ACAO.getValue());
		}
		if(e.getSource() == this.transaçãoHolder.getAbortarTransacaoButton()) {
			this.notificar(StringVariables.TRANSACAO_ABORT.getValue());
		}
		if(e.getSource() == this.transaçãoHolder.getCommitTransacaoButton()) {
			this.notificar(StringVariables.TRANSACAO_COMMIT.getValue());
		}
		
	}

}

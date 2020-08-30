package br.ufpe.cin.control.handlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import br.ufpe.cin.model.StringVariables;
import br.ufpe.cin.view.TransacaoHolder;

public class TransacaoHolderHandler extends Observable implements ActionListener{

	private TransacaoHolder transacaoHolder;
	
	public TransacaoHolderHandler(AbstractHandler abstractHandler, TransacaoHolder transacaoHolder) {
		
		this.addObserver(abstractHandler);
		this.transacaoHolder = transacaoHolder;
		this.transacaoHolder.getIniciarTransacaoButton().addActionListener(this);
		this.transacaoHolder.getAdicionarAcaoButton().addActionListener(this);
		this.transacaoHolder.getAbortarTransacaoButton().addActionListener(this);
		this.transacaoHolder.getCommitTransacaoButton().addActionListener(this);
		
		this.transacaoHolder.getAdicionarAcaoButton().setVisible(false);
		this.transacaoHolder.getAbortarTransacaoButton().setVisible(false);
		this.transacaoHolder.getCommitTransacaoButton().setVisible(false);
	}
	
	public TransacaoHolder getTransacaoHolder() {
		return transacaoHolder;
	}

	private void notificar(String tipo) {
		this.setChanged();
		this.notifyObservers(tipo);
	}
	
	private void iniciarTransacao() {
		this.notificar(StringVariables.TRANSACAO_INICIO.getValue());
		this.transacaoHolder.getIniciarTransacaoButton().setVisible(false);
		this.transacaoHolder.getAdicionarAcaoButton().setVisible(true);
		this.transacaoHolder.getAbortarTransacaoButton().setVisible(true);
		this.transacaoHolder.getCommitTransacaoButton().setVisible(true);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent evento) {
		if(evento.getSource() == this.transacaoHolder.getIniciarTransacaoButton()) {
			this.iniciarTransacao();
		}
		if(evento.getSource() == this.transacaoHolder.getAdicionarAcaoButton()) {
			this.notificar(StringVariables.TRANSACAO_ACAO.getValue());
		}
		if(evento.getSource() == this.transacaoHolder.getAbortarTransacaoButton()) {
			this.notificar(StringVariables.TRANSACAO_ABORT.getValue());
		}
		if(evento.getSource() == this.transacaoHolder.getCommitTransacaoButton()) {
			this.notificar(StringVariables.TRANSACAO_COMMIT.getValue());
		}
		
	}

}

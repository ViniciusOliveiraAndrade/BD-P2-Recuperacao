package br.ufpe.cin.control.handlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import br.ufpe.cin.model.StringVariables;
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
		
		this.transa��oHolder.getAdicionarAcaoButton().setVisible(false);
		this.transa��oHolder.getAbortarTransacaoButton().setVisible(false);
		this.transa��oHolder.getCommitTransacaoButton().setVisible(false);
	}
	
	public TransacaoHolder getTransa��oHolder() {
		return transa��oHolder;
	}

	private void notificar(String tipo) {
		this.setChanged();
		this.notifyObservers(tipo);
	}
	
	private void iniciarTransacao() {
		this.notificar(StringVariables.TRANSACAO_INICIO.getValue());
		this.transa��oHolder.getIniciarTransacaoButton().setVisible(false);
		this.transa��oHolder.getAdicionarAcaoButton().setVisible(true);
		this.transa��oHolder.getAbortarTransacaoButton().setVisible(true);
		this.transa��oHolder.getCommitTransacaoButton().setVisible(true);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.transa��oHolder.getIniciarTransacaoButton()) {
			this.iniciarTransacao();
		}
		if(e.getSource() == this.transa��oHolder.getAdicionarAcaoButton()) {
			this.notificar(StringVariables.TRANSACAO_ACAO.getValue());
		}
		if(e.getSource() == this.transa��oHolder.getAbortarTransacaoButton()) {
			this.notificar(StringVariables.TRANSACAO_ABORT.getValue());
		}
		if(e.getSource() == this.transa��oHolder.getCommitTransacaoButton()) {
			this.notificar(StringVariables.TRANSACAO_COMMIT.getValue());
		}
		
	}

}

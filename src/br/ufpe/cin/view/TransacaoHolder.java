package br.ufpe.cin.view;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import br.ufpe.cin.model.Transacao;

public class TransacaoHolder extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private Transacao transacao;
	
	private JLabel indicadorLabel;

	private JButton iniciarTransacaoButton;
	private JButton adicionarAcaoButton;
	private JButton abortarTransacaoButton;	
	private JButton commitTransacaoButton;

	public TransacaoHolder(Transacao transacao) {
		this.transacao = transacao;
		
		this.indicadorLabel = new JLabel("T"+transacao.getCod());

		this.iniciarTransacaoButton = new JButton("Iniciar Transação");
		this.adicionarAcaoButton = new JButton("Adicionar Ação");
		this.abortarTransacaoButton = new JButton("Abortar Transação");
		this.commitTransacaoButton = new JButton("Commit Transação");
		
		this.add(this.indicadorLabel);
		
		this.add(this.iniciarTransacaoButton);
		this.add(this.adicionarAcaoButton);
		this.add(this.abortarTransacaoButton);
		this.add(this.commitTransacaoButton);

		this.setBackground(Color.WHITE);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
	}

	public Transacao getTransacao() {
		return transacao;
	}

	public JLabel getIndicadorLabel() {
		return indicadorLabel;
	}

	public JButton getIniciarTransacaoButton() {
		return iniciarTransacaoButton;
	}

	public JButton getAdicionarAcaoButton() {
		return adicionarAcaoButton;
	}

	public JButton getAbortarTransacaoButton() {
		return abortarTransacaoButton;
	}

	public JButton getCommitTransacaoButton() {
		return commitTransacaoButton;
	}
	
}

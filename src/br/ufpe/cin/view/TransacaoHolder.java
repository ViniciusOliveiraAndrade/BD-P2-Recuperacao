package br.ufpe.cin.view;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import br.ufpe.cin.model.Transacao;

public class TransacaoHolder extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private Transacao t;
	
	private JLabel indicadorLabel;
	
	private JButton addAcaoButton;
	private JButton iniciarTransacaoButton;
	private JButton abortarButton;	
	private JButton commitButton;

	public TransacaoHolder(Transacao t) {
		this.t = t;
		
		this.indicadorLabel = new JLabel("T"+t.getCod());
		
		this.addAcaoButton = new JButton("Add Ação");
		this.iniciarTransacaoButton = new JButton("Iniciar");
		this.abortarButton = new JButton("Abortar");
		this.commitButton = new JButton("Commit");
		
		this.add(this.indicadorLabel);
		this.add(this.addAcaoButton);
		this.add(this.iniciarTransacaoButton);
		this.add(this.abortarButton);
		this.add(this.commitButton);
		
		this.setBorder(BorderFactory.createLineBorder(Color.black));
	}

	public Transacao getT() {
		return t;
	}

	public JLabel getIndicadorLabel() {
		return indicadorLabel;
	}
	
	public JButton getAddAcaoButton() {
		return addAcaoButton;
	}
	
	public JButton getIniciarTransacaoButton() {
		return iniciarTransacaoButton;
	}
	
	public JButton getAbortarButton() {
		return abortarButton;
	}

	public JButton getCommitButton() {
		return commitButton;
	}
	
}

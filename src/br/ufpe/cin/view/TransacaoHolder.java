package br.ufpe.cin.view;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import br.ufpe.cin.model.Transacao;

public class TransacaoHolder extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private Transacao t;
	
	private JLabel indicadorLabel;
	
	private JButton updateButton;
	
	private JButton commitButton;

	public TransacaoHolder(Transacao t) {
		this.t = t;
		
		this.indicadorLabel = new JLabel("T"+t.getCod());
		
		this.updateButton = new JButton("Update");
		this.commitButton = new JButton("Commit");
		
		this.add(this.indicadorLabel);
		this.add(this.updateButton);
		this.add(this.commitButton);
	}

	public Transacao getT() {
		return t;
	}

	public JLabel getIndicadorLabel() {
		return indicadorLabel;
	}

	public JButton getUpdateButton() {
		return updateButton;
	}

	public JButton getCommitButton() {
		return commitButton;
	}
	
}

package br.ufpe.cin.view;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MenuPanel  extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private JButton addVariavelButton;
	private JButton addTransacaoButton;
	private JButton estouroMemoriaButton;
	private JButton checkPointButton;
	private JButton recuperarButton;
	
	public MenuPanel() {
		this.setLayout(new GridLayout(0, 1));
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),""));
		
		this.addVariavelButton = new JButton("Adicionar Variável");
		
		this.addTransacaoButton = new JButton("Adicionar Transação");
//		this.addButton.setIcon(new ImageIcon("res/addIcon.png"));
		
		this.estouroMemoriaButton = new JButton("Estouro de Memória");
		
		this.checkPointButton = new JButton("Checkpoint");
//		this.checkPointButton.setIcon(new ImageIcon("res/checkPointIcon.png"));
		
		this.recuperarButton = new JButton("Recuperar Falhas");
//		this.recuperarButton.setIcon(new ImageIcon("res/recoverIcon.png"));
		
		this.add(this.addVariavelButton);
		this.add(this.addTransacaoButton);
		this.add(this.estouroMemoriaButton);
		this.add(this.checkPointButton);
		this.add(this.recuperarButton);
	}


	public JButton getAddVariavelButton() {
		return addVariavelButton;
	}
	
	public JButton getAddTransacaoButton() {
		return addTransacaoButton;
	}

	public JButton getEstouroMemoriaButton() {
		return estouroMemoriaButton;
	}
	
	public JButton getCheckPointButton() {
		return checkPointButton;
	}

	public JButton getRecuperarButton() {
		return recuperarButton;
	}

}

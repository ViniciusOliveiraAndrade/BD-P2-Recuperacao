package br.ufpe.cin.view;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MenuPanel  extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private JButton addButton;
	private JButton checkPointButton;
	private JButton falhaButton;
	private JButton recuperarButton;
	
	public MenuPanel() {
		this.setLayout(new GridLayout(0, 2, 15, 15));
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),""));
		
		this.addButton = new JButton("Add Transação");
		this.addButton.setIcon(new ImageIcon("res/addIcon.png"));
		
		this.checkPointButton = new JButton("Check Point");
		this.checkPointButton.setIcon(new ImageIcon("res/checkPointIcon.png"));
		
		this.falhaButton = new JButton("Gerar Falha");
		this.falhaButton.setIcon(new ImageIcon("res/failIcon.png"));
		
		this.recuperarButton = new JButton("Recuperar");
		this.recuperarButton.setIcon(new ImageIcon("res/recoverIcon.png"));
		
		
		this.add(this.addButton);
		this.add(this.checkPointButton);
		this.add(this.falhaButton);
		this.add(this.recuperarButton);
	}


	public JButton getAddButton() {
		return addButton;
	}


	public JButton getCheckPointButton() {
		return checkPointButton;
	}


	public JButton getFalhaButton() {
		return falhaButton;
	}


	public JButton getRecuperarButton() {
		return recuperarButton;
	}

}

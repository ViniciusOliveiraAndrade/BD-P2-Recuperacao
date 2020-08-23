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
		setLayout(new GridLayout(0, 2, 15, 15));
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),""));
		
		addButton = new JButton("Add Transação");
		addButton.setIcon(new ImageIcon("res/addIcon.png"));
		
		checkPointButton = new JButton("Check Point");
		checkPointButton.setIcon(new ImageIcon("res/checkPointIcon.png"));
		
		falhaButton = new JButton("Gerar Falha");
		falhaButton.setIcon(new ImageIcon("res/failIcon.png"));
		
		recuperarButton = new JButton("Recuperar");
		recuperarButton.setIcon(new ImageIcon("res/recoverIcon.png"));
		
		
		add(addButton);
		add(checkPointButton);
		add(falhaButton);
		add(recuperarButton);
	}
	
	

}

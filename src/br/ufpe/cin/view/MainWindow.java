package br.ufpe.cin.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class MainWindow extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	private TabHolder tabHolder;
	

//	private InfoPanel infoPanel;
	
	public MainWindow() {
		this.setSize(900, 600);
		this.setTitle("Recuparação de Trasação");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setExtendedState(MAXIMIZED_BOTH);
	
//		this.infoPanel = new InfoPanel();
		
		this.tabHolder = new TabHolder();
		
//		add(infoPanel, BorderLayout.PAGE_START);
		this.add(this.tabHolder,BorderLayout.CENTER);
		
		this.setVisible(true);
	}
}

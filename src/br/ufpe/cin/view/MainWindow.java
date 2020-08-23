package br.ufpe.cin.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class MainWindow extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	private TabHolder tabHolder;
	

	private InfoPanel infoPanel;
	
	public MainWindow() {
		setSize(900, 600);
		setTitle("Recuparação de Trasação");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(MAXIMIZED_BOTH);
	
		infoPanel = new InfoPanel();
		
		tabHolder = new TabHolder();
		
		add(infoPanel, BorderLayout.PAGE_START);
		add(tabHolder,BorderLayout.CENTER);
		
		setVisible(true);
	}
}

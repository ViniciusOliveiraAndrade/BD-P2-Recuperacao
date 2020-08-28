package br.ufpe.cin.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class MainWindow extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	private TabHolder tabHolder;
	
	public MainWindow() {
		this.setSize(1382, 706);
		this.setTitle("Recuparação de Trasação");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setExtendedState(MAXIMIZED_BOTH);
		
		this.tabHolder = new TabHolder();
		
		getContentPane().add(this.tabHolder,BorderLayout.CENTER);
		
		this.setVisible(true);
	}
}

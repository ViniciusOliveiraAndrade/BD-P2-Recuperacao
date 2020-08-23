package br.ufpe.cin.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class MemoriaHolder extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private JScrollPane scroll;
	int index;
	
	public MemoriaHolder(String titulo) {
		setLayout(new GridLayout(0, 1, 3, 3));
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),titulo));
		index = 0;
        
		scroll = new JScrollPane(this);
        
	}
	
	public JScrollPane getPanel() {
		   return scroll;
	   }
	
	public void addInformacao(){
        String tipo="oi"; 
        
        try {
        	this.add(new JLabel(tipo+ ++index));
     	   	this.update();
     	   	
         } catch (Exception e) {
            e.printStackTrace();
            
            JOptionPane.showMessageDialog(this, "Não foi possivel Adicionar");
        }
    }
	
	private void update() {
		this.revalidate();
 	   	this.repaint();
	}
	public void removerComponent(Component comp) {
		this.remove(comp);
		this.update();
	}
}

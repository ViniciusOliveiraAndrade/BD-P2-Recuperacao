package br.ufpe.cin.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class MemoriaHolder extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private JScrollPane scroll;
	private JPanel bord;
	
	public MemoriaHolder(String titulo) {
		bord = new JPanel();
		bord.setLayout(new BorderLayout());
		bord.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),titulo));

		this.setLayout(new GridLayout(0, 1, 3, 3));		
        
		this.scroll = new JScrollPane(this);
        
		bord.add(scroll,BorderLayout.CENTER);
	}
	
	public JPanel getPanel() {
		   return bord;
	   }
	
	
	public void addTransacao(TransacaoHolder transacaoHolder){
        try {
        	this.add(transacaoHolder);
     	   	this.update();
     	   	this.scrollDown();
     	   
         } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Não foi possível adicionar a transação "+ transacaoHolder.getIndicadorLabel().getText());
        }
    }
	
	
	public void addEvento(EventoHolder eventoHolder){
        try {
        	this.add(eventoHolder);
     	   	this.update();
     	   	this.scrollDown();
     	   
         } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Não foi possível adicionar o evento "+ eventoHolder.getText());
        }
    }
	
	private void scrollDown() {
		Rectangle rectangle = this.getBounds();
 	   	rectangle.y = (rectangle.height + rectangle.height) + rectangle.y;
 	   	this.scrollRectToVisible(rectangle);
	}
	
	public void update() {
		this.revalidate();
 	   	this.repaint();
	}
	public void removerComponent(Component component) {
		this.remove(component);
		this.update();
	}
}

package br.ufpe.cin.view;

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
	
	public MemoriaHolder(String titulo) {
		this.setLayout(new GridLayout(0, 1, 3, 3));
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),titulo));
        
		this.scroll = new JScrollPane(this);
        
	}
	
	public JScrollPane getPanel() {
		   return scroll;
	   }
	
	
	public void addTransacao(TransacaoHolder t){
        try {
        	this.add(t);
     	   	this.update();
     	   	this.scrollDown();
     	   
         } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Não foi possível adicionar a transação "+ t.getIndicadorLabel().getText());
        }
    }
	
	
	public void addEvento(EventoHolder ev){
        try {
        	this.add(ev);
     	   	this.update();
     	   	this.scrollDown();
     	   
         } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Não foi possível adicionar o evento "+ ev.getText());
        }
        System.out.println("entrou");
    }
	
	private void scrollDown() {
		Rectangle r = this.getBounds();
 	   	r.y = (r.height + r.height) + r.y;
 	   	this.scrollRectToVisible(r);
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

package br.ufpe.cin.view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public class TransacaoHolder extends JPanel {
	private static final long serialVersionUID = 1L;
	private MemoriaHolder log;
	public TransacaoHolder() {
		log = new MemoriaHolder("Log");

		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(e.getButton() == 1) {
					log.addInformacao();
				}
			}
		});
		add(log.getPanel());
	}
		
	
}

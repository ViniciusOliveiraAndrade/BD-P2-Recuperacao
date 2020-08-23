package br.ufpe.cin.view;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public class GerenciadoTransacaoPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	private MemoriaHolder cache;
	private MemoriaHolder log;
	private MemoriaHolder disco;
//	private MemoriaHolder disco;
	
	
	public GerenciadoTransacaoPanel() {
		setLayout(new GridLayout(0, 2, 5, 5));
		cache = new MemoriaHolder("cache");
		log = new MemoriaHolder("Log");
		disco = new MemoriaHolder("Disco");
//		disco = new MemoriaHolder("Disco");
		
		add(cache.getPanel());
		add(log.getPanel());
		add(disco.getPanel());
//		add(disco.getPanel());
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(e.getButton() == 1) {
					log.addInformacao();
				}
			}
		});
	}

}

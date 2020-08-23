package br.ufpe.cin.view;

import java.awt.GridLayout;

import javax.swing.JPanel;

public class GerenciadoTransacaoPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	private MemoriaHolder cacheHolder;
	private MemoriaHolder logHolder;
	private MemoriaHolder discoHolder;
	private MenuPanel menuHolder;
	
	public GerenciadoTransacaoPanel() {
		this.setLayout(new GridLayout(0, 2, 5, 5));
		this.cacheHolder = new MemoriaHolder("cache");
		this.logHolder = new MemoriaHolder("Log");
		this.discoHolder = new MemoriaHolder("Disco");
		this.menuHolder = new MenuPanel();
		
		this.add(this.cacheHolder.getPanel());
		this.add(this.logHolder.getPanel());
		this.add(this.discoHolder.getPanel());
		this.add(this.menuHolder);
	}

	public MemoriaHolder getCacheHolder() {
		return cacheHolder;
	}

	public MemoriaHolder getLogHolder() {
		return logHolder;
	}

	public MemoriaHolder getDiscoHolder() {
		return discoHolder;
	}

	public MenuPanel getMenuHolder() {
		return menuHolder;
	}

}

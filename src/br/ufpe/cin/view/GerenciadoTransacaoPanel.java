package br.ufpe.cin.view;

import java.awt.GridLayout;

import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

public class GerenciadoTransacaoPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	private MemoriaHolder transacoesHolder;
	private MemoriaHolder cacheHolder;
	private MemoriaHolder logMemoriaHolder;
	private MemoriaHolder logDiscoHolder;
	private MemoriaHolder discoHolder;
	private MenuPanel menuHolder;
	
	public GerenciadoTransacaoPanel() {
		this.transacoesHolder = new MemoriaHolder("Transações");
		this.menuHolder = new MenuPanel();
		this.setLayout(new GridLayout(3,2));
	
		this.cacheHolder = new MemoriaHolder("Cache");
		this.logMemoriaHolder = new MemoriaHolder("Log de Memória");
		this.discoHolder = new MemoriaHolder("Disco");
		this.logDiscoHolder = new MemoriaHolder("Log de Disco");
		
		this.add(menuHolder);
		this.add(transacoesHolder.getPanel());
		add(this.cacheHolder.getPanel());
		add(this.logMemoriaHolder.getPanel());
		add(this.discoHolder.getPanel());
		add(this.logDiscoHolder.getPanel());
		
	}
	
	public MemoriaHolder getTransacoesHolder() {
		return transacoesHolder;
	}

	public MemoriaHolder getCacheHolder() {
		return cacheHolder;
	}

	public MemoriaHolder getLogMemoriaHolder() {
		return logMemoriaHolder;
	}
	
	public MemoriaHolder getLogDiscoHolder() {
		return logDiscoHolder;
	}

	public MemoriaHolder getDiscoHolder() {
		return discoHolder;
	}

	public MenuPanel getMenuHolder() {
		return menuHolder;
	}

}

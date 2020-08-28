package br.ufpe.cin.view;

import java.awt.GridLayout;

import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GerenciadoTransacaoPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	private MemoriaHolder transacoesHolder;
	private MemoriaHolder cacheHolder;
	private MemoriaHolder logMemoriaHolder;
	private MemoriaHolder logDiscoHolder;
	private MemoriaHolder discoHolder;
	private MenuPanel menuHolder;
	private MemoriaHolder variaveisHolder;
	
	public GerenciadoTransacaoPanel() {
		this.setLayout(new GridLayout(0, 2, 5, 5));
		this.transacoesHolder = new MemoriaHolder("Transações");
		this.cacheHolder = new MemoriaHolder("Cache");
		this.logMemoriaHolder = new MemoriaHolder("Log de Memória");
		this.discoHolder = new MemoriaHolder("Disco");
		this.logDiscoHolder = new MemoriaHolder("Log de Disco");
		this.variaveisHolder = new MemoriaHolder("Variáveis Iniciais");
		
		this.add(this.transacoesHolder.getPanel());
		this.add(this.cacheHolder.getPanel());
		this.add(this.logMemoriaHolder.getPanel());
		this.add(this.discoHolder.getPanel());
		this.add(this.logDiscoHolder.getPanel());
		this.menuHolder = new MenuPanel();
		add(menuHolder);
		menuHolder.getRecuperarButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		this.add(this.variaveisHolder.getPanel());
	}
	
	public MemoriaHolder getTransacoes() {
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
	
	public MemoriaHolder getVariaveisHolder() {
		return variaveisHolder;
	}

	public MenuPanel getMenuHolder() {
		return menuHolder;
	}

}

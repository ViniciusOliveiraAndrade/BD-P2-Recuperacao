package br.ufpe.cin.control.handlers;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import br.ufpe.cin.model.CheckPoint;
import br.ufpe.cin.model.Evento;
import br.ufpe.cin.model.Transacao;
import br.ufpe.cin.view.EventoHolder;
import br.ufpe.cin.view.GerenciadoTransacaoPanel;
import br.ufpe.cin.view.TransacaoHolder;

public class AdiadaMenuHandler extends AbstractHandler {
	
	private ArrayList<TransacaoHolder> transacoes;
	private ArrayList<Evento> eventos;
	private int tCount;
	private int cpCount;
	
	public AdiadaMenuHandler(GerenciadoTransacaoPanel gtp) {
		super(gtp);
		
		this.getGtp().getMenuHolder().getAddButton().addActionListener(this);
		this.getGtp().getMenuHolder().getCheckPointButton().addActionListener(this);
		
		this.transacoes = new ArrayList<TransacaoHolder>();
		this.eventos = new ArrayList<Evento>();
		
		this.tCount = 0;
		this.cpCount = 0;
	}
	
	private void addEvento(CheckPoint cp){
		Evento e = new Evento(cp);
		this.eventos.add(e);
		this.getGtp().getLogHolder().addEvento(new EventoHolder(e));
	}
	
	private void addEvento(Transacao t){
		Evento e = new Evento(t);
		this.eventos.add(e);
		this.getGtp().getLogHolder().addEvento(new EventoHolder(e));
	}
	
	private void addTransacao() {
		Transacao t = new Transacao(this.tCount ++);
		TransacaoHolder th = new TransacaoHolder(t);
		this.getGtp().getCacheHolder().addTransacao(th);
		this.transacoes.add(th);
		this.addEvento(t);
		
	}
	
	private void addCheckpoint() {
		CheckPoint cp = new CheckPoint(this.cpCount ++);
		this.addEvento(cp);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==this.getGtp().getMenuHolder().getAddButton()) {
			
			this.addTransacao();
			
		}
		if (e.getSource()==this.getGtp().getMenuHolder().getCheckPointButton()) {
			this.addCheckpoint();
		}
		
	}

}

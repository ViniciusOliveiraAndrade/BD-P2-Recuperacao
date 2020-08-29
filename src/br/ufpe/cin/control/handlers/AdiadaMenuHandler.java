package br.ufpe.cin.control.handlers;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Observable;

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
	
//	private adicionarVarivelWindow;
//	private adicionarAcaoWindow;
	
	public AdiadaMenuHandler(GerenciadoTransacaoPanel gtp) {
		super(gtp);
		
		this.getGtp().getMenuHolder().getAddTransacaoButton().addActionListener(this);
		this.getGtp().getMenuHolder().getCheckPointButton().addActionListener(this);
		
		this.transacoes = new ArrayList<TransacaoHolder>();
		this.eventos = new ArrayList<Evento>();
		
		this.tCount = 0;
		this.cpCount = 0;
	}
	
	private void addEventoLogDisco(CheckPoint cp){
		Evento e = new Evento(cp);
		this.eventos.add(e);
		this.getGtp().getLogDiscoHolder().addEvento(new EventoHolder(e));

	}
	
	private void addEventoLogMemoria(Transacao t){
		Evento e = new Evento(t);
		this.eventos.add(e);
		this.getGtp().getDiscoHolder().addEvento(new EventoHolder(e));
	}
	
	private void adicionarTransacaoMemoria() {
		Transacao t = new Transacao(this.tCount ++);
		TransacaoHolder th = new TransacaoHolder(t);
		this.getGtp().getCacheHolder().addTransacao(th);
		this.transacoes.add(th);
		this.addEventoLogMemoria(t);
		
	}
	
	private void adicionarCheckpoint() {
		CheckPoint cp = new CheckPoint(this.cpCount ++);
		this.addEventoLogDisco(cp);
	}
	
	
	
	
	/**
	 * 
	 * GTP - Adicionar variavel
	 * GTP - Adicionar transa��o
	 * GTP - Estouro de Mem�ria
	 * GTP - CheckPoint
	 * GTP - Recuperar falha
	 * AV - OK
	 * T - Adicionar A��o
	 * T - Iniciar Transa��o
	 * T - Abortar
	 * T - Commit
	 * T - /UPDATE/
	 * 
	 * */
	
	
	@Override
	public void actionPerformed(ActionEvent e) {

//		GerenciadoTransacaoPanel
//		if (e.getSource()==this.getGtp().getMenuHolder().getAdicionarVariavelButton()) {
//			this.adicionarVariavel();	
//		}
//		if (e.getSource()==this.getGtp().getMenuHolder().getAdicionarTransacaoButton()) {
//			this.adicionarTransacao();	
//		}
//		if (e.getSource()==this.getGtp().getMenuHolder().getEstourarMemoriaButton()) {
//			this.estourarMemoria();
//		}

		if (e.getSource()==this.getGtp().getMenuHolder().getCheckPointButton()) {
			this.adicionarCheckpoint();
		}
//		if (e.getSource()==this.getGtp().getMenuHolder().getRecuperarFalhaButton()) {
//			this.recuperarFalha();	
//		}
		
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof TransacaoHolderHander) {
            String tipo = (String)arg;
			
            switch (tipo) {
			case "iniciar":
				
				break;
			case "acao":
				
				break;
			case "abortar":
				
				break;
			case "commit":
				
				break;
			default:
				break;
			}
		}
		
	}

}

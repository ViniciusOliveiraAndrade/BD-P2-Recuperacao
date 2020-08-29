package br.ufpe.cin.control.handlers;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Observable;

import br.ufpe.cin.model.CheckPoint;
import br.ufpe.cin.model.Evento;
import br.ufpe.cin.model.Transacao;
import br.ufpe.cin.model.Variavel;
import br.ufpe.cin.view.AdicionarVariavelWindow;
import br.ufpe.cin.view.EventoHolder;
import br.ufpe.cin.view.GerenciadoTransacaoPanel;
import br.ufpe.cin.view.TransacaoHolder;

public class AdiadaMenuHandler extends AbstractHandler {

	private ArrayList<TransacaoHolder> transacoes;
	private ArrayList<Evento> eventos;
	private ArrayList<Variavel> variaveisCache;
	private ArrayList<Variavel> variaveisDisco;

	private int tCount;
	private int cpCount;

//	private adicionarVarivelWindow;
//	private adicionarAcaoWindow;

	public AdiadaMenuHandler(GerenciadoTransacaoPanel gtp) {
		super(gtp);

		this.getGtp().getMenuHolder().getAddVariavelButton().addActionListener(this);
		this.getGtp().getMenuHolder().getAddTransacaoButton().addActionListener(this);
		this.getGtp().getMenuHolder().getEstouroMemoriaButton().addActionListener(this);
		this.getGtp().getMenuHolder().getCheckPointButton().addActionListener(this);
		this.getGtp().getMenuHolder().getRecuperarButton().addActionListener(this);

		this.transacoes = new ArrayList<TransacaoHolder>();
		this.eventos = new ArrayList<Evento>();
		this.variaveisCache = new ArrayList<Variavel>();
		this.variaveisDisco = new ArrayList<Variavel>();

		this.tCount = 0;
		this.cpCount = 0;
	}

	private void addEventoLogDisco(CheckPoint cp) {
		Evento e = new Evento(cp);
		this.eventos.add(e);
		this.getGtp().getLogDiscoHolder().addEvento(new EventoHolder(e));

	}

	private void addEventoLogMemoria(Transacao t) {
		Evento e = new Evento(t);
		this.eventos.add(e);
		this.getGtp().getLogMemoriaHolder().addEvento(new EventoHolder(e));
	}

	private void adicionarTransacao() {
		Transacao t = new Transacao(this.tCount++);
		TransacaoHolder th = new TransacaoHolder(t);
		this.getGtp().getTransacoesHolder().addTransacao(th);
		this.transacoes.add(th);
		this.addEventoLogMemoria(t);

	}

	private void adicionarCheckpoint() {
		CheckPoint cp = new CheckPoint(this.cpCount++);
		this.addEventoLogDisco(cp);
	}

	private void adicionarVariavel() {
		this.setAvw(new AdicionarVariavelWindow());
		this.getAvw().getAdicionarButton().addActionListener(this);
	}

	/**
	 * 
	 * GTP - Adicionar variavel GTP - Adicionar transaï¿½ï¿½o GTP - Estouro de
	 * Memï¿½ria GTP - CheckPoint GTP - Recuperar falha AV - OK T - Adicionar
	 * Aï¿½ï¿½o T - Iniciar Transaï¿½ï¿½o T - Abortar T - Commit T - /UPDATE/
	 * 
	 */

	@Override
	public void actionPerformed(ActionEvent e) {

//		GerenciadoTransacaoPanel
//		Adicionar Variavel
		if (e.getSource() == this.getGtp().getMenuHolder().getAddVariavelButton()) {
			this.adicionarVariavel();
		}
		
//		Adicionar transação
		if (e.getSource()==this.getGtp().getMenuHolder().getAddTransacaoButton()) {
			this.adicionarTransacao();	
		}
//		if (e.getSource()==this.getGtp().getMenuHolder().getEstourarMemoriaButton()) {
//			this.estourarMemoria();
//		}

		if (e.getSource() == this.getGtp().getMenuHolder().getCheckPointButton()) {
			this.adicionarCheckpoint();
		}
//		if (e.getSource()==this.getGtp().getMenuHolder().getRecuperarFalhaButton()) {
//			this.recuperarFalha();	
//		}

		if (this.getAvw() != null) {
			if (e.getSource() == this.getAvw().getAdicionarButton()) {
				if (!this.getAvw().getNameTextField().getText().isEmpty()
						&& !this.getAvw().getValueTextField().getText().isEmpty()) {
					String nome = this.getAvw().getNameTextField().getText();
					long valor = Long.valueOf(this.getAvw().getValueTextField().getText());

					Variavel v = new Variavel(nome, valor);
					this.variaveisDisco.add(v);
					this.getGtp().getDiscoHolder().addEvento(new EventoHolder(new Evento(v)));

					this.getAvw().setVisible(false);
					this.getAvw().dispose();
				}
			}
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof TransacaoHolderHander) {
			String tipo = (String) arg;

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

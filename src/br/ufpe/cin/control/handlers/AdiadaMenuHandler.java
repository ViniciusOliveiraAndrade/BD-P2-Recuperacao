package br.ufpe.cin.control.handlers;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Observable;

import javax.swing.JSpinner.DefaultEditor;
import javax.swing.SpinnerListModel;
import javax.swing.event.ChangeEvent;

import br.ufpe.cin.model.Acao;
import br.ufpe.cin.model.Checkpoint;
import br.ufpe.cin.model.Evento;
import br.ufpe.cin.model.StringVariables;
import br.ufpe.cin.model.Transacao;
import br.ufpe.cin.model.Variavel;
import br.ufpe.cin.view.AdicionarAcaoWindow;
import br.ufpe.cin.view.AdicionarVariavelWindow;
import br.ufpe.cin.view.EventoHolder;
import br.ufpe.cin.view.GerenciadorTransacaoPanel;
import br.ufpe.cin.view.TransacaoHolder;

public class AdiadaMenuHandler extends AbstractHandler {

	private ArrayList<TransacaoHolder> transacoes;
	
	private ArrayList<Evento> eventosLogMemoria;
	private ArrayList<Evento> eventosLogDisco;
	
	private ArrayList<Variavel> variaveisCache;
	private ArrayList<Variavel> variaveisDisco;

	private Transacao transacaoAtual;

	private int transacaoCount;
	private int checkpointCount;

//	private adicionarVarivelWindow;
//	private adicionarAcaoWindow;

	public AdiadaMenuHandler(GerenciadorTransacaoPanel gerenciadorTransacaoPanel) {
		super(gerenciadorTransacaoPanel);

		this.getGerenciadorTransacaoPanel().getMenuHolder().getAddVariavelButton().addActionListener(this);
		this.getGerenciadorTransacaoPanel().getMenuHolder().getAddTransacaoButton().addActionListener(this);
		this.getGerenciadorTransacaoPanel().getMenuHolder().getEstouroMemoriaButton().addActionListener(this);
		this.getGerenciadorTransacaoPanel().getMenuHolder().getCheckPointButton().addActionListener(this);
		this.getGerenciadorTransacaoPanel().getMenuHolder().getRecuperarButton().addActionListener(this);

		this.transacoes = new ArrayList<TransacaoHolder>();
		
		this.eventosLogMemoria = new ArrayList<Evento>();
		this.eventosLogDisco = new ArrayList<Evento>();
		
		this.variaveisCache = new ArrayList<Variavel>();
		this.variaveisDisco = new ArrayList<Variavel>();

		this.transacaoCount = 0;
		this.checkpointCount = 0;
	}

	private void addEventoLogDisco(Checkpoint checkpoint) {
		Evento evento = new Evento(checkpoint);
		this.eventosLogDisco.add(evento);
		this.getGerenciadorTransacaoPanel().getLogDiscoHolder().addEvento(new EventoHolder(evento));
	}

//	private void addEventoLogDisco(Transacao t) {
//		Evento e = new Evento(t);
//		this.eventosDisco.add(e);
//		this.getGtp().getLogDiscoHolder().addEvento(new EventoHolder(e));
//	}

	private void addEventoLogMemoria(Transacao transacao) {
		Evento evento = new Evento(transacao);

		this.eventosLogMemoria.add(evento);
		this.getGerenciadorTransacaoPanel().getLogMemoriaHolder().addEvento(new EventoHolder(evento));
	}

	private void addEventoLogMemoria(Acao acao) {
		Evento evento = new Evento(this.transacaoAtual, acao);
		this.eventosLogMemoria.add(evento);

		this.getGerenciadorTransacaoPanel().getLogMemoriaHolder().addEvento(new EventoHolder(evento));
	}

	private void adicionarTransacao() {
		Transacao transacao = new Transacao(this.transacaoCount++);
		TransacaoHolder transacaoHolder = new TransacaoHolder(transacao);
		
		@SuppressWarnings("unused")
		TransacaoHolderHandler transacaoHolderHander = new TransacaoHolderHandler(this, transacaoHolder);

		this.getGerenciadorTransacaoPanel().getTransacoesHolder().addTransacao(transacaoHolder);
		this.transacoes.add(transacaoHolder);
	}

	private void adicionarCheckpoint() {
		Checkpoint checkPoint = new Checkpoint(this.checkpointCount++);
		this.addEventoLogDisco(checkPoint);
	}

	private void criarTelaAdicionarVariavel() {
		this.setAdicionarVariavelWindow(new AdicionarVariavelWindow());
		this.getAdicionarVariavelWindow().getAdicionarButton().addActionListener(this);
	}

	private void criarTelaAdicionarAcao() {
		this.setAdicionarAcaoWindow(new AdicionarAcaoWindow());
		this.getAdicionarAcaoWindow().getAdicionarButton().addActionListener(this);
	}

	private void adicionarVariavel() {
		String nome = this.getAdicionarVariavelWindow().getNameTextField().getText();
		long valor = Long.valueOf(this.getAdicionarVariavelWindow().getValueTextField().getText());

		Variavel variavel = new Variavel(nome, valor);
		this.variaveisDisco.add(variavel);
		this.updateDisplayDisco();

		this.getAdicionarVariavelWindow().setVisible(false);
		this.getAdicionarVariavelWindow().dispose();
	}

	private void adicionarAcao() {
		Variavel variavel = this.getVariavelCache((String) this.getAdicionarAcaoWindow().getVariavelSpinner().getValue());
		if (variavel == null ) {
			variavel = this.getVariavelDisco((String) this.getAdicionarAcaoWindow().getVariavelSpinner().getValue());
		}
		variavel.locked(this.transacaoAtual.getCod());
		
		if ((String.valueOf(this.getAdicionarAcaoWindow().getTipoAcaoSpinner().getValue())).equals(StringVariables.ACAO_WRITE.getValue())) {
			
			if (!this.isVariavelCache(variavel.getNome())) {
				Acao acaoLeitura = new Acao(variavel);
				this.addEventoLogMemoria(acaoLeitura);
				Variavel variavel_locked = new Variavel(variavel.getNome(), variavel.getValor());
				variavel_locked.locked(variavel.getTransacaoCod());
				this.variaveisCache.add(variavel_locked);
			}
			
			long valor = Long.valueOf(this.getAdicionarAcaoWindow().getValorTextField().getText());
			Acao acaoEscrita = new Acao(variavel, valor );
			this.addEventoLogMemoria(acaoEscrita);
			this.getVariavelCache(variavel.getNome()).setValor(valor);
			this.updateDisplayCache();
			
		} if ((String.valueOf(this.getAdicionarAcaoWindow().getTipoAcaoSpinner().getValue())).equals(StringVariables.ACAO_READ.getValue())) {
			
			Acao acaoLeitura = new Acao(variavel );
			this.addEventoLogMemoria(acaoLeitura);
			
			if (!this.isVariavelCache(variavel.getNome())) {
				Variavel variavel_locked =new  Variavel(variavel.getNome(), variavel.getValor());
				variavel_locked.locked(variavel.getTransacaoCod());
				this.variaveisCache.add(variavel_locked );
			}
			this.updateDisplayCache();
		}
		
		this.getAdicionarAcaoWindow().setVisible(false);
		this.getAdicionarAcaoWindow().dispose();
	}

	private void estourarMemoria() {
		for (Evento e : this.eventosLogMemoria) {
			this.eventosLogDisco.add(e);
			this.getGerenciadorTransacaoPanel().getLogDiscoHolder().addEvento(new EventoHolder(e));
			this.getGerenciadorTransacaoPanel().getLogMemoriaHolder().remove(0);
			this.getGerenciadorTransacaoPanel().getLogMemoriaHolder().update();
		}
		this.eventosLogMemoria.clear();
	}

	private void updateDisplayCache(){
		this.getGerenciadorTransacaoPanel().getCacheHolder().removeAll();
		
		for (Variavel variavel : this.variaveisCache) {
			this.getGerenciadorTransacaoPanel().getCacheHolder().addEvento(new EventoHolder(new Evento(variavel)));
		}
	}
	
	private void updateDisplayDisco(){
		this.getGerenciadorTransacaoPanel().getDiscoHolder().removeAll();
		
		for (Variavel variavel : this.variaveisDisco) {
			this.getGerenciadorTransacaoPanel().getDiscoHolder().addEvento(new EventoHolder(new Evento(variavel)));
		}
	}
	
	private boolean isVariavelCache(String nome) {
		boolean emCache = false;
		for (Variavel variavel : this.variaveisCache) {
			if (variavel.getNome().equals(nome)) {
				emCache = true;
			}
		}
		
		return emCache;
	}
	
	private Variavel getVariavelCache(String nome) {
		
		for (Variavel variavel : this.variaveisCache) {
			if (variavel.getNome().equals(nome)) {
				return variavel;
			}
		}
		
		return null;
	}
	
	private Variavel getVariavelDisco(String nome) {
		
		for (Variavel variavel : this.variaveisDisco) {
			if (variavel.getNome().equals(nome)) {
				return variavel;
			}
		}
		return null;
	}
	
	@Override
	public void abortar(Transacao transacao) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void commit(Transacao transacao) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent evento) {

//		GerenciadoTransacaoPanel
//		Adicionar Variavel
		if (evento.getSource() == this.getGerenciadorTransacaoPanel().getMenuHolder().getAddVariavelButton()) {
			this.criarTelaAdicionarVariavel();
		}


//		Adicionar transação
		if (evento.getSource() == this.getGerenciadorTransacaoPanel().getMenuHolder().getAddTransacaoButton()) {
			this.adicionarTransacao();
		}

//		Estouro de memoria
		if (evento.getSource() == this.getGerenciadorTransacaoPanel().getMenuHolder().getEstouroMemoriaButton()) {
			this.estourarMemoria();
		}

//		Checkpoint
		if (evento.getSource() == this.getGerenciadorTransacaoPanel().getMenuHolder().getCheckPointButton()) {
			this.adicionarCheckpoint();
		}

//		if (e.getSource()==this.getGtp().getMenuHolder().getRecuperarFalhaButton()) {
//			this.recuperarFalha();	
//		}

//		Butao da tela adicionar Variavel 
		if (this.getAdicionarVariavelWindow() != null) {
			if (evento.getSource() == this.getAdicionarVariavelWindow().getAdicionarButton()) {
				if (!this.getAdicionarVariavelWindow().getNameTextField().getText().isEmpty()
						&& !this.getAdicionarVariavelWindow().getValueTextField().getText().isEmpty()) {
					this.adicionarVariavel();
				}
			}
		}

//		Butao da tela adicionar acao
		if (this.getAdicionarAcaoWindow() != null) {
			if (evento.getSource() == this.getAdicionarAcaoWindow().getAdicionarButton()) {
				this.adicionarAcao();
			}
		}
	}
	
	@Override
	public void update(Observable observable, Object arg) {
		if (observable instanceof TransacaoHolderHandler) {
			String tipo = (String) arg;
			Transacao transacao = ((TransacaoHolderHandler) observable).getTransacaoHolder().getT();
			this.transacaoAtual = transacao;
			
			switch (tipo) {
			case "INICIO":
				this.addEventoLogMemoria(transacao);
				break;
			case "ACAO":
				this.criarTelaAdicionarAcao();
				ArrayList<String> variaveisDisponiveis = new ArrayList<>();
				
				for (Variavel variavel : this.variaveisCache) {
					if (!variavel.isLocked()) {
						variaveisDisponiveis.add(variavel.getNome());
					}else if (variavel.isLocked() && variavel.getTransacaoCod() == this.transacaoAtual.getCod()) {
						variaveisDisponiveis.add(variavel.getNome());
					}
					
				}
				
				for (Variavel variavel : this.variaveisDisco) {
					if (!this.isVariavelCache(variavel.getNome())) {
						if (!variavel.isLocked()) {
							variaveisDisponiveis.add(variavel.getNome());
						}else if (variavel.isLocked() && variavel.getTransacaoCod() == this.transacaoAtual.getCod()) {
							variaveisDisponiveis.add(variavel.getNome());
						}
					}
				}
				
				
				if (variaveisDisponiveis.size() < 1) {
					variaveisDisponiveis.add("");
				}
				
				String[] acao = new String[2];
				acao[0] = StringVariables.ACAO_READ.getValue();
				acao[1] = StringVariables.ACAO_WRITE.getValue();

				this.getAdicionarAcaoWindow().getVariavelSpinner().setModel(new SpinnerListModel(variaveisDisponiveis));
				((DefaultEditor) this.getAdicionarAcaoWindow().getVariavelSpinner().getEditor()).getTextField().setEditable(false);

				this.getAdicionarAcaoWindow().getTipoAcaoSpinner().setModel(new SpinnerListModel(acao));
				((DefaultEditor) this.getAdicionarAcaoWindow().getTipoAcaoSpinner().getEditor()).getTextField().setEditable(false);
				this.getAdicionarAcaoWindow().getTipoAcaoSpinner().addChangeListener(this);
				this.getAdicionarAcaoWindow().visibilidade();
				
				break;
			case "ABORT":
//				System.out.println("T"+t.getCod() +" "+StringVariables.TRANSACAO_ABORT.getValue());
				break;
			case "COMMIT":
//				System.out.println("T"+t.getCod() +" "+StringVariables.TRANSACAO_COMMIT.getValue());
				break;
			default:
				break;
			}
		}
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		if (StringVariables.ACAO_WRITE.getValue().equals(String.valueOf(this.getAdicionarAcaoWindow().getTipoAcaoSpinner().getValue()))) {
			this.getAdicionarAcaoWindow().visibilidade();
		}
		if (StringVariables.ACAO_READ.getValue().equals(String.valueOf(this.getAdicionarAcaoWindow().getTipoAcaoSpinner().getValue()))) {
			this.getAdicionarAcaoWindow().visibilidade();
		}
		
	}

	

}

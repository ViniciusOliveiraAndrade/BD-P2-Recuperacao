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

public class ImediataAMenuHandler extends AbstractHandler {

	private ArrayList<TransacaoHolder> transacoes;
	private ArrayList<TransacaoHolder> transacoesAtivas;
	private ArrayList<TransacaoHolder> transacoesAbortadas;
	private ArrayList<TransacaoHolder> transacoesComitadas;
	
	private ArrayList<Evento> eventosLogMemoria;
	private ArrayList<Evento> eventosLogDisco;
	
	private ArrayList<Variavel> variaveisCache;
	private ArrayList<Variavel> variaveisDisco;

	private Transacao atual;

	private int transacaoCount;
	private int checkpointCount;

//	private adicionarVarivelWindow;
//	private adicionarAcaoWindow;

	public ImediataAMenuHandler(GerenciadorTransacaoPanel gerenciadorTransacaoPanel) {
		super(gerenciadorTransacaoPanel);

		this.getGerenciadorTransacaoPanel().getMenuHolder().getAddVariavelButton().addActionListener(this);
		this.getGerenciadorTransacaoPanel().getMenuHolder().getAddTransacaoButton().addActionListener(this);
		this.getGerenciadorTransacaoPanel().getMenuHolder().getEstouroMemoriaButton().addActionListener(this);
		this.getGerenciadorTransacaoPanel().getMenuHolder().getCheckPointButton().addActionListener(this);
		this.getGerenciadorTransacaoPanel().getMenuHolder().getRecuperarButton().addActionListener(this);

		this.transacoes = new ArrayList<TransacaoHolder>();
		this.transacoesAtivas = new ArrayList<TransacaoHolder>();
		this.transacoesAbortadas = new ArrayList<TransacaoHolder>();
		this.transacoesComitadas = new ArrayList<TransacaoHolder>();
		
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

//	private ArrayList<Transacao> pegarTransacoesDepoisCheckPoint(){
//		
//		ArrayList<Transacao> trasasoesDepoisCheckpoint = new ArrayList<>();
//		
//		Checkpoint utimoCheckpoint = this.getUtimoCheckPoint();
//		
//		if (utimoCheckpoint == null) {
//			utimoCheckpoint = new Checkpoint(this.checkpointCount++);
//		}
//		
//		for (int i = this.eventosLogDisco.size()-1; i >= 0 ; i-- ) {
//			Evento evento = this.eventosLogDisco.get(i);
//			if (evento.getTipo().equals(StringVariables.EVENTO_TRANSACAO.getValue()) || evento.getTipo().equals(StringVariables.EVENTO_ACAO.getValue())) {
////				return evento.getCheckPoint();
//			}
//		}
//		
//		
//		return null;
//	}	
	
		
	private Checkpoint getUtimoCheckPoint() {
		for (int i = this.eventosLogDisco.size()-1; i >= 0 ; i-- ) {
			Evento evento = this.eventosLogDisco.get(i);
			if (evento.getTipo().equals(StringVariables.EVENTO_CHECKPOINT)) {
				return evento.getCheckPoint();
			}
		}
		return null;
	}
	
	private void adicionarEventoLogMemoria(Transacao transacao) {
		Evento e = new Evento(transacao);

		this.eventosLogMemoria.add(e);
		this.getGerenciadorTransacaoPanel().getLogMemoriaHolder().addEvento(new EventoHolder(e));
	}
	
	private void adicionarEventoLogMemoria(Transacao transacao, boolean abort) {
		Evento e = new Evento(transacao, abort);

		this.eventosLogMemoria.add(e);
		this.getGerenciadorTransacaoPanel().getLogMemoriaHolder().addEvento(new EventoHolder(e));
	}

	private void adicionarEventoLogMemoria(Acao acao) {
		this.atual.getAcoes().add(acao);
		Evento evento = new Evento(this.atual, acao);
		
		this.eventosLogMemoria.add(evento);
		this.getGerenciadorTransacaoPanel().getLogMemoriaHolder().addEvento(new EventoHolder(evento));
	}

	private void adicionarTransacao() {
		Transacao t = new Transacao(this.transacaoCount++);
		TransacaoHolder th = new TransacaoHolder(t);
		@SuppressWarnings("unused")
		TransacaoHolderHandler thh = new TransacaoHolderHandler(this, th);
		this.getGerenciadorTransacaoPanel().getTransacoesHolder().addTransacao(th);
		this.transacoes.add(th);
	}

	private void adicionarCheckpoint() {
		Checkpoint cp = new Checkpoint(this.checkpointCount++);
		this.addEventoLogDisco(cp);
	}

	private void adicionarVariavel() {
		String nome = this.getAdicionarVariavelWindow().getNameTextField().getText();
		long valor = Long.valueOf(this.getAdicionarVariavelWindow().getValueTextField().getText());

		Variavel v = new Variavel(nome, valor);
		this.variaveisDisco.add(v);
		this.updateDisplayDisco();

		this.getAdicionarVariavelWindow().setVisible(false);
		this.getAdicionarVariavelWindow().dispose();
	}

	private void adicionarAcao() {
		Variavel variavel = this.getVariavelCache((String) this.getAdicionarAcaoWindow().getVariavelSpinner().getValue());
		if (variavel == null ) {
			variavel = this.getVariavelDisco((String) this.getAdicionarAcaoWindow().getVariavelSpinner().getValue());
		}
		variavel.locked(this.atual.getCod());
		
		if ((String.valueOf(this.getAdicionarAcaoWindow().getTipoAcaoSpinner().getValue())).equals(StringVariables.ACAO_WRITE.getValue())) {
			
			if (!this.isVariavelCache(variavel.getNome())) {
				Acao acaoLeitura = new Acao(variavel);
				this.adicionarEventoLogMemoria(acaoLeitura);
				Variavel variavel_locked = new Variavel(variavel.getNome(), variavel.getValor());
				variavel_locked.locked(variavel.getTransacaoCod());
				this.variaveisCache.add(variavel_locked);
			}
			
			long valor = Long.valueOf(this.getAdicionarAcaoWindow().getValorTextField().getText());
			Acao acaoEscrita = new Acao(variavel, valor );
//			System.out.println("Novo"+ valor + " Velho"+acaoEscrita.getValorVelho());
			this.adicionarEventoLogMemoria(acaoEscrita);
			this.getVariavelCache(variavel.getNome()).setValor(valor);
			this.updateDisplayCache();
			
		}else if ((String.valueOf(this.getAdicionarAcaoWindow().getTipoAcaoSpinner().getValue())).equals(StringVariables.ACAO_READ.getValue())) {
			
			Acao acaoLeitura = new Acao(variavel );
			this.adicionarEventoLogMemoria(acaoLeitura);
			
			if (!this.isVariavelCache(variavel.getNome())) {
				Variavel variavel_locked = new  Variavel(variavel.getNome(), variavel.getValor());
				variavel_locked.locked(variavel.getTransacaoCod());
				this.variaveisCache.add(variavel_locked );
			}
			this.updateDisplayCache();
		}
		
		
		this.getAdicionarAcaoWindow().setVisible(false);
		this.getAdicionarAcaoWindow().dispose();
	}

	private void criarTelaAdicionarVariavel() {
		this.setAdicionarVariavelWindow(new AdicionarVariavelWindow());
		this.getAdicionarVariavelWindow().getAdicionarButton().addActionListener(this);
	}

	private void criarTelaAdicionarAcao() {
		this.setAdicionarAcaoWindow(new AdicionarAcaoWindow());
		this.getAdicionarAcaoWindow().getAdicionarButton().addActionListener(this);
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
		
		for (Variavel v : this.variaveisCache) {
			this.getGerenciadorTransacaoPanel().getCacheHolder().addEvento(new EventoHolder(new Evento(v)));
		}
		
		this.getGerenciadorTransacaoPanel().getCacheHolder().update();
	}
	
	private void updateDisplayDisco(){
		this.getGerenciadorTransacaoPanel().getDiscoHolder().removeAll();
		
		for (Variavel v : this.variaveisDisco) {
			this.getGerenciadorTransacaoPanel().getDiscoHolder().addEvento(new EventoHolder(new Evento(v)));
		}
		this.getGerenciadorTransacaoPanel().getDiscoHolder().update();
	}
	
	private void updateDisplayTransacoes(){
		this.getGerenciadorTransacaoPanel().getTransacoesHolder().removeAll();
		
		for (TransacaoHolder transacaoHolder : this.transacoes) {
			this.getGerenciadorTransacaoPanel().getTransacoesHolder().add(transacaoHolder);
		}
		this.getGerenciadorTransacaoPanel().getTransacoesHolder().update();
	}
	
	private boolean isVariavelCache(String nome) {
		boolean emCache = false;
		for (Variavel v : this.variaveisCache) {
			if (v.getNome().equals(nome)) {
				emCache = true;
			}
		}
		
		return emCache;
	}
	
	private Variavel getVariavelCache(String nome) {
		
		for (Variavel v : this.variaveisCache) {
			if (v.getNome().equals(nome)) {
				return v;
			}
		}
		return null;
	}
	
	private Variavel getVariavelDisco(String nome) {
		
		for (Variavel v : this.variaveisDisco) {
			if (v.getNome().equals(nome)) {
				return v;
			}
		}
		return null;
	}
	
	private void removerVariavelCache(String nome){
		int i = -1;
		for (int j = this.variaveisCache.size()-1; j >= 0;j--) {
			if ( this.variaveisCache.get(j).getNome().equals(nome)) {
				i = j;
			}
		}
		if(i > -1) {
			this.variaveisCache.remove(i);
		}
		
		this.updateDisplayCache();
		this.updateDisplayDisco();
	}
	
	private void colocarVariavelDoCacheNoDisco(String nomeVari·vel) {
		Variavel variavel = this.getVariavelCache(nomeVari·vel);
		if(variavel != null) {
			for (Variavel variavel_aux: this.variaveisDisco) {
				if(variavel_aux.getNome().equals(variavel.getNome())) {
					variavel_aux.setValor(variavel.getValor());
					variavel_aux.unlock();
					this.removerVariavelCache(nomeVari·vel);
					
				}
			}
		}
	}
	
//	private void colocarVariavelDoCacheNoDisco(String nomeVari·vel) {
//		Variavel variavel = this.getVariavelCache(nomeVari·vel);
//		if(variavel != null) {
//			for (Variavel variavel_aux: this.variaveisDisco) {
//				if(variavel_aux.getNome().equals(variavel.getNome())) {
//					variavel_aux.setValor(variavel.getValor());
//					variavel_aux.unlock();
//					this.removerVariavelCache(nomeVari·vel);
//					
//				}
//			}
//		}
//	}
	
	@Override
	public void abortar(Transacao transacao) {
		ArrayList<Variavel> variaveis_auxiliares = new ArrayList<Variavel>();
		for (int i = transacao.getAcoes().size()-1; i >= 0; i-- ) {
			Acao acao = transacao.getAcoes().get(i);
			if (acao.getTipo().equals(StringVariables.ACAO_WRITE.getValue())) {
				this.getVariavelCache(acao.getVariavelAlvo().getNome()).setValor(acao.getValorVelho());
				variaveis_auxiliares.add(acao.getVariavelAlvo());
			}
		} 
		
		for(Variavel variavel_aux: variaveis_auxiliares) {
			this.colocarVariavelDoCacheNoDisco(variavel_aux.getNome());
		}
		
		this.updateDisplayCache();
		this.updateDisplayDisco();
		

		int j = 0;
		for(int i = this.transacoes.size()-1;i >=0; i--) {
			if(this.transacoes.get(i).getT().getCod() == transacao.getCod()) {
				this.transacoesAbortadas.add(this.transacoes.get(i));
				this.getGerenciadorTransacaoPanel().getTransacoesHolder().remove(this.transacoes.get(i));
				j = i;
			}
		}
		this.transacoes.remove(j);
		this.updateDisplayTransacoes();
		this.adicionarEventoLogMemoria(this.atual,true);
	}
	
	@Override
	public void commit(Transacao transacao) {
		ArrayList<Variavel> variaveis_auxiliares = new ArrayList<Variavel>();
		int quantidadeAcaoes = transacao.getAcoes().size();
		for (int i = 0; i < quantidadeAcaoes; i++ ) {
			Acao acao = transacao.getAcoes().get(i);
			variaveis_auxiliares.add(acao.getVariavelAlvo());
		} 
		
		for(Variavel variavel_aux: variaveis_auxiliares) {
			this.colocarVariavelDoCacheNoDisco(variavel_aux.getNome());
		}
		
		this.updateDisplayCache();
		this.updateDisplayDisco();
		

		int j = 0;
		for(int i = this.transacoes.size()-1;i >=0; i--) {
			if(this.transacoes.get(i).getT().getCod() == transacao.getCod()) {
				this.transacoesComitadas.add(this.transacoes.get(i));
				this.getGerenciadorTransacaoPanel().getTransacoesHolder().remove(this.transacoes.get(i));
				j = i;
			}
		}
		
		this.transacoes.remove(j);
		this.updateDisplayTransacoes();
		this.adicionarEventoLogMemoria(this.atual,false);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {

//		GerenciadoTransacaoPanel
//		Adicionar Variavel
		if (e.getSource() == this.getGerenciadorTransacaoPanel().getMenuHolder().getAddVariavelButton()) {
			this.criarTelaAdicionarVariavel();
		}


//		Adicionar transa√ß√£o
		if (e.getSource() == this.getGerenciadorTransacaoPanel().getMenuHolder().getAddTransacaoButton()) {
			this.adicionarTransacao();
		}

//		Estouro de memoria
		if (e.getSource() == this.getGerenciadorTransacaoPanel().getMenuHolder().getEstouroMemoriaButton()) {
			this.estourarMemoria();
		}

//		Checkpoint
		if (e.getSource() == this.getGerenciadorTransacaoPanel().getMenuHolder().getCheckPointButton()) {
			this.adicionarCheckpoint();
		}

//		if (e.getSource()==this.getGtp().getMenuHolder().getRecuperarFalhaButton()) {
//			this.recuperarFalha();	
//		}

//		Butao da tela adicionar Variavel 
		if (this.getAdicionarVariavelWindow() != null) {
			if (e.getSource() == this.getAdicionarVariavelWindow().getAdicionarButton()) {
				if (!this.getAdicionarVariavelWindow().getNameTextField().getText().isEmpty()
						&& !this.getAdicionarVariavelWindow().getValueTextField().getText().isEmpty()) {
					this.adicionarVariavel();
				}
			}
		}

//		Butao da tela adicionar acao
		if (this.getAdicionarAcaoWindow() != null) {
			if (e.getSource() == this.getAdicionarAcaoWindow().getAdicionarButton()) {
				this.adicionarAcao();
			}
		}
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof TransacaoHolderHandler) {
			String tipo = (String) arg;
			Transacao transacao = ((TransacaoHolderHandler) o).getTransacaoHolder().getT();
			this.atual = transacao;
			
			switch (tipo) {
			case "INICIO":
				
				this.adicionarEventoLogMemoria(transacao);
				break;
			case "ACAO":
				this.criarTelaAdicionarAcao();
				ArrayList<String> disponiveis = new ArrayList<>();
				
				
				for (Variavel v : this.variaveisCache) {
					if (!v.isLocked()) {
						disponiveis.add(v.getNome());
					}else if (v.isLocked() && v.getTransacaoCod() == this.atual.getCod()) {
						disponiveis.add(v.getNome());
					}
					
				}
				
				for (Variavel v : this.variaveisDisco) {
					if (!this.isVariavelCache(v.getNome())) {
						if (!v.isLocked()) {
							disponiveis.add(v.getNome());
						}else if (v.isLocked() && v.getTransacaoCod() == this.atual.getCod()) {
							disponiveis.add(v.getNome());
						}
					}
				}
				
				
				if (disponiveis.size() < 1) {
					disponiveis.add("");
				}
				
				String[] acao = new String[2];
				acao[0] = StringVariables.ACAO_READ.getValue();
				acao[1] = StringVariables.ACAO_WRITE.getValue();

				this.getAdicionarAcaoWindow().getVariavelSpinner().setModel(new SpinnerListModel(disponiveis));
				((DefaultEditor) this.getAdicionarAcaoWindow().getVariavelSpinner().getEditor()).getTextField().setEditable(false);

				this.getAdicionarAcaoWindow().getTipoAcaoSpinner().setModel(new SpinnerListModel(acao));
				((DefaultEditor) this.getAdicionarAcaoWindow().getTipoAcaoSpinner().getEditor()).getTextField().setEditable(false);
				this.getAdicionarAcaoWindow().getTipoAcaoSpinner().addChangeListener(this);
				this.getAdicionarAcaoWindow().visibilidade();
				
				break;
			case "ABORT":
				this.abortar(transacao);
//				System.out.println("T"+t.getCod() +" "+StringVariables.TRANSACAO_ABORT.getValue());
				break;
			case "COMMIT":
				this.commit(transacao);
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

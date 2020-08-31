package br.ufpe.cin.control.handlers;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Observable;

import javax.swing.JSpinner.DefaultEditor;
import javax.swing.JOptionPane;
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

	private Transacao transacaoAtual;

	private Checkpoint checkpointAtual;

	private int transacaoCount;
	private int checkpointCount;

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

	private Checkpoint getUtimoCheckPoint() {
		for (int i = this.eventosLogDisco.size() - 1; i >= 0; i--) {
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
		this.transacaoAtual.getAcoes().add(acao);
		Evento evento = new Evento(this.transacaoAtual, acao);

		this.eventosLogMemoria.add(evento);
		this.getGerenciadorTransacaoPanel().getLogMemoriaHolder().addEvento(new EventoHolder(evento));
	}
	
	private void adicionarEventoLogDisco(Acao acao) {
		this.transacaoAtual.getAcoes().add(acao);
		Evento evento = new Evento(this.transacaoAtual, acao);

		this.eventosLogDisco.add(evento);
		this.getGerenciadorTransacaoPanel().getLogDiscoHolder().addEvento(new EventoHolder(evento));
	}

	private void adicionarEventoLogDisco(Transacao transacao, boolean abort) {
		Evento e = new Evento(transacao, abort);

		this.eventosLogDisco.add(e);
		this.getGerenciadorTransacaoPanel().getLogDiscoHolder().addEvento(new EventoHolder(e));
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
		this.checkpointAtual = cp;
		this.addEventoLogDisco(cp);
		this.transacoesAbortadas.clear();
		this.transacoesComitadas.clear();
		
//		for (Variavel v: this.variaveisCache) {
//			this.colocarVariavelDoCacheNoDisco(v.getNome(), false);
//		}
//		this.updateDisplayDisco();
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
		Variavel variavel = this
				.getVariavelCache((String) this.getAdicionarAcaoWindow().getVariavelSpinner().getValue());
		if (variavel == null) {
			variavel = this.getVariavelDisco((String) this.getAdicionarAcaoWindow().getVariavelSpinner().getValue());
		}
		variavel.locked(this.transacaoAtual.getCod());

		if ((String.valueOf(this.getAdicionarAcaoWindow().getTipoAcaoSpinner().getValue()))
				.equals(StringVariables.ACAO_WRITE.getValue())) {

			if (!this.isVariavelCache(variavel.getNome())) {
				Acao acaoLeitura = new Acao(variavel);
				this.adicionarEventoLogMemoria(acaoLeitura);
				this.adicionarEventoLogDisco(acaoLeitura);
				Variavel variavel_locked = new Variavel(variavel.getNome(), variavel.getValor());
				variavel_locked.locked(variavel.getTransacaoCod());
				this.variaveisCache.add(variavel_locked);
			}

			long valor = Long.valueOf(this.getAdicionarAcaoWindow().getValorTextField().getText());
			Acao acaoEscrita = new Acao(variavel, valor);
//			System.out.println("Novo"+ valor + " Velho"+acaoEscrita.getValorVelho());
			this.adicionarEventoLogMemoria(acaoEscrita);
			this.adicionarEventoLogDisco(acaoEscrita);
//			this.adicionarEventoLogDisco(acaoEscrita);
			this.getVariavelCache(variavel.getNome()).setValor(valor);
			this.updateDisplayCache();

		} else if ((String.valueOf(this.getAdicionarAcaoWindow().getTipoAcaoSpinner().getValue()))
				.equals(StringVariables.ACAO_READ.getValue())) {

			Acao acaoLeitura = new Acao(variavel);
			this.adicionarEventoLogMemoria(acaoLeitura);
			this.adicionarEventoLogDisco(acaoLeitura);

			if (!this.isVariavelCache(variavel.getNome())) {
				Variavel variavel_locked = new Variavel(variavel.getNome(), variavel.getValor());
				variavel_locked.locked(variavel.getTransacaoCod());
				this.variaveisCache.add(variavel_locked);
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

		ArrayList<String> disponiveis = new ArrayList<>();

		for (Variavel v : this.variaveisCache) {
			if (!v.isLocked()) {
				disponiveis.add(v.getNome());
			} else if (v.isLocked() && v.getTransacaoCod() == this.transacaoAtual.getCod()) {
				disponiveis.add(v.getNome());
			}
		}

		for (Variavel v : this.variaveisDisco) {
			if (!this.isVariavelCache(v.getNome())) {
				if (!v.isLocked()) {
					disponiveis.add(v.getNome());
				} else if (v.isLocked() && v.getTransacaoCod() == this.transacaoAtual.getCod()) {
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
		((DefaultEditor) this.getAdicionarAcaoWindow().getVariavelSpinner().getEditor()).getTextField()
				.setEditable(false);

		this.getAdicionarAcaoWindow().getTipoAcaoSpinner().setModel(new SpinnerListModel(acao));
		((DefaultEditor) this.getAdicionarAcaoWindow().getTipoAcaoSpinner().getEditor()).getTextField()
				.setEditable(false);
		this.getAdicionarAcaoWindow().getTipoAcaoSpinner().addChangeListener(this);
		this.getAdicionarAcaoWindow().visibilidade();
	}

	private void estourarMemoria() {
		this.colocarVariavelDoCacheNoDisco(this.variaveisCache.get(0).getNome(),true);
//		for (Evento e : this.eventosLogMemoria) {
//			this.eventosLogDisco.add(e);
//			this.getGerenciadorTransacaoPanel().getLogDiscoHolder().addEvento(new EventoHolder(e));
//			this.getGerenciadorTransacaoPanel().getLogMemoriaHolder().remove(0);
//			this.getGerenciadorTransacaoPanel().getLogMemoriaHolder().update();
//		}
//		this.eventosLogMemoria.clear();

	}

	private void updateDisplayCache() {
		this.getGerenciadorTransacaoPanel().getCacheHolder().removeAll();

		for (Variavel v : this.variaveisCache) {
			this.getGerenciadorTransacaoPanel().getCacheHolder().addEvento(new EventoHolder(new Evento(v)));
		}

		this.getGerenciadorTransacaoPanel().getCacheHolder().update();
	}

	private void updateDisplayDisco() {
		this.getGerenciadorTransacaoPanel().getDiscoHolder().removeAll();

		for (Variavel v : this.variaveisDisco) {
			this.getGerenciadorTransacaoPanel().getDiscoHolder().addEvento(new EventoHolder(new Evento(v)));
		}
		this.getGerenciadorTransacaoPanel().getDiscoHolder().update();
	}

	private void updateDisplayLogDisco() {
		this.getGerenciadorTransacaoPanel().getLogDiscoHolder().removeAll();

		for (Evento evento : this.eventosLogDisco) {
			this.getGerenciadorTransacaoPanel().getLogDiscoHolder().addEvento(new EventoHolder(evento));
		}
		this.getGerenciadorTransacaoPanel().getLogDiscoHolder().update();
	}

	private void updateDisplayLogMemoria() {
		this.getGerenciadorTransacaoPanel().getLogMemoriaHolder().removeAll();

		for (Evento evento : this.eventosLogMemoria) {
			this.getGerenciadorTransacaoPanel().getLogMemoriaHolder().addEvento(new EventoHolder(evento));
		}
		this.getGerenciadorTransacaoPanel().getLogMemoriaHolder().update();
	}

	private void updateDisplayTransacoes() {
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

	private TransacaoHolder getTransacaoHolder(int transacaoCod) {

		for (int i = this.transacoes.size() - 1; i >= 0; i--) {
			if (this.transacoes.get(i).getTransacao().getCod() == transacaoCod) {
				return this.transacoes.get(i);

			}
		}

		for (int i = this.transacoesAtivas.size() - 1; i >= 0; i--) {
			if (this.transacoesAtivas.get(i).getTransacao().getCod() == transacaoCod) {
				return this.transacoesAtivas.get(i);

			}
		}

		for (int i = this.transacoesAbortadas.size() - 1; i >= 0; i--) {
			if (this.transacoesAbortadas.get(i).getTransacao().getCod() == transacaoCod) {
				return this.transacoesAbortadas.get(i);

			}
		}

		for (int i = this.transacoesComitadas.size() - 1; i >= 0; i--) {
			if (this.transacoesComitadas.get(i).getTransacao().getCod() == transacaoCod) {
				return this.transacoesComitadas.get(i);

			}
		}

		return null;
	}

	private void removerVariavelCache(String nome) {
		int i = -1;
		for (int j = this.variaveisCache.size() - 1; j >= 0; j--) {
			if (this.variaveisCache.get(j).getNome().equals(nome)) {
				i = j;
			}
		}
		if (i > -1) {
			this.variaveisCache.remove(i);
		}

		this.updateDisplayCache();
		this.updateDisplayDisco();
	}

	private void colocarVariavelDoCacheNoDisco(String nomeVari·vel, boolean removerCache) {
		Variavel variavel = this.getVariavelCache(nomeVari·vel);
		if (variavel != null) {
			for (int i = this.variaveisDisco.size()-1; i >=0; i --) {
				if (this.variaveisDisco.get(i).getNome().equals(variavel.getNome())) {
					this.variaveisDisco.get(i).setValor(variavel.getValor());
					this.variaveisDisco.get(i).unlock();
					if(removerCache) {
						this.removerVariavelCache(nomeVari·vel);
					}
				}
			}
		}
	}

	@Override
	public void abortar(Transacao transacao) {
		ArrayList<Variavel> variaveis_auxiliares = new ArrayList<Variavel>();
		for (int i = transacao.getAcoes().size() - 1; i >= 0; i--) {
			Acao acao = transacao.getAcoes().get(i);
			if (acao.getTipo().equals(StringVariables.ACAO_WRITE.getValue())) {
				this.getVariavelCache(acao.getVariavelAlvo().getNome()).setValor(acao.getValorVelho());
				acao.getVariavelAlvo().unlock();
				this.getVariavelCache(acao.getVariavelAlvo().getNome()).unlock();
				variaveis_auxiliares.add(acao.getVariavelAlvo());
			}
		}

		for (Variavel variavel_aux : variaveis_auxiliares) {
			variavel_aux.unlock();
			this.colocarVariavelDoCacheNoDisco(variavel_aux.getNome(), false);
		}

		this.updateDisplayCache();
		this.updateDisplayDisco();

		TransacaoHolder transacaoHolder = this.getTransacaoHolder(transacao.getCod());

		this.transacoesAbortadas.add(transacaoHolder);
		this.getGerenciadorTransacaoPanel().getTransacoesHolder().remove(transacaoHolder);

		
		this.transacoes.remove(transacaoHolder);
		this.transacoesAtivas.remove(transacaoHolder);

		
		this.adicionarEventoLogDisco(this.transacaoAtual, true);
		
//		this.eventosLogDisco.add(this.eventosLogMemoria.get(this.eventosLogMemoria.size()-1));
//		this.getGerenciadorTransacaoPanel().getLogDiscoHolder().addEvento(new EventoHolder(this.eventosLogDisco.get(this.eventosLogDisco.size()-1)));
		transacao.abortT();
		
		this.updateDisplayTransacoes();
	}

	@Override
	public void commit(Transacao transacao) {
		ArrayList<Variavel> variaveis_auxiliares = new ArrayList<Variavel>();
		int quantidadeAcaoes = transacao.getAcoes().size();
		for (int i = 0; i < quantidadeAcaoes; i++) {
			Acao acao = transacao.getAcoes().get(i);
			acao.getVariavelAlvo().unlock();
			this.getVariavelCache(acao.getVariavelAlvo().getNome()).unlock();
			variaveis_auxiliares.add(acao.getVariavelAlvo());
		}

		for (Variavel variavel_aux : variaveis_auxiliares) {
			variavel_aux.unlock();
			this.colocarVariavelDoCacheNoDisco(variavel_aux.getNome(), false);
		}

		this.updateDisplayCache();
		this.updateDisplayDisco();

		TransacaoHolder transacaoHolder = this.getTransacaoHolder(transacao.getCod());

		this.transacoesComitadas.add(transacaoHolder);
		this.getGerenciadorTransacaoPanel().getTransacoesHolder().remove(transacaoHolder);

		this.transacoes.remove(transacaoHolder);
		this.transacoesAtivas.remove(transacaoHolder);

		this.updateDisplayTransacoes();
		this.adicionarEventoLogDisco(this.transacaoAtual, false);
		
//		this.eventosLogDisco.add(this.eventosLogMemoria.get(this.eventosLogMemoria.size()-1));
//		this.getGerenciadorTransacaoPanel().getLogDiscoHolder().addEvento(new EventoHolder(this.eventosLogDisco.get(this.eventosLogDisco.size()-1)));
//		this.adicionarEventoLogDisco(this.transacaoAtual, false);
		transacao.commitT();
	}

	@Override
	public void undo() {
		ArrayList<Transacao> transacoesRefeitas = new ArrayList<Transacao>();
//		Fazer Undo nas ativaas
		ArrayList<Integer> indexs = new ArrayList<>();
		for (int i = this.transacoesAtivas.size() - 1; i >= 0; i--) {
			if(!transacoesRefeitas.contains("T"+this.transacoesAtivas.get(i).getTransacao().getCod())) {
				transacoesRefeitas.add(this.transacoesAtivas.get(i).getTransacao());
			}

			ArrayList<Variavel> variaveis_auxiliares = new ArrayList<Variavel>();
			for (int j = this.transacoesAtivas.get(i).getTransacao().getAcoes().size() - 1; j >= 0; j--) {
				Acao acao = this.transacoesAtivas.get(i).getTransacao().getAcoes().get(j);
				if (acao.getTipo().equals(StringVariables.ACAO_WRITE.getValue())) {
					this.getVariavelCache(acao.getVariavelAlvo().getNome()).setValor(acao.getValorVelho());
					variaveis_auxiliares.add(acao.getVariavelAlvo());
				}
			}

//			for (Variavel variavel_aux : variaveis_auxiliares) {
//				this.colocarVariavelDoCacheNoDisco(variavel_aux.getNome(), false);
//			}

			this.updateDisplayCache();
			this.updateDisplayDisco();

//			TransacaoHolder transacaoHolder = this.getTransacaoHolder(this.transacoesAtivas.get(i).getTransacao().getCod());

//			this.transacoesAbortadas.add(transacaoHolder);
			this.getGerenciadorTransacaoPanel().getTransacoesHolder().remove(this.transacoesAtivas.get(i));

//			this.transacoes.remove(transacaoHolder);
			indexs.add(new Integer(this.transacoesAtivas.get(i).getTransacao().getCod()));
//			this.transacoesAtivas.remove(i);

//			this.updateDisplayTransacoes();
//			this.adicionarEventoLogMemoria(this.transacaoAtual,true);
//			transacao.abortT();
		}

//		for (Integer i : indexs) {
//			this.transacoesAtivas.remove(this.getTransacaoHolder(i.intValue()));
//			this.transacoes.remove(this.getTransacaoHolder(i.intValue()));
//		}
		
		
		
		this.updateDisplayTransacoes();
		
		for (Transacao transacao: transacoesRefeitas) {
			Evento evento = new Evento(transacao, "UNDO");
			this.eventosLogDisco.add(evento);
			this.getGerenciadorTransacaoPanel().getLogDiscoHolder().addEvento(new EventoHolder(evento));
		}
	}

	@Override
	public void redo() {
		ArrayList<Transacao> transacoesRefeitas = new ArrayList<Transacao>();
//		Fazer redo nas comitadas
		ArrayList<Integer> indexs = new ArrayList<>();
		for (int i = 0; i < this.transacoesComitadas.size(); i++) {

			ArrayList<String> variaveisUsadas = new ArrayList<String>();
			ArrayList<Variavel> variaveis_auxiliares = new ArrayList<Variavel>();

			for (int j = this.transacoesComitadas.get(i).getTransacao().getAcoes().size() - 1; j >= 0; j--) {
				Acao acao = this.transacoesComitadas.get(i).getTransacao().getAcoes().get(j);
				
				if (acao.getTipo().equals(StringVariables.ACAO_WRITE.getValue())) {
					
					if (!variaveisUsadas.contains(acao.getVariavelAlvo().getNome())) {
						
						variaveisUsadas.add(acao.getVariavelAlvo().getNome());
						if(!transacoesRefeitas.contains("T"+this.transacoesComitadas.get(i).getTransacao().getCod())) {
							transacoesRefeitas.add(this.transacoesComitadas.get(i).getTransacao());
						}
						
						if (this.isVariavelCache(acao.getVariavelAlvo().getNome())) {
							this.getVariavelCache(acao.getVariavelAlvo().getNome()).setValor(acao.getValorNovo());
							variaveis_auxiliares.add(acao.getVariavelAlvo());
							
						} else {
							this.variaveisCache.add(this.getVariavelDisco(acao.getVariavelAlvo().getNome()));
							this.getVariavelCache(acao.getVariavelAlvo().getNome()).setValor(acao.getValorNovo());
							variaveis_auxiliares.add(acao.getVariavelAlvo());
						}
					}
				}
			}

			for (Variavel variavel_aux : variaveis_auxiliares) {
				this.colocarVariavelDoCacheNoDisco(variavel_aux.getNome(), false);
			}

			this.updateDisplayCache();
			this.updateDisplayDisco();

			this.getGerenciadorTransacaoPanel().getTransacoesHolder().remove(this.transacoesComitadas.get(i));

			indexs.add(new Integer(this.transacoesComitadas.get(i).getTransacao().getCod()));
		}

		for (Integer i : indexs) {
			this.transacoesComitadas.remove(this.getTransacaoHolder(i.intValue()));
		}
		
		this.updateDisplayTransacoes();
		
		for (Transacao transacao: transacoesRefeitas) {
			Evento evento = new Evento(transacao, "REDO");
			this.eventosLogDisco.add(evento);
			this.getGerenciadorTransacaoPanel().getLogDiscoHolder().addEvento(new EventoHolder(evento));
		}
		this.updateDisplayLogDisco();
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

//		Recuperar
		if (e.getSource() == this.getGerenciadorTransacaoPanel().getMenuHolder().getRecuperarButton()) {
			this.undo();
			this.redo();
		}

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
			Transacao transacao = ((TransacaoHolderHandler) o).getTransacaoHolder().getTransacao();
			this.transacaoAtual = transacao;

			switch (tipo) {
			case "INICIO":
				transacao.iniciarT();
				this.adicionarEventoLogMemoria(transacao);
				this.transacoesAtivas.add(this.getTransacaoHolder(transacao.getCod()));
				break;
			case "ACAO":
				this.criarTelaAdicionarAcao();
				break;
			case "ABORT":
				this.abortar(transacao);
				break;
			case "COMMIT":
				this.commit(transacao);
				break;
			default:
				break;
			}

		}

//		System.out.println("Transcoes: "+this.transacoes.size());
//		System.out.println("Transcoes Ativas: "+this.transacoesAtivas.size());
//		System.out.println("Transcoes Abortadas: "+this.transacoesAbortadas.size());
//		System.out.println("Transcoes Commitadas: "+this.transacoesComitadas.size());
//		System.out.println("================================================");

//		System.out.println("======================");
//		System.out.println("Abort");
//		for (TransacaoHolder transacaoHolder: this.transacoesAbortadas) {
//			System.out.println("T"+transacaoHolder.getTransacao().getCod()+" Tempo Abort: "+transacaoHolder.getTransacao().getTempoAbort());
//		}
//		System.out.println("======================");
//		System.out.println("Commit");
//		for (TransacaoHolder transacaoHolder: this.transacoesComitadas) {
//			System.out.println("T"+transacaoHolder.getTransacao().getCod()+" Tempo Commit: "+transacaoHolder.getTransacao().getTempoCommit());
//		}
//		

	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		if (StringVariables.ACAO_WRITE.getValue()
				.equals(String.valueOf(this.getAdicionarAcaoWindow().getTipoAcaoSpinner().getValue()))) {
			this.getAdicionarAcaoWindow().visibilidade();
		}
		if (StringVariables.ACAO_READ.getValue()
				.equals(String.valueOf(this.getAdicionarAcaoWindow().getTipoAcaoSpinner().getValue()))) {
			this.getAdicionarAcaoWindow().visibilidade();
		}

	}

}

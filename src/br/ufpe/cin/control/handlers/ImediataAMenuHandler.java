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

	private ArrayList<Transacao> pegarTransacoesDepoisCheckPoint(){
		
		ArrayList<Transacao> trasasoesDepoisCheckpoint = new ArrayList<>();
		
		Checkpoint utimoCheckpoint = this.getUtimoCheckPoint();
		
		if (utimoCheckpoint == null) {
			utimoCheckpoint = new Checkpoint(this.checkpointCount++);
		}
		
		for (int i = this.eventosLogDisco.size()-1; i >= 0 ; i-- ) {
			Evento evento = this.eventosLogDisco.get(i);
			if (evento.getTipo().equals(StringVariables.EVENTO_TRANSACAO.getValue()) || evento.getTipo().equals(StringVariables.EVENTO_ACAO.getValue())) {
//				return evento.getCheckPoint();
			}
		}
		
		
		
		return null;
	}
	
	
	private Checkpoint getUtimoCheckPoint() {
		for (int i = this.eventosLogDisco.size()-1; i >= 0 ; i-- ) {
			Evento evento = this.eventosLogDisco.get(i);
			if (evento.getTipo().equals(StringVariables.EVENTO_CHECKPOINT)) {
				return evento.getCheckPoint();
			}
		}
		return null;
	}
	
	private void addEventoLogMemoria(Transacao t) {
		Evento e = new Evento(t);

		this.eventosLogMemoria.add(e);
		this.getGerenciadorTransacaoPanel().getLogMemoriaHolder().addEvento(new EventoHolder(e));
	}

	private void addEventoLogMemoria(Acao a) {
		Evento e = new Evento(this.atual, a);
		this.eventosLogMemoria.add(e);

		this.getGerenciadorTransacaoPanel().getLogMemoriaHolder().addEvento(new EventoHolder(e));
	}

	private void adicionarTransacao() {
		Transacao t = new Transacao(this.transacaoCount++);
		TransacaoHolder th = new TransacaoHolder(t);
		@SuppressWarnings("unused")
		TransacaoHolderHander thh = new TransacaoHolderHander(this, th);

		this.getGerenciadorTransacaoPanel().getTransacoesHolder().addTransacao(th);
		this.transacoes.add(th);
	}

	private void adicionarCheckpoint() {
		Checkpoint cp = new Checkpoint(this.checkpointCount++);
		this.addEventoLogDisco(cp);
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
		
		for (Variavel v : this.variaveisCache) {
			this.getGerenciadorTransacaoPanel().getCacheHolder().addEvento(new EventoHolder(new Evento(v)));
		}
	}
	
	private void updateDisplayDisco(){
		this.getGerenciadorTransacaoPanel().getDiscoHolder().removeAll();
		
		for (Variavel v : this.variaveisDisco) {
			this.getGerenciadorTransacaoPanel().getDiscoHolder().addEvento(new EventoHolder(new Evento(v)));
		}
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

	@Override
	public void actionPerformed(ActionEvent e) {

//		GerenciadoTransacaoPanel
//		Adicionar Variavel
		if (e.getSource() == this.getGerenciadorTransacaoPanel().getMenuHolder().getAddVariavelButton()) {
			this.criarTelaAdicionarVariavel();
		}


//		Adicionar transação
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
		if (o instanceof TransacaoHolderHander) {
			String tipo = (String) arg;
			Transacao t = ((TransacaoHolderHander) o).getTransacaoHolder().getT();
			this.atual = t;
			
			switch (tipo) {
			case "INICIO":
				this.addEventoLogMemoria(t);
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

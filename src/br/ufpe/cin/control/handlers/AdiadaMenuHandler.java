package br.ufpe.cin.control.handlers;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Observable;

import javax.swing.JSpinner.DefaultEditor;
import javax.swing.SpinnerListModel;
import javax.swing.event.ChangeEvent;

import br.ufpe.cin.model.Acao;
import br.ufpe.cin.model.CheckPoint;
import br.ufpe.cin.model.Evento;
import br.ufpe.cin.model.StringVariables;
import br.ufpe.cin.model.Transacao;
import br.ufpe.cin.model.Variavel;
import br.ufpe.cin.view.AdicionarAcaoWindow;
import br.ufpe.cin.view.AdicionarVariavelWindow;
import br.ufpe.cin.view.EventoHolder;
import br.ufpe.cin.view.GerenciadoTransacaoPanel;
import br.ufpe.cin.view.TransacaoHolder;

public class AdiadaMenuHandler extends AbstractHandler {

	private ArrayList<TransacaoHolder> transacoes;
	private ArrayList<Evento> eventosMemoria;
	private ArrayList<Evento> eventosDisco;
	private ArrayList<Variavel> variaveisCache;
	private ArrayList<Variavel> variaveisDisco;

	private Transacao atual;

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
		
		this.eventosMemoria = new ArrayList<Evento>();
		this.eventosDisco = new ArrayList<Evento>();
		
		this.variaveisCache = new ArrayList<Variavel>();
		this.variaveisDisco = new ArrayList<Variavel>();

		this.tCount = 0;
		this.cpCount = 0;
	}

	private void addEventoLogDisco(CheckPoint cp) {
		Evento e = new Evento(cp);
		this.eventosDisco.add(e);
		this.getGtp().getLogDiscoHolder().addEvento(new EventoHolder(e));
	}

	private void addEventoLogDisco(Transacao t) {
		Evento e = new Evento(t);
		this.eventosDisco.add(e);
		this.getGtp().getLogDiscoHolder().addEvento(new EventoHolder(e));
	}

	private void addEventoLogMemoria(Transacao t) {
		Evento e = new Evento(t);
		this.eventosMemoria.add(e);
		this.getGtp().getLogMemoriaHolder().addEvento(new EventoHolder(e));
	}

	private void addEventoLogMemoria(Acao a) {
		Evento e = new Evento(this.atual, a);
		this.eventosMemoria.add(e);
		this.getGtp().getLogMemoriaHolder().addEvento(new EventoHolder(e));
	}

	private void adicionarTransacao() {
		Transacao t = new Transacao(this.tCount++);
		TransacaoHolder th = new TransacaoHolder(t);
		TransacaoHolderHander thh = new TransacaoHolderHander(this, th);
		this.getGtp().getTransacoesHolder().addTransacao(th);
		this.transacoes.add(th);
	}

	private void adicionarCheckpoint() {
		CheckPoint cp = new CheckPoint(this.cpCount++);
		this.addEventoLogDisco(cp);
	}

	private void criarTelaAdicionarVariavel() {
		this.setAvw(new AdicionarVariavelWindow());
		this.getAvw().getAdicionarButton().addActionListener(this);
	}

	private void criarTelaAdicionarAcao() {
		this.setAaw(new AdicionarAcaoWindow());
		this.getAaw().getAdicionarButton().addActionListener(this);
	}

	private void adicionarVariavel() {
		String nome = this.getAvw().getNameTextField().getText();
		long valor = Long.valueOf(this.getAvw().getValueTextField().getText());

		Variavel v = new Variavel(nome, valor);
		this.variaveisDisco.add(v);
		this.updateCacheDisco();

		this.getAvw().setVisible(false);
		this.getAvw().dispose();
	}

	private void adicionarAcao() {
		Variavel v = this.getVariavel((String) this.getAaw().getVariavelSpinner().getValue());
		v.setLocked(true);
		
		if ((String.valueOf(this.getAaw().getTipoAcaoSpinner().getValue())).equals(StringVariables.ACAO_READ.getValue())) {
			Acao a = new Acao(v);
			this.addEventoLogMemoria(a);
		}else {
			Acao a = new Acao(v, Long.valueOf(this.getAaw().getValorTextField().getText()));
			this.addEventoLogMemoria(a);
		}
		
		this.getAaw().setVisible(false);
		this.getAaw().dispose();
	}

	private void estourarMemoria() {
		for (Evento e : this.eventosMemoria) {
			this.eventosDisco.add(e);
			this.getGtp().getLogDiscoHolder().addEvento(new EventoHolder(e));
			this.getGtp().getLogMemoriaHolder().remove(0);
			this.getGtp().getLogMemoriaHolder().update();
		}
		this.eventosMemoria.clear();

	}

	private void updateCacheDisco() {
		this.getGtp().getCacheHolder().removeAll();
		this.getGtp().getDiscoHolder().removeAll();
		
		for (Variavel v : this.variaveisCache) {
			this.getGtp().getCacheHolder().addEvento(new EventoHolder(new Evento(v)));
		}
		for (Variavel v : this.variaveisDisco) {
			this.getGtp().getDiscoHolder().addEvento(new EventoHolder(new Evento(v)));
		}
	}
	
	private Variavel getVariavel(String nome) {
		for (Variavel v : this.variaveisCache) {
			if (v.getNome().equals(nome)) {
				return v;
			}
		}
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
		if (e.getSource() == this.getGtp().getMenuHolder().getAddVariavelButton()) {
			this.criarTelaAdicionarVariavel();
		}

//		Adicionar transação
		if (e.getSource() == this.getGtp().getMenuHolder().getAddTransacaoButton()) {
			this.adicionarTransacao();
		}

//		Estouro de memoria
		if (e.getSource() == this.getGtp().getMenuHolder().getEstouroMemoriaButton()) {
			this.estourarMemoria();
		}

//		Checkpoint
		if (e.getSource() == this.getGtp().getMenuHolder().getCheckPointButton()) {
			this.adicionarCheckpoint();
		}

//		if (e.getSource()==this.getGtp().getMenuHolder().getRecuperarFalhaButton()) {
//			this.recuperarFalha();	
//		}

//		Butao da tela adicionar Variavel 
		if (this.getAvw() != null) {
			if (e.getSource() == this.getAvw().getAdicionarButton()) {
				if (!this.getAvw().getNameTextField().getText().isEmpty()
						&& !this.getAvw().getValueTextField().getText().isEmpty()) {
					this.adicionarVariavel();
				}
			}
		}

//		Butao da tela adicionar acao
		if (this.getAaw() != null) {
			if (e.getSource() == this.getAaw().getAdicionarButton()) {
				this.adicionarAcao();
			}
		}
	}
	
	

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof TransacaoHolderHander) {
			String tipo = (String) arg;
			Transacao t = ((TransacaoHolderHander) o).getTransaçãoHolder().getT();
			this.atual = t;
			switch (tipo) {
			case "INICIO":
				this.addEventoLogMemoria(t);

//				System.out.println("T"+t.getCod() +" "+StringVariables.TRANSACAO_INICIO.getValue());
				break;
			case "ACAO":
				this.criarTelaAdicionarAcao();
				ArrayList<String> disponiveis = new ArrayList<>();
				if (this.variaveisCache.size() < 1 && this.variaveisDisco.size() < 1) {
					disponiveis.add("");
				}
				
				for (Variavel v : this.variaveisCache) {
					if (!v.isLocked()) {
						if(!disponiveis.contains(v.getNome())) {
						disponiveis.add(v.getNome());}
					}
				}
				
				for (Variavel v : this.variaveisDisco) {
					if (!v.isLocked()) {
						if(!disponiveis.contains(v.getNome())) {
						disponiveis.add(v.getNome());}
					}
				}
				
				
				
				String[] acao = new String[2];
				acao[0] = StringVariables.ACAO_READ.getValue();
				acao[1] = StringVariables.ACAO_WRITE.getValue();

				this.getAaw().getVariavelSpinner().setModel(new SpinnerListModel(disponiveis));
				((DefaultEditor) this.getAaw().getVariavelSpinner().getEditor()).getTextField().setEditable(false);

				this.getAaw().getTipoAcaoSpinner().setModel(new SpinnerListModel(acao));
				((DefaultEditor) this.getAaw().getTipoAcaoSpinner().getEditor()).getTextField().setEditable(false);
				this.getAaw().getTipoAcaoSpinner().addChangeListener(this);
				this.getAaw().visibilidade();
				
				
//				System.out.println("T"+t.getCod() +" "+StringVariables.TRANSACAO_ACAO.getValue());
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
		if (StringVariables.ACAO_WRITE.getValue().equals(String.valueOf(this.getAaw().getTipoAcaoSpinner().getValue()))) {
			this.getAaw().visibilidade();
		}
		if (StringVariables.ACAO_READ.getValue().equals(String.valueOf(this.getAaw().getTipoAcaoSpinner().getValue()))) {
			this.getAaw().visibilidade();
		}
		
	}

}

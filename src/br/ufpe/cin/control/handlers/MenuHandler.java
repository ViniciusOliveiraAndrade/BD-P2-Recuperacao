package br.ufpe.cin.control.handlers;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import br.ufpe.cin.model.Transacao;
import br.ufpe.cin.view.GerenciadoTransacaoPanel;
import br.ufpe.cin.view.TransacaoHolder;

public class MenuHandler extends AbstractHandler {
	
	private ArrayList<TransacaoHolder> transacoes;
	private int tCount;
	
	public MenuHandler(GerenciadoTransacaoPanel gtp) {
		super(gtp);
		this.getGtp().getMenuHolder().getAddButton().addActionListener(this);
		
		this.transacoes = new ArrayList<TransacaoHolder>();
		
		this.tCount = 0;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==this.getGtp().getMenuHolder().getAddButton()) {
			TransacaoHolder th = new TransacaoHolder(new Transacao(this.tCount ++));
			this.getGtp().getCacheHolder().addTransacao(th);
			this.transacoes.add(th);
		}
		
	}

}

package br.ufpe.cin.view;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import br.ufpe.cin.model.Evento;

public class EventoHolder extends JLabel{
	
	private static final long serialVersionUID = 1L;

	public EventoHolder(Evento e) {
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		switch (e.getTipo()) {
		case "transacao":
			this.setText("T"+e.getTransacao().getCod() + " iniciou");
			break;
		case "acao":
			this.setText("T"+e.getTransacao().getCod() + " Falta pegar ação");
			break;
		case "checkpoint":
			this.setText("CheckPoint "+e.getCheckPoint().getCod()+" criado");
			break;
			
		default:
			break;
		}
	}

}

package br.ufpe.cin.view;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import br.ufpe.cin.model.Evento;
import br.ufpe.cin.model.StringVariables;

public class EventoHolder extends JLabel{
	
	private static final long serialVersionUID = 1L;

	public EventoHolder(Evento e) {
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));	
		this.setBackground(Color.WHITE);
		this.setOpaque(true);
		switch (e.getTipo()) {
		case "TRANSACAO":
			this.setText("[ START_TRANSACTION, T"+e.getTransacao().getCod()+" ]");
			break;
		case "ACAO":
			if (e.getAcao().getTipo() == StringVariables.ACAO_WRITE.getValue()) {
				this.setText("[ WRITE_ITEM, T"+e.getTransacao().getCod() + ", "+e.getAcao().getVariavelAlvo().getNome() + ", " +e.getAcao().getValorVelho() + ", " +e.getAcao().getValorNovo()+" ]");
			}else if (e.getAcao().getTipo() == StringVariables.ACAO_READ.getValue()) {
				this.setText("[ READ_ITEM, T"+e.getTransacao().getCod() + ", "+e.getAcao().getVariavelAlvo().getNome()+" ]");
			}
			break;
		case "CHECKPOINT":
			this.setText("[ CHECKPOINT ]");
			break;
		case "VARIAVEL":
			this.setText(e.getVariavel().getNome()+ " = " + e.getVariavel().getValor());
			break;
			
		default:
			break;
		}
		
	}

}

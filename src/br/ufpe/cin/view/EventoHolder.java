package br.ufpe.cin.view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import br.ufpe.cin.model.Evento;
import br.ufpe.cin.model.StringVariables;

public class EventoHolder extends JLabel{
	
	private static final long serialVersionUID = 1L;

	public EventoHolder(Evento evento) {
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));	
		this.setBackground(Color.WHITE);
		this.setOpaque(true);
		switch (evento.getTipo()) {
		case "TRANSACAO":
			this.setText(" [ START_TRANSACTION, T"+evento.getTransacao().getCod()+" ]");
			break;
		case "ACAO":
			if (evento.getAcao().getTipo() == StringVariables.ACAO_WRITE.getValue()) {
				this.setText(" [ WRITE_ITEM, T"+evento.getTransacao().getCod() + ", "+evento.getAcao().getVariavelAlvo().getNome() + ", " +evento.getAcao().getValorVelho() + ", " +evento.getAcao().getValorNovo()+" ]");
			}else if (evento.getAcao().getTipo() == StringVariables.ACAO_READ.getValue()) {
				this.setText(" [ READ_ITEM, T"+evento.getTransacao().getCod() + ", "+evento.getAcao().getVariavelAlvo().getNome()+" ]");
			}
			break;
		case "CHECKPOINT":
			this.setText(" [ CHECKPOINT ]");
			break;
		case "VARIAVEL":
			this.setText(" "+evento.getVariavel().getNome()+ " = " + evento.getVariavel().getValor());
			break;
		case "ABORT":
			this.setText(" [ ABORT_TRANSACTION, T"+evento.getTransacao().getCod()+" ]");
			break;
		case "COMMIT":
			this.setText(" [ COMMIT_TRANSACTION, T"+evento.getTransacao().getCod()+" ]");
			break;
		case "UNDO_REDO":
			if(evento.getUNDO_REDO() == "UNDO") {
				this.setText(" [ UNDO, T"+evento.getTransacao().getCod()+" ]");
			}else {
				this.setText(" [ REDO, T"+evento.getTransacao().getCod()+" ]");
			}
			break;
			
		default:
			break;
		}
		this.setFont(new Font(this.getFont().getFamily(), 0, 20));
	}

}

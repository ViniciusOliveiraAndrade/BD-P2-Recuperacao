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
		switch (e.getTipo()) {
		case "TRANSACAO":
			this.setText("T"+e.getTransacao().getCod() + " iniciou");
			break;
		case "ACAO":
			if (e.getAcao().getTipo() == StringVariables.ACAO_WRITE.getValue()) {
				this.setText("T"+e.getTransacao().getCod() + " "+e.getAcao().getTipo() + " " +e.getAcao().getVariavelAlvo().getNome() + " = " +e.getAcao().getValorNovo());
			}else if (e.getAcao().getTipo() == StringVariables.ACAO_READ.getValue()) {
				this.setText("T"+e.getTransacao().getCod() + " "+e.getAcao().getTipo() + " " +e.getAcao().getVariavelAlvo().getNome());
				
			}
			break;
		case "CHECKPOINT":
			this.setText("CheckPoint "+e.getCheckPoint().getCod()+" criado");
			break;
		case "VARIAVEL":
			this.setText(e.getVariavel().getNome()+ " = " + e.getVariavel().getValor());
			break;
			
		default:
			break;
		}
	}

}

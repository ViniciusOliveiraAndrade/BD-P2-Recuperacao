package br.ufpe.cin.view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;

public class AdicionarAcaoWindow extends JFrame {
	private JTextField valorTextField;
	private JButton adicionarButton = new JButton("Adicionar");
	private JSpinner tipoAcaoSpinner = new JSpinner();
	private JSpinner variavelSpinner = new JSpinner();
	
	public AdicionarAcaoWindow() {
		setSize(334, 235);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JLabel lblAdicionarAo = new JLabel("Adicionar Ação");
		lblAdicionarAo.setBounds(119, 11, 71, 14);
		getContentPane().add(lblAdicionarAo);
		
		JLabel lblTipo = new JLabel("Tipo");
		lblTipo.setBounds(10, 50, 46, 14);
		getContentPane().add(lblTipo);
		
		JLabel lblVarivel = new JLabel("Variável");
		lblVarivel.setBounds(119, 50, 46, 14);
		getContentPane().add(lblVarivel);
		
		JLabel lblValor = new JLabel("Valor");
		lblValor.setBounds(224, 50, 46, 14);
		getContentPane().add(lblValor);
		
		adicionarButton.setBounds(117, 162, 89, 23);
		getContentPane().add(adicionarButton);
		
		tipoAcaoSpinner.setBounds(10, 64, 59, 20);
		getContentPane().add(tipoAcaoSpinner);
		
		valorTextField = new JTextField();
		valorTextField.setBounds(224, 64, 86, 20);
		getContentPane().add(valorTextField);
		valorTextField.setColumns(10);
		
		variavelSpinner.setBounds(117, 64, 59, 20);
		getContentPane().add(variavelSpinner);
		
		setVisible(true);	
	}

	public JTextField getValorTextField() {
		return valorTextField;
	}

	public JButton getAdicionarButton() {
		return adicionarButton;
	}

	public JSpinner getTipoAcaoSpinner() {
		return tipoAcaoSpinner;
	}

	public JSpinner getVariavelSpinner() {
		return variavelSpinner;
	}
	
}

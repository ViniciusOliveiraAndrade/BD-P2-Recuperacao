package br.ufpe.cin.view;

import javax.swing.JFrame;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class AdicionarVariavelWindow extends JFrame {
	private JTextField nameTextField;
	private JTextField valueTextField;
	private JButton adicionarButton = new JButton("Adicionar");
	
	public AdicionarVariavelWindow() {
		setSize(216, 192);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		
		adicionarButton.setBounds(98, 119, 89, 23);
		getContentPane().add(adicionarButton);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setBounds(10, 39, 46, 14);
		getContentPane().add(lblNome);
		
		JLabel lblValor = new JLabel("Valor");
		lblValor.setBounds(10, 74, 46, 14);
		getContentPane().add(lblValor);
		
		nameTextField = new JTextField();
		nameTextField.setBounds(49, 36, 135, 20);
		getContentPane().add(nameTextField);
		nameTextField.setColumns(10);
		
		valueTextField = new JTextField();
		valueTextField.setBounds(49, 71, 135, 20);
		getContentPane().add(valueTextField);
		valueTextField.setColumns(10);
		valueTextField.addKeyListener(new KeyAdapter() {
		    public void keyTyped(KeyEvent e) {
		        char c = e.getKeyChar();
		        if (!((c >= '0') && (c <= '9') ||
		           (c == KeyEvent.VK_BACK_SPACE) ||
		           (c == KeyEvent.VK_DELETE))) {
		          getToolkit().beep();
		          e.consume();
		        }
		      }
		    });
		
		JLabel lblAdicionarVarivel = new JLabel("Adicionar Vari\u00E1vel");
		lblAdicionarVarivel.setBounds(49, 11, 112, 14);
		getContentPane().add(lblAdicionarVarivel);
		
		setVisible(true);
	}

	public JTextField getNameTextField() {
		return nameTextField;
	}

	public JTextField getValueTextField() {
		return valueTextField;
	}

	public JButton getAdicionarButton() {
		return adicionarButton;
	}
	
}

package ecaAgentes;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class FrameCenario extends JFrame implements ActionListener {

	private static final long serialVersionUID = -5873687310182629380L;

	//atributos
	private JPanel pnPrincipal = new JPanel();
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem menuAgente;
	private JMenuItem menuInvasor;
	//Agentes
	private ArrayList<Agente> agentes;
	//construtor
	public FrameCenario(){
		this.agentes = new ArrayList<Agente>();
		//configuracao do painel e frame
		this.pnPrincipal.setPreferredSize(new Dimension(700, 300));
		this.pnPrincipal.setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setContentPane(this.pnPrincipal);
		//menus
		menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		menu = new JMenu("Mundo");
		menu.setMnemonic(KeyEvent.VK_S);
		menuBar.add(menu);
		menuAgente = new JMenuItem("Add. Agentes do tipo X");
		menuAgente.setMnemonic(KeyEvent.VK_U);
		menu.add(menuAgente);
		menuAgente.addActionListener(this);
		menu.addSeparator();
		menuInvasor = new JMenuItem("Add. Agentes do tipo Y");
		menu.add(menuInvasor);
		menuInvasor.addActionListener(this);		
	}
	public JPanel getPnPrincipal() {
		return pnPrincipal;
	}
	//eventos
	public void actionPerformed(ActionEvent evento) {
		if (evento.getSource().equals(menuAgente)) {
			for (int cont = 1; cont <= 10; cont++) {
				Agente a = new Agente(this, this.agentes.size() + 1);
				this.agentes.add(a);
			}
			for (Agente a: this.agentes) {
				a.execute();
			}
			this.menuAgente.setEnabled(false);
		}
		if (evento.getSource().equals(menuInvasor)) {

		}


	}

	public ArrayList<Agente> getAgentes() {
		return agentes;
	}
}

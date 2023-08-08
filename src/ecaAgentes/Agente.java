package ecaAgentes;

import java.awt.Color;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JButton;
import javax.swing.SwingWorker;

public class Agente extends SwingWorker<Object, Object> {

	enum Direcao {
		NORTE,
		SUL,
		LESTE,
		OESTE
	}
	//id do agente 
	private int id;
	//posição horizontal em pixels
	private int posAtualH;
	//posição vertical em pixels
	private int posAtualV;
	//posição horizontal objetivo em pixels
	private int objetivoH;
	//posição vertical objetivo em pixels
	private int objetivoV;
	//posição horizontal limite da tela em pixels
	private int limiteH;
	//posição vertical limite da tela em pixels
	private int limiteV;
	//componente botão que representa o corpo
	private JButton avatar;
	//Tela
	private FrameCenario cenario;
	//Variável para parar/executar a execucação da lógica
	private boolean executar;
	//Objeto para gerar números randomicos
	private Random rnd;
	//variável que guarda a direção atual
	private Direcao direcao;
	//variável que guarda espessura da borda em pixels
	private int bordaCenario;
	//variável que guarda tamanho em pixels do corpo
	private int tamanhoAvatar;
	//variável que guarda deslocamento em pixels do corpo
	private int velocidadeAvatar;

	//Construtor do Agente - Inicializa atributos/ variáveis
	public Agente(FrameCenario f, int id) {
		this.velocidadeAvatar = 1;
		this.direcao = Agente.Direcao.LESTE;
		this.id = id;
		this.cenario = f;
		this.bordaCenario = 60;
		this.tamanhoAvatar = 50;
		this.rnd = new Random();
		this.executar = true;	
		this.limiteH = this.cenario.getContentPane().getWidth() - bordaCenario;
		this.limiteV = this.cenario.getContentPane().getHeight() - bordaCenario;
		this.posicaoInicial();
		this.novoObjetivo();
		this.avatar = new JButton(""+id);
		this.avatar.setBounds(posAtualH,posAtualV,tamanhoAvatar,tamanhoAvatar);
		this.avatar.setBackground(new Color(250,250,250));
		cenario.getPnPrincipal().add(avatar);	
	}

	//Método/ função que roda em Loop infinito
	@Override
	public String doInBackground() {
		while (this.executar) {
			try {
				for (Agente at: this.cenario.getAgentes()) {
					//ignorar ele mesmo na lista
					if (at != this) {
						//evitar colisao
						/*
						if (this.avatar.getBounds().intersects(at.getAvatar().getBounds())) {
							this.novoObjetivoPorRange();
						}
						*/
						while (this.avatar.getBounds().intersects(at.getAvatar().getBounds())) {
							this.velocidadeAvatar = 3;
							this.movimentarAleatoriamente();
							//delay
							synchronized (this) {
								this.wait(100);
							}
						}
						this.velocidadeAvatar--;
					} 
				}	
				this.movimentarParaObjetivo();
				//delay
				synchronized (this) {
					this.wait(100);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return "";
	}

	
	//método / função para movimento aleatório na tela
	public void movimentarAleatoriamente(){
		//movimenta sem sair do frame
		if ((this.posAtualH >= (this.limiteH - (this.bordaCenario * 2))) || (this.posAtualH <= (this.bordaCenario * 2))) {
			if (this.direcao == Agente.Direcao.LESTE) {
				this.direcao = Agente.Direcao.OESTE;
			} else {
				this.direcao = Agente.Direcao.LESTE;
			}
		}
		if ((this.posAtualV <= (this.bordaCenario * 2)) || (this.posAtualV >= (this.limiteV - (this.bordaCenario * 2)))) {
			if (this.direcao == Agente.Direcao.NORTE) {
				this.direcao = Agente.Direcao.SUL;
			} else {
				this.direcao = Agente.Direcao.NORTE;
			}
		}
		switch(this.direcao) {
		case LESTE: 
			this.posAtualH = this.posAtualH + this.velocidadeAvatar; 
			break;
		case SUL: 
			this.posAtualV = this.posAtualV + this.velocidadeAvatar; 
			break;
		case OESTE: 
			this.posAtualH = this.posAtualH - this.velocidadeAvatar; 
			break;
		case NORTE: 
			this.posAtualV = this.posAtualV - this.velocidadeAvatar; 
			break;
		}
		this.atualizarCenario();

	}

	//movimenta agente para objetivo em pixels
	public void movimentarParaObjetivo(){
		//movimenta sem sair do frame
		if (this.posAtualH == this.objetivoH) {
			this.novoObjetivo(); 
		}
		if (this.posAtualV == this.objetivoV) {
			this.novoObjetivo();  
		}
		if (this.posAtualH < this.objetivoH) {
			this.posAtualH++; 
			this.direcao = Agente.Direcao.LESTE;

		}
		if (this.posAtualH > this.objetivoH) {
			this.posAtualH--;
			this.direcao = Agente.Direcao.OESTE;

		}
		if (this.posAtualV < this.objetivoV) {
			this.posAtualV++;
			this.direcao = Agente.Direcao.NORTE;

		}
		if (this.posAtualV > this.objetivoV) {
			this.posAtualV--;
			this.direcao = Agente.Direcao.SUL;

		}
		this.atualizarCenario();
	}

	
	
	//defie novo objetivo em pixel com valores aleatórios
	public void novoObjetivo() {
		this.objetivoH = rnd.nextInt(limiteH - (this.bordaCenario * 2));
		this.objetivoV = rnd.nextInt(limiteV - (this.bordaCenario * 2));
	}
	
	//defie posição inicia na tela em pixel com valores aleatórios	
	public void posicaoInicial() {
		int rangeH = limiteH / 10;
		int rangeV = limiteV / 10;
		this.posAtualH = ThreadLocalRandom.current().nextInt(rangeH * (id - 1), rangeH * id);
		this.posAtualV = ThreadLocalRandom.current().nextInt(rangeV * (id - 1), rangeV * id);

	}
	
	//métodos para obter dados das variaveis/atributos
	public Agente.Direcao getDirecao() {
		return direcao;
	}

	public JButton getAvatar() {
		return avatar;
	}

	public boolean isExecutar() {
		return executar;
	}

	public void setExecutar(boolean executar) {
		this.executar = executar;
	}

	public int getPosicaoH() {
		return posAtualH;
	}

	public int getPosicaoV() {
		return posAtualV;
	}

	//métodos para atualizar tela senão fica piscando
	public void atualizarCenario(){
		cenario.getPnPrincipal().remove(this.avatar);
		this.avatar.setBounds(this.posAtualH, this.posAtualV,tamanhoAvatar,tamanhoAvatar);
		cenario.getPnPrincipal().add(this.avatar);
		cenario.repaint();
	}

}

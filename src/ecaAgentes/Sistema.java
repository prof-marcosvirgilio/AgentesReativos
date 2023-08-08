package ecaAgentes;

import javax.swing.JFrame;

public class Sistema {

	public static void main(String[] args) {
		//instanciando objeto JFrame  
				FrameCenario frame = new FrameCenario();
				//m�todo de organizar o frame
				frame.pack();
				frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
				//m�todo para mostrar o frame 
				frame.setVisible(true);

	}

}

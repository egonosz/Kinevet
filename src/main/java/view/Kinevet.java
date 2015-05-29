package view;

import java.awt.EventQueue;
import java.io.UnsupportedEncodingException;

import javax.swing.JFrame;

import model.Jatek;
import controller.Kontroller;

public class Kinevet {

	private KFrame frame;
	private Kontroller kontroller;
	private Jatek jatek;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					Kinevet window = new Kinevet();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws UnsupportedEncodingException 
	 * @throws SecurityException 
	 */
	public Kinevet() throws SecurityException, UnsupportedEncodingException {
		
		
		frame = new KFrame("Ki nevet a végén?");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jatek= new Jatek();
		kontroller=new Kontroller(frame,jatek);
		
	
	}

	
}

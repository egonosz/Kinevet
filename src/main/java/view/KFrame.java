package view;

import java.awt.BorderLayout;
import java.awt.HeadlessException;

import javax.swing.JFrame;

import controller.Kontroller;

@SuppressWarnings("serial")
public class KFrame extends JFrame {
	private StatuszPanel sPanel;
	private Tabla tábla;
	private KMenuBar menüBar;
	private Kontroller kontroller;
	public KFrame(String name) throws HeadlessException {
		super(name);
		this.setBounds(0,0,442,525);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new BorderLayout(0, 0));
		this.setResizable(false);
		menüBar=new KMenuBar();
		this.setJMenuBar(menüBar);
		sPanel=new StatuszPanel();
		this.add(sPanel, BorderLayout.NORTH);
		tábla=new Tabla();
		this.add(tábla, BorderLayout.CENTER);
		}
	
	public void setKontroller(Kontroller kontroller)
	{
		this.kontroller=kontroller;
		menüBar.setKontroller(kontroller.getMenüKontroller());
	}
	public StatuszPanel getStatusPanel()
	{
		return sPanel;
	}
	public Tabla getTábla()
	{
		return tábla;
	}
}

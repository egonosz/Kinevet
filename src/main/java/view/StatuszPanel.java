/**
 * A dobókocka gomb kirajzolásáért és a játékos nevek megjelenítéséért felelős osztály.
 *
 * @author  Takác Ján
 * @version 1.0
 * @since   2015-05-25 
 */


package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import controller.Kontroller;
import model.Jatekos;

@SuppressWarnings("serial")
public class StatuszPanel extends JPanel {

	
	private List<JátékosLabel> játékosNévCímkék;
	private JButton dobóKocka;
	StatuszPanel() {
		super();
		this.setPreferredSize(new Dimension(442, 35));
		this.setBackground(new Color(219, 194, 94));
		dobóKocka = new JButton("Kocka");
		dobóKocka.setEnabled(false);
		this.add(dobóKocka);
		játékosNévCímkék = new ArrayList<JátékosLabel>(4);
		for (int i = 1; i < 5; i++) {
			JátékosLabel l = new JátékosLabel("Player" + i);

			l.setAlignmentX(Component.CENTER_ALIGNMENT);
			l.setHorizontalAlignment(SwingConstants.CENTER);
			játékosNévCímkék.add(l);
		}

	}
	public void setDobóKockaKontroller(Kontroller kontroller)
	{
		dobóKocka.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				kontroller.kockaDobás();
			}
			});
	}
	
	public void setDobóKockaSzöveg(String x)
	{
		dobóKocka.setText(x);
	}
	public void setKockaAktív(boolean b)
	{
		dobóKocka.setEnabled(b);
		validate();
	}
	public void setAktívJátékosCímke(int id)
	{
		for(JátékosLabel l :játékosNévCímkék)
		{
			if(l.getJátékosId()==id)
			{
				Border b=BorderFactory.createLineBorder(l.getForeground(),3);
				l.setBorder(b);
			}
			else 
			{
				l.setBorder(new EmptyBorder(0,0,0,0));
			}
		}
	validate();
	}

	public void beállítás(List<Jatekos> játékosok) {
		this.removeAll();
		if (játékosok.size() == 3) {
			this.setLayout(new GridLayout(0, 5, 0, 0));
		} else {
			this.setLayout(new GridLayout(0, játékosok.size() + 1, 0, 0));
		}
		SetJátékosNévCímkék(játékosok);

		this.add(játékosNévCímkék.get(0));
		if (játékosok.size() > 2) {
			this.add(játékosNévCímkék.get(1));
		}

		this.add(dobóKocka);
		if (játékosok.size() == 2) {
			this.add(játékosNévCímkék.get(1));
		} else {
			this.add(játékosNévCímkék.get(2));
			if (játékosok.size() == 4) {
				this.add(játékosNévCímkék.get(3));
			} else {
				játékosNévCímkék.get(3).setVisible(false);
			}
		}

		this.validate();
	}

	private void SetJátékosNévCímkék(List<Jatekos> játékosok) {
		for (int i = 0; i < játékosok.size(); i++) {
			Jatekos x = játékosok.get(i);
		
			játékosNévCímkék.get(i).setText(new String(x.getNév()));
			játékosNévCímkék.get(i).setVisible(true);
			játékosNévCímkék.get(i).setJátékosId(x.getId());
			switch (x.getSzín()) {
			case PIROS:
				játékosNévCímkék.get(i).setForeground(new Color(255, 0, 0));
				break;
			case ZÖLD:
				játékosNévCímkék.get(i).setForeground(new Color(0, 255, 0));
				break;
			case KÉK:
				játékosNévCímkék.get(i).setForeground(new Color(0, 0, 255));
				break;
			case FEKETE:
				játékosNévCímkék.get(i).setForeground(new Color(0, 0, 0));
				break;
			}

		}

	}

}

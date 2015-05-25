package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import model.Jatekos;

@SuppressWarnings("serial")
public class StatuszPanel extends JPanel {

	private List<JLabel> játékosNévCímkék;
	private JButton dobóKocka;

	StatuszPanel() {
		super();
		this.setPreferredSize(new Dimension(442, 35));
		dobóKocka = new JButton("Kocka");
		dobóKocka.setEnabled(false);
		this.add(dobóKocka);
		játékosNévCímkék = new ArrayList<JLabel>(4);
		for (int i = 1; i < 5; i++) {
			JLabel l = new JLabel("Player" + i);

			l.setAlignmentX(Component.CENTER_ALIGNMENT);
			l.setHorizontalAlignment(SwingConstants.CENTER);
			játékosNévCímkék.add(l);
		}

	}

	private void SetJátékosNévCímkék(List<Jatekos> játékosok) {
		for (int i = 0; i < játékosok.size(); i++) {
			for (Jatekos a : játékosok) {
				if (a.getKezdőHely().getKezdőPozíció() == (i * 10)) {

					játékosNévCímkék.get(i).setText(new String(a.getNév()));
					játékosNévCímkék.get(i).setVisible(true);
					switch (a.getSzín()) {
					case PIROS:
						játékosNévCímkék.get(i).setForeground(
								new Color(255, 0, 0));
						break;
					case ZÖLD:
						játékosNévCímkék.get(i).setForeground(
								new Color(0, 255, 0));
						break;
					case KÉK:
						játékosNévCímkék.get(i).setForeground(
								new Color(0, 0, 255));
						break;
					case FEKETE:
						játékosNévCímkék.get(i).setForeground(
								new Color(0, 0, 0));
						break;
					}
				}
			}
		}
		játékosNévCímkék.get(0).setBorder(BorderFactory.createLineBorder(játékosNévCímkék.get(0).getForeground()));
 
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
		dobóKocka.setEnabled(true);
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

}

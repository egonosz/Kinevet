

package view;

/**
 * Az Megjelentítő fő osztálya. A megjelenítő többi része csak innen érhető el.
 *
 * @author  Takác Ján
 * @version 1.0
 * @since   2015-05-25 
 */

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import controller.Kontroller;

@SuppressWarnings("serial")
public class KFrame extends JFrame {
	private StatuszPanel sPanel;
	private Tabla tábla;
	private KMenuBar menüBar;
	private Kontroller kontroller;

	public KFrame(String name) throws HeadlessException {
		super(name);
		this.setBounds(0, 0, 442, 525);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new BorderLayout(0, 0));
		this.setResizable(false);
		menüBar = new KMenuBar();
		this.setJMenuBar(menüBar);
		sPanel = new StatuszPanel();
		this.add(sPanel, BorderLayout.NORTH);
		tábla = new Tabla();
		this.add(tábla, BorderLayout.CENTER);
	}

	public void setKontroller(Kontroller kontroller) {
		this.kontroller = kontroller;
		menüBar.setKontroller(kontroller.getMenüKontroller());
		sPanel.setDobóKockaKontroller(kontroller);
		tábla.setKontroller(kontroller);
	}

	public StatuszPanel getStatusPanel() {
		return sPanel;
	}

	public boolean győzelemMsg(String név) {
		Object[] lehetőségek = { "Még egy játék", "Kilépek" };
		JOptionPane pane = new JOptionPane(new JLabel("Nyertél " + név + " !",
				JLabel.CENTER), JOptionPane.DEFAULT_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, lehetőségek, lehetőségek[0]);
		Dialog dialog = pane.createDialog(this, "Győzelem!");
		dialog.setVisible(true);
		Object kiválasztott = pane.getValue();
		if (lehetőségek[0].equals(kiválasztott))
			return true;
		return false;
	}

	public void nemTudszLépni(String név) {
		JOptionPane.showMessageDialog(this, "Nem tudsz lépni " + név + " :(");
	}

	public Tabla getTábla() {
		return tábla;
	}
}

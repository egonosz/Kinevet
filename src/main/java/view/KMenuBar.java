package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import controller.MenuKontroller;

@SuppressWarnings("serial")
public class KMenuBar extends JMenuBar {

	private MenuKontroller menuKontroller;

	private JMenu játékMenu;
	private JMenuItem újJátékMenuP;
	private JMenuItem mentésMenuP;
	private JMenuItem betöltésMenuP;
	private JMenuItem kilépésMenuP;

	public KMenuBar() {
		super();

		játékMenu = new JMenu("Játék");
		this.add(játékMenu);
		újJátékMenuP = new JMenuItem("Új játék");
		játékMenu.add(újJátékMenuP);
		kilépésMenuP = new JMenuItem("Exit");
		játékMenu.add(kilépésMenuP);

	}

	public void setKontroller(MenuKontroller menuKontroller) {
		this.menuKontroller = menuKontroller;
		ActionControllerBeállítás();
	}

	private void ActionControllerBeállítás() {
		újJátékMenuP.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				List<String> names = new ArrayList<String>();
				for (int i = 1; i < 5; i++) {
					String name = JOptionPane.showInputDialog(újJátékMenuP, i
							+ ". Játékos neve?", null);
					if (name == null || name.isEmpty() == true) {
						if (i > 2)
							break;
						else
							i--;

					}
					else {
						names.add(name);
					}

				}
				menuKontroller.újJáték(names);
			}
		});

		kilépésMenuP.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				menuKontroller.kilépés();
			}
		});

	}
}

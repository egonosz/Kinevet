package controller;

import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import model.Babu;
import model.Jatekos;
import model.Kezdohely;
import model.Szin;
import model.Jatek;
import view.KFrame;

public class Kontroller {
	private KFrame frame;
	private Jatek játék;
	private MenuKontroller menüKontroller;

	public Kontroller(KFrame frame, Jatek jatek) {
		super();

		this.menüKontroller = new MenuKontroller(this);

		this.frame = frame;
		this.frame.setKontroller(this);
		this.játék = jatek;

	}

	public void játékLétrehozása(List<String> nevek) {
		List<Szin> temp_színek = new ArrayList<Szin>(4);
		temp_színek.add(Szin.PIROS);
		temp_színek.add(Szin.ZÖLD);
		temp_színek.add(Szin.KÉK);
		temp_színek.add(Szin.FEKETE);
		List<Kezdohely> temp_kezdőhelyek = new ArrayList<Kezdohely>(4);
		temp_kezdőhelyek.add(Kezdohely.ÉSZAK);
		temp_kezdőhelyek.add(Kezdohely.KELET);
		temp_kezdőhelyek.add(Kezdohely.DÉL);
		temp_kezdőhelyek.add(Kezdohely.NYUGAT);
		Random rand = new Random();
		List<Jatekos> játékosok = new ArrayList<Jatekos>(nevek.size());
		for (int i = 0; i < nevek.size(); i++) {
			int rsz = rand.nextInt(4 - i);
			int rk = rand.nextInt(4 - i);
			játékosok.add(new Jatekos(temp_kezdőhelyek.get(rk), temp_színek
					.get(rsz), nevek.get(i), i));
			temp_színek.remove(rsz);
			temp_kezdőhelyek.remove(rk);
		}
		for (int i = 0; i < játékosok.size(); i++) {
			for (int j = i; j < játékosok.size(); j++) {
				if (játékosok.get(i).getKezdőHely().getKezdőPozíció() > játékosok
						.get(j).getKezdőHely().getKezdőPozíció()) {
					Collections.swap(játékosok, j, i);
				}
			}
			játékosok.get(i).setId(i);
		}
		játék.setJatekosok(játékosok);
		játék.setJátékosKöre(0);
		frame.getStatusPanel().beállítás(játékosok);
		frame.getStatusPanel().setAktívJátékosCímke(0);
		frame.getStatusPanel().setKockaAktív(true);
		frame.getTábla().setTáblaAKezdőállapotba(játékosok);
		frame.repaint();

	}

	public void Dobás() {
		Random rand = new Random();
		int x = rand.nextInt(6) + 1;
		játék.setUtolsóGurítás(x);
		frame.getStatusPanel().setDobóKockaSzöveg(Integer.toString(x));
		frame.getStatusPanel().setKockaAktív(false);
		frame.getTábla().setAktív(true);
	}

	public boolean vanOttSajátBábu(Jatekos j, Babu b, int i) {
		for (Babu bb : j.getBábuk()) {
			if (bb != b) {
				if (i == bb.getPozíció()) {
					return false;
				}
			}
		}
		return true;
	}

	public void Lépés(Jatekos j, Babu b) {

		// b.setPozíció(b.getPozíció()+játék.getUtolsóGurítás());
		if (b.getPozíció() == -1) {
			if (játék.getUtolsóGurítás() == 6 || játék.getUtolsóGurítás() == 1) {
				b.setPozíció(j.getKezdőHely().getKezdőPozíció());
			}
		} else {
			int köv_pozíció = b.getPozíció() + játék.getUtolsóGurítás();
			if ((b.getPozíció() < j.getKezdőHely().getKezdőPozíció())
					&& (köv_pozíció >= j.getKezdőHely().getKezdőPozíció())) {
				int kül = (köv_pozíció % 40)
						- j.getKezdőHely().getKezdőPozíció();

				int köv_valósPozíció = -2 - (kül % 4);
				if (!vanOttSajátBábu(j, b, köv_valósPozíció)) {
					b.setPozíció(köv_valósPozíció);
				} else {
					return;
				}
			}
		}
		if (játék.getUtolsóGurítás() != 6) {
			játék.setJátékosKöre((játék.getJátékosKöre() + 1)
					% játék.getJátékosokSzáma());
		}

		frame.getStatusPanel().setAktívJátékosCímke(játék.getJátékosKöre());
		frame.getStatusPanel().setKockaAktív(true);
		frame.getStatusPanel().setDobóKockaSzöveg("Kocka");
		frame.getTábla().setAktív(false);
		frame.getTábla().bábukFrissítése(játék.getJatekosok());
		frame.repaint();
	}

	public int getAktívJátékos() {
		return játék.getJátékosKöre();
	}

	public List<Jatekos> getJátékosok() {
		return játék.getJatekosok();
	}

	public MenuKontroller getMenüKontroller() {
		return menüKontroller;
	}

}

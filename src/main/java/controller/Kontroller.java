
package controller;

/**
 * A játék alkalmazás logikáját tartalmazza. A kontroller tartalmazza a játék szabályokat.
 *
 * @author  Takác Ján
 * @version 1.0
 * @since   2015-05-25 
 */


import java.awt.event.MouseAdapter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

import javax.swing.JOptionPane;

import model.Allapot;
import model.BabuPozicio;
import model.Jatekos;
import model.Kezdohely;
import model.Szin;
import model.Jatek;
import view.KFrame;

public class Kontroller {
	private KFrame frame;
	private Jatek játék;
	private MenuKontroller menüKontroller;

	protected static Logger logger = Logger.getLogger(Kontroller.class.getName());

	/**
	 * A view és a model segítségével hozza létre a játékot.
	 * 
	 * @param frame
	 *            a view
	 * @param jatek
	 *            a model
	 */

	public Kontroller(KFrame frame, Jatek jatek){
		super();
		  StreamHandler handler=new StreamHandler(System.out,new SimpleFormatter());
		  try {
			handler.setEncoding("UTF8");
		} catch (SecurityException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.addHandler(handler);
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
		logger.log(Level.INFO, "Uj jatek : " + nevek.size()
				+ " darab  jatekossal");
	}

	/**
	 * Generál egy véletlenszerű számot amelyet át ad a
	 * <code>kockaDobásEsemény</code> függvénynek.
	 */
	public void kockaDobás() {
		Random rand = new Random();
		int x = rand.nextInt(6) + 1;
		kockaDobásEsemény(x);
	}

	/**
	 * Gondoskodik a szükséges teendőkről kocka dobás esetén. Ha tudlépnia
	 * játékos, akkor aktiválja a táblát és deaktiválja a kockát. Ha nem
	 * tudlépni akkor vált a következő játékosra.
	 * 
	 * @param x
	 *            hanyast dobtak
	 */

	public void kockaDobásEsemény(int x) {
		játék.setUtolsóGurítás(x);
		frame.getStatusPanel().setDobóKockaSzöveg(Integer.toString(x));
		frame.getStatusPanel().setKockaAktív(false);
		List<BabuPozicio> l = léphetőPozíciók(getAktívJátékos());
		logger.log(Level.INFO, "Kockadobas : " + x);
		if (l.isEmpty()) {

			frame.nemTudszLépni(getAktívJátékos().getNév());
			if (játék.getUtolsóGurítás() != 6) {
				játék.setJátékosKöre((játék.getJátékosKöre() + 1)
						% játék.getJátékosokSzáma());
			}

			frame.getStatusPanel().setAktívJátékosCímke(játék.getJátékosKöre());
			frame.getStatusPanel().setKockaAktív(true);
			frame.getStatusPanel().setDobóKockaSzöveg("Kocka");
			frame.getTábla().setAktív(false);
			return;
		}

		frame.getTábla().setLéphetőMezők(l, getAktívJátékos());
		frame.getTábla().setAktív(true);
		frame.repaint();
		
	}

	/**
	 * Ha <code>b</code> egy védett mező pozíciűja igazat ad vissza
	 * 
	 * @param b
	 *            a tesztelni kívánt mező.
	 * @return Igaz ha a mező védett
	 */
	public boolean isVédetMező(BabuPozicio b) {
		if (b.getÁllapot() == Allapot.PÁLYA)
			for (int i = 0; i < 4; i++) {
				if ((b.getPozíció() == i * 10)
						&& (b.getÁllapot() == Allapot.PÁLYA)) {
					return true;
				}
			}
		return false;
	}

	/**
	 * Vissza adja, hogy van-e a játékosnak bábuja a kiválasztott mezőn.
	 * 
	 * @param j
	 *            Játékos mlynek keressüka bábuit
	 * @param b
	 *            amely pozíción keressük
	 * @return
	 */

	public boolean vanAcélbaOttSajátBábu(Jatekos j, BabuPozicio b) {
		if (b.getÁllapot() != Allapot.CÉL)
			return false;
		for (BabuPozicio bb : j.getBábuk()) {
			if (b.equals(bb)) {
				return true;
			} else if ((bb.getÁllapot() == Allapot.CÉL)) {
				if (((b.getPozíció() == 1) && (bb.getPozíció() == 5))
						|| ((b.getPozíció() == 5) && (bb.getPozíció() == 1))
						|| ((b.getPozíció() == 2) && (bb.getPozíció() == 4))
						|| ((b.getPozíció() == 4) && (bb.getPozíció() == 2))) {
					return true;
				}
			}

		}
		return false;
	}

	/**
	 * vissza adja hogy az ellenségnek van a kiválasztott bábuja a mezőn.
	 * 
	 * @param j
	 *            játékos aki keresi az ellenséges bábukat
	 * @param b
	 *            Bábu pozíció ahol keressük az ellenséget
	 * @return
	 */

	public boolean vanOttEllenségesBábu(Jatekos j, BabuPozicio b) {
		for (Jatekos ej : játék.getJatekosok())
			for (BabuPozicio bb : ej.getBábuk()) {
				if (j.getId() != ej.getId()) {
					if (b.equals(bb)) {
						return true;
					}
				}
			}
		return false;
	}

	/**
	 * Kidobja a <code>b</code> mezőn lévő bábukat ha lehetséges.
	 * 
	 * @param j
	 *            A játékos aki dobni akar.
	 * @param b
	 *            A mező melyről ki akar dobni bábukat.
	 * @return Hamis ha nem sikerült dobnia, vagy nem volt a mezőn ellenfél.
	 */

	public boolean Dobás(Jatekos j, BabuPozicio b) {
		if (b.getÁllapot() != Allapot.PÁLYA)
			return false;
		if (isVédetMező(b))
			return false;
		boolean dobott = false;
		for (Jatekos ej : játék.getJatekosok())
			if (j != ej) {
				for (BabuPozicio bb : ej.getBábuk()) {
					if (bb.getÁllapot() == Allapot.PÁLYA)
						if (b.equals(bb)) {
							bb.setPozíció(0);
							bb.setÁllapot(Allapot.BÁZIS);
							dobott = true;
							logger.log(Level.INFO, "Dobas : " + ej.getNév()
									+ " " + b.getPozíció()
									+ " mezon levo babuja(i) bazisra kerul(nek).");

						}

				}
			}
		if (dobott)
			return true;
		return false;
	}

	/**
	 * Vissza adja az utlosó kocka dobás alapján a bábu következő pozícióját és
	 * Állapotát. Ha nem tud lépni vissza adja az előző. Nem ellenőrzi, hogy
	 * van-e másik bábu a mezőn.
	 * 
	 * @param j
	 *            a játékos
	 * @param b
	 *            a bábu pozíciója
	 * @return
	 */

	public BabuPozicio getKovPoz(Jatekos j, BabuPozicio b) {
		int g = játék.getUtolsóGurítás();
		int bP = b.getPozíció();
		int kP = j.getKezdőHely().getKezdőPozíció();
		if (b.getÁllapot() == Allapot.BÁZIS) /* Bábu Bázis mezőn */
		{
			if (g == 6 || g == 1) {
				return new BabuPozicio(kP, Allapot.PÁLYA);
			}
			return b;
		} else if (b.getÁllapot() == Allapot.CÉL) /* Bábu győzelmi mezőn */
		{
			BabuPozicio x = new BabuPozicio((g + bP) % 6, Allapot.CÉL);
			if (x.equals(b)) {
				return b;
			}
			return x;
		} else {
			int x = 0; /* Lokális köv pozíció */
			if (bP < kP) {
				x = bP + 40 - kP + g;
			} else {
				x = bP - kP + g;
			}

			if (x >= 40) /* cél mezőre lépés */
			{
				return new BabuPozicio((x - 40), Allapot.CÉL);
			} else /* Sima mezőre lépés */
			{
				return new BabuPozicio((bP + g) % 40, Allapot.PÁLYA);
			}

		}

	}

	/**
	 * Le ellenőrzi, hogy oda lehet-e lépni egy CÉL mezőre.
	 * 
	 * @param j
	 *            játékos aki lépni akar
	 * @param ide
	 *            Ezt a mezőt ellenőrzi.
	 * @return Igaz ha oda tud lépni
	 */
	public boolean odaLehetLépni(Jatekos j, BabuPozicio ide) {
		if (ide.getÁllapot() == Allapot.CÉL) {
			if (vanAcélbaOttSajátBábu(j, ide)) {
				return false;
			}

		}
		return true;
	}

	/**
	 * Vissza adja egy bábuval melyik mezőre lehet lépni.
	 * 
	 * @param j
	 *            játékos aki lépni akar
	 * @param b
	 *            a bábu amelyikkel lépni akar
	 * @return visszatérés a mezővel amire léphet
	 */

	public BabuPozicio léphetőPozíció(Jatekos j, BabuPozicio b) {
		BabuPozicio x = getKovPoz(j, b);
		if (x != b)
			if (odaLehetLépni(j, x))
				return x;
		return null;
	}

	/**
	 * Vissza adja az összes mezőt ahova tud lépni egy bizonyos játékos.
	 * 
	 * @param j
	 *            melyik játékos bábuit ellenőrizze
	 * @return apozíciók listája ahova tud lépne a játékos.
	 */

	public List<BabuPozicio> léphetőPozíciók(Jatekos j) {
		List<BabuPozicio> l = new ArrayList<BabuPozicio>(4);

		for (BabuPozicio b : j.getBábuk()) {
			BabuPozicio x = léphetőPozíció(j, b);
			if (x != null) {
				l.add(x);
			}
		}

		return l;
	}

	/**
	 * Meghívja a <code>frame</code> győzelem kijelző függvényét. Ezenkívül egy
	 * új játék indításáról vagy kilépésről is gondolkozik, hateljesültek a
	 * győzelmi feltételek.
	 * 
	 * @param j
	 *            a győztes játékos
	 */

	public void győzelem(Jatekos j) {
		logger.log(Level.INFO, "Győzött : " + j.getNév());

		if (frame.győzelemMsg(j.getNév())) {
			List<String> names = new ArrayList<String>();
			for (int i = 1; i < 5; i++) {
				String name = JOptionPane.showInputDialog(frame, i
						+ ". Játékos neve?", null);
				if (name == null || name.isEmpty() == true) {
					if (i > 2)
						break;
					else
						i--;

				} else {
					names.add(name);
				}
			}
			játékLétrehozása(names);
			return;
		}
		System.exit(0);
	}

	/**
	 * Ez a függvény gondoskodik a lépésről. Magába foglalja a dobást a bábu
	 * áthelyezéseke. és a kör váltást.
	 * 
	 * @param j
	 *            játékos aki lépni akar
	 * @param b
	 *            bábu amelyikkel
	 */
	public void Lépés(Jatekos j, BabuPozicio b) {
		BabuPozicio bK = getKovPoz(j, b);
		if (b == bK) {
			return;
		} else if (bK.getÁllapot() == Allapot.CÉL) {
			if (vanAcélbaOttSajátBábu(j, bK)) {
				return;
			}
			int i = 0;
			b.setPozíció(bK.getPozíció());
			b.setÁllapot(bK.getÁllapot());
			for (BabuPozicio bb : j.getBábuk()) {
				if (bb.getÁllapot().equals(Allapot.CÉL))
					i++;
			}
			if (i == 4)
				győzelem(j);
			frame.getTábla().bábukFrissítése(játék.getJatekosok());
		} else {

			Dobás(j, bK);
			logger.log(Level.INFO, "Lepes : " + j.getNév()+" babuja a " + b.getPozíció()
					+ " mezorol a"+bK.getPozíció()+" mezore lepett.");
			b.setPozíció(bK.getPozíció());
			b.setÁllapot(bK.getÁllapot());
		}

		if (játék.getUtolsóGurítás() != 6) {
			játék.setJátékosKöre((játék.getJátékosKöre() + 1)
					% játék.getJátékosokSzáma());
		}

		
		frame.getTábla().setAktív(false);
		frame.getTábla().bábukFrissítése(játék.getJatekosok());
		frame.getStatusPanel().setAktívJátékosCímke(játék.getJátékosKöre());
		frame.getStatusPanel().setKockaAktív(true);
		frame.getStatusPanel().setDobóKockaSzöveg("Kocka");

		frame.repaint();
	}

	public int getAktívJátékosId() {
		return játék.getJátékosKöre();
	}

	public Jatekos getAktívJátékos() {
		for (Jatekos j : játék.getJatekosok()) {
			if (j.getId() == getAktívJátékosId())
				return j;
		}
		return null;
	}

	public List<Jatekos> getJátékosok() {
		return játék.getJatekosok();
	}

	public MenuKontroller getMenüKontroller() {
		return menüKontroller;
	}

}

package KinevetTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;

import model.Allapot;
import model.BabuPozicio;
import model.Jatek;
import model.Jatekos;
import model.Kezdohely;
import model.Szin;

import org.junit.Before;
import org.junit.Test;

import view.KFrame;
import controller.Kontroller;

public class KontrollerTest {
	KFrame frame;
	Jatek jatek;
	Kontroller kontroller;
	@Before
	public void init(){
		frame = new KFrame("Ki nevet a végén?");
		jatek= new Jatek();
		kontroller=new Kontroller(frame,jatek);
		List<String> nevek=new ArrayList<String>();
		nevek.add("egyes");
		nevek.add("kettes");
		nevek.add("hármas");
		nevek.add("négyes");
		
		kontroller.játékLétrehozása(nevek);
		
	}
	@Test
	public void testkockaDobásEsemény() {

		
		
		jatek.setJátékosKöre(0);
		kontroller.kockaDobásEsemény(1);
		assertEquals(jatek.getJátékosKöre(),0);
		assertEquals(jatek.getUtolsóGurítás(),1);
		
		jatek.setJátékosKöre(0);
		
		kontroller.kockaDobásEsemény(6);
		assertEquals(jatek.getJátékosKöre(),0);
		assertEquals(jatek.getUtolsóGurítás(),6);
		
	}
	
	@Test
	public void testisVédettMező(){
		assertTrue(kontroller.isVédetMező(new BabuPozicio(0,Allapot.PÁLYA)));
		assertTrue(kontroller.isVédetMező(new BabuPozicio(10,Allapot.PÁLYA)));
		assertTrue(kontroller.isVédetMező(new BabuPozicio(20,Allapot.PÁLYA)));
		assertTrue(kontroller.isVédetMező(new BabuPozicio(30,Allapot.PÁLYA)));
		
		assertFalse(kontroller.isVédetMező(new BabuPozicio(1,Allapot.PÁLYA)));
		assertFalse(kontroller.isVédetMező(new BabuPozicio(39,Allapot.PÁLYA)));
		assertFalse(kontroller.isVédetMező(new BabuPozicio(0,Allapot.BÁZIS)));
		assertFalse(kontroller.isVédetMező(new BabuPozicio(0,Allapot.CÉL)));
	}
	@Test
	public void testvanOttSajátBábu()
	{
		kontroller.getJátékosok().set(0,new Jatekos(Kezdohely.ÉSZAK,Szin.PIROS,"Első",0));
		kontroller.getJátékosok().set(1,new Jatekos(Kezdohely.DÉL,Szin.KÉK,"Második",1));
		kontroller.getJátékosok().set(2,new Jatekos(Kezdohely.KELET,Szin.ZÖLD,"Harmadik",2));
		kontroller.getJátékosok().set(3,new Jatekos(Kezdohely.NYUGAT,Szin.FEKETE,"Negyedik",3));
		Jatekos j=kontroller.getJátékosok().get(0);
		Vector<BabuPozicio> babuk=new Vector<BabuPozicio>(4);
		babuk.add(new BabuPozicio(0,Allapot.CÉL));
		babuk.add(new BabuPozicio(1,Allapot.CÉL));
		babuk.add(new BabuPozicio(2,Allapot.CÉL));
		babuk.add(new BabuPozicio(3,Allapot.CÉL));
		kontroller.getJátékosok().get(0).setBábuk(babuk);
		jatek.setJátékosKöre(0);
		
		assertTrue(kontroller.vanAcélbaOttSajátBábu(j, new BabuPozicio(0,Allapot.CÉL)));
		assertTrue(kontroller.vanAcélbaOttSajátBábu(j, new BabuPozicio(1,Allapot.CÉL)));
		assertTrue(kontroller.vanAcélbaOttSajátBábu(j, new BabuPozicio(2,Allapot.CÉL)));
		assertTrue(kontroller.vanAcélbaOttSajátBábu(j, new BabuPozicio(3,Allapot.CÉL)));
		assertTrue(kontroller.vanAcélbaOttSajátBábu(j, new BabuPozicio(4,Allapot.CÉL)));
		assertTrue(kontroller.vanAcélbaOttSajátBábu(j, new BabuPozicio(5,Allapot.CÉL)));
		
		assertFalse(kontroller.vanAcélbaOttSajátBábu(j, new BabuPozicio(3,Allapot.PÁLYA)));
		assertFalse(kontroller.vanAcélbaOttSajátBábu(j, new BabuPozicio(0,Allapot.BÁZIS)));
		assertFalse(kontroller.vanAcélbaOttSajátBábu(j, new BabuPozicio(39,Allapot.PÁLYA)));
		
		assertFalse(kontroller.vanAcélbaOttSajátBábu(j, new BabuPozicio(0,Allapot.PÁLYA)));
		assertFalse(kontroller.vanAcélbaOttSajátBábu(j, new BabuPozicio(1,Allapot.PÁLYA)));
		assertFalse(kontroller.vanAcélbaOttSajátBábu(j, new BabuPozicio(2,Allapot.PÁLYA)));
		assertFalse(kontroller.vanAcélbaOttSajátBábu(j, new BabuPozicio(3,Allapot.PÁLYA)));
		assertFalse(kontroller.vanAcélbaOttSajátBábu(j, new BabuPozicio(4,Allapot.PÁLYA)));
		assertFalse(kontroller.vanAcélbaOttSajátBábu(j, new BabuPozicio(5,Allapot.PÁLYA)));
		
		
		babuk.set(0,new BabuPozicio(10,Allapot.PÁLYA));
		babuk.set(1,new BabuPozicio(15,Allapot.PÁLYA));
		babuk.set(2,new BabuPozicio(5,Allapot.PÁLYA));
		babuk.set(3,new BabuPozicio(20,Allapot.PÁLYA));
		kontroller.getJátékosok().get(0).setBábuk(babuk);

		assertFalse(kontroller.vanAcélbaOttSajátBábu(j, new BabuPozicio(0,Allapot.CÉL)));
		assertFalse(kontroller.vanAcélbaOttSajátBábu(j, new BabuPozicio(1,Allapot.CÉL)));
		assertFalse(kontroller.vanAcélbaOttSajátBábu(j, new BabuPozicio(2,Allapot.CÉL)));
		assertFalse(kontroller.vanAcélbaOttSajátBábu(j, new BabuPozicio(3,Allapot.CÉL)));
		assertFalse(kontroller.vanAcélbaOttSajátBábu(j, new BabuPozicio(4,Allapot.CÉL)));
		assertFalse(kontroller.vanAcélbaOttSajátBábu(j, new BabuPozicio(5,Allapot.CÉL)));
		}
	@Test
	public void testVanOttEllenségesBábu()
	{
		jatek.setJátékosKöre(0);
		kontroller.getJátékosok().set(0,new Jatekos(Kezdohely.ÉSZAK,Szin.PIROS,"Első",0));
		kontroller.getJátékosok().set(1,new Jatekos(Kezdohely.DÉL,Szin.KÉK,"Második",1));
		kontroller.getJátékosok().set(2,new Jatekos(Kezdohely.KELET,Szin.ZÖLD,"Harmadik",2));
		kontroller.getJátékosok().set(3,new Jatekos(Kezdohely.NYUGAT,Szin.FEKETE,"Negyedik",3));
		Jatekos j=kontroller.getJátékosok().get(0);
		Vector<BabuPozicio> ellensegesBabuk=new Vector<BabuPozicio>(4);
		ellensegesBabuk.add(new BabuPozicio(0,Allapot.PÁLYA));
		ellensegesBabuk.add(new BabuPozicio(10,Allapot.PÁLYA));
		ellensegesBabuk.add(new BabuPozicio(39,Allapot.PÁLYA));
		ellensegesBabuk.add(new BabuPozicio(1,Allapot.PÁLYA));
		kontroller.getJátékosok().get(1).setBábuk(ellensegesBabuk);
		
		assertTrue(kontroller.vanOttEllenségesBábu(j, new BabuPozicio(0,Allapot.PÁLYA)));
		assertTrue(kontroller.vanOttEllenségesBábu(j, new BabuPozicio(10,Allapot.PÁLYA)));
		assertTrue(kontroller.vanOttEllenségesBábu(j, new BabuPozicio(39,Allapot.PÁLYA)));
		assertTrue(kontroller.vanOttEllenségesBábu(j, new BabuPozicio(1,Allapot.PÁLYA)));
		
		assertFalse(kontroller.vanOttEllenségesBábu(j, new BabuPozicio(0,Allapot.BÁZIS)));
		assertFalse(kontroller.vanOttEllenségesBábu(j, new BabuPozicio(11,Allapot.PÁLYA)));
		assertFalse(kontroller.vanOttEllenségesBábu(j, new BabuPozicio(2,Allapot.PÁLYA)));
		assertFalse(kontroller.vanOttEllenségesBábu(j, new BabuPozicio(1,Allapot.CÉL)));
		
		
	}
	@Test
	public void testDobás()
	{
		jatek.setJátékosKöre(0);
		kontroller.getJátékosok().set(0,new Jatekos(Kezdohely.ÉSZAK,Szin.PIROS,"Első",0));
		kontroller.getJátékosok().set(1,new Jatekos(Kezdohely.DÉL,Szin.KÉK,"Második",1));
		kontroller.getJátékosok().set(2,new Jatekos(Kezdohely.KELET,Szin.ZÖLD,"Harmadik",2));
		kontroller.getJátékosok().set(3,new Jatekos(Kezdohely.NYUGAT,Szin.FEKETE,"Negyedik",3));
		Jatekos j=kontroller.getJátékosok().get(0);
		Vector<BabuPozicio> ellensegesBabuk=new Vector<BabuPozicio>(4);
		ellensegesBabuk.add(new BabuPozicio(1,Allapot.PÁLYA));
		ellensegesBabuk.add(new BabuPozicio(10,Allapot.PÁLYA));
		ellensegesBabuk.add(new BabuPozicio(39,Allapot.PÁLYA));
		ellensegesBabuk.add(new BabuPozicio(0,Allapot.PÁLYA));
		kontroller.getJátékosok().get(1).setBábuk(ellensegesBabuk);
		
		assertTrue(kontroller.Dobás(j, new BabuPozicio(1,Allapot.PÁLYA)));
		assertEquals(ellensegesBabuk.get(0),new BabuPozicio(0,Allapot.BÁZIS));
		
		assertTrue(kontroller.Dobás(j, new BabuPozicio(39,Allapot.PÁLYA)));
		assertEquals(ellensegesBabuk.get(2),new BabuPozicio(0,Allapot.BÁZIS));
		
		assertFalse(kontroller.Dobás(j, new BabuPozicio(10,Allapot.PÁLYA)));
		assertEquals(ellensegesBabuk.get(1),new BabuPozicio(10,Allapot.PÁLYA));

		assertFalse(kontroller.Dobás(j, new BabuPozicio(0,Allapot.PÁLYA)));
		assertEquals(ellensegesBabuk.get(3),new BabuPozicio(0,Allapot.PÁLYA));

		assertFalse(kontroller.Dobás(j, new BabuPozicio(12,Allapot.PÁLYA)));
		
		assertFalse(kontroller.Dobás(j, new BabuPozicio(10,Allapot.PÁLYA)));
		
		
	}
	@Test
	public void testgetKovPoz()
	{
		jatek.setJátékosKöre(0);
		kontroller.getJátékosok().set(0,new Jatekos(Kezdohely.ÉSZAK,Szin.PIROS,"Első",0));
		kontroller.getJátékosok().set(1,new Jatekos(Kezdohely.DÉL,Szin.KÉK,"Második",1));
		kontroller.getJátékosok().set(2,new Jatekos(Kezdohely.KELET,Szin.ZÖLD,"Harmadik",2));
		kontroller.getJátékosok().set(3,new Jatekos(Kezdohely.NYUGAT,Szin.FEKETE,"Negyedik",3));
		Jatekos j=kontroller.getJátékosok().get(0);
		Vector<BabuPozicio> s=new Vector<BabuPozicio>(4);
		
		s.add(new BabuPozicio(0,Allapot.PÁLYA));
		s.add(new BabuPozicio(10,Allapot.PÁLYA));
		s.add(new BabuPozicio(0,Allapot.CÉL));
		s.add(new BabuPozicio(5,Allapot.CÉL));
		kontroller.getJátékosok().get(0).setBábuk(s);
		
		
		BabuPozicio b=new BabuPozicio(0,Allapot.BÁZIS);
		jatek.setUtolsóGurítás(2);
		assertEquals(kontroller.getKovPoz(j,b),b);
		jatek.setUtolsóGurítás(6);
		assertEquals(kontroller.getKovPoz(j,b),new BabuPozicio(0,Allapot.PÁLYA));
		jatek.setUtolsóGurítás(1);
		assertEquals(kontroller.getKovPoz(j,b),new BabuPozicio(0,Allapot.PÁLYA));
		
		b=new BabuPozicio(39,Allapot.PÁLYA);
		jatek.setUtolsóGurítás(1);
		assertEquals(kontroller.getKovPoz(j,b),new BabuPozicio(0,Allapot.CÉL));
		jatek.setUtolsóGurítás(2);
		assertEquals(kontroller.getKovPoz(j,b),new BabuPozicio(1,Allapot.CÉL));
		jatek.setUtolsóGurítás(3);
		assertEquals(kontroller.getKovPoz(j,b),new BabuPozicio(2,Allapot.CÉL));
		
		b=new BabuPozicio(2,Allapot.CÉL);
		jatek.setUtolsóGurítás(4);
		assertEquals(kontroller.getKovPoz(j,b),new BabuPozicio(0,Allapot.CÉL));
		jatek.setUtolsóGurítás(1);
		assertEquals(kontroller.getKovPoz(j,b),new BabuPozicio(3,Allapot.CÉL));
		jatek.setUtolsóGurítás(6);
		assertEquals(kontroller.getKovPoz(j,b),b);
		
		b=new BabuPozicio(15,Allapot.PÁLYA);
		jatek.setUtolsóGurítás(6);
		assertEquals(kontroller.getKovPoz(j,b),new BabuPozicio(21,Allapot.PÁLYA));
		
		b=new BabuPozicio(20,Allapot.PÁLYA);
		jatek.setUtolsóGurítás(3);
		assertEquals(kontroller.getKovPoz(j,b),new BabuPozicio(23,Allapot.PÁLYA));
		
	}
	@Test
	public void testLéphetőPozíció()
	{
		jatek.setJátékosKöre(0);
		kontroller.getJátékosok().set(0,new Jatekos(Kezdohely.ÉSZAK,Szin.PIROS,"Első",0));
		kontroller.getJátékosok().set(1,new Jatekos(Kezdohely.DÉL,Szin.KÉK,"Második",1));
		kontroller.getJátékosok().set(2,new Jatekos(Kezdohely.KELET,Szin.ZÖLD,"Harmadik",2));
		kontroller.getJátékosok().set(3,new Jatekos(Kezdohely.NYUGAT,Szin.FEKETE,"Negyedik",3));
		Jatekos j=kontroller.getJátékosok().get(0);
		
		Vector<BabuPozicio> s=new Vector<BabuPozicio>(4);
		s.add(new BabuPozicio(0,Allapot.PÁLYA));
		s.add(new BabuPozicio(25,Allapot.PÁLYA));
		s.add(new BabuPozicio(0,Allapot.CÉL));
		s.add(new BabuPozicio(5,Allapot.CÉL));
		kontroller.getJátékosok().get(0).setBábuk(s);
		
		
		BabuPozicio b=new BabuPozicio(0,Allapot.BÁZIS);
		jatek.setUtolsóGurítás(2);
		assertEquals(kontroller.léphetőPozíció(j,b),null);
		jatek.setUtolsóGurítás(6);
		assertEquals(kontroller.léphetőPozíció(j,b),new BabuPozicio(0,Allapot.PÁLYA));
		jatek.setUtolsóGurítás(1);
		assertEquals(kontroller.léphetőPozíció(j,b),new BabuPozicio(0,Allapot.PÁLYA));
		
		b=new BabuPozicio(39,Allapot.PÁLYA);
		jatek.setUtolsóGurítás(1);
		assertEquals(kontroller.léphetőPozíció(j,b),null);
		jatek.setUtolsóGurítás(2);
		assertEquals(kontroller.léphetőPozíció(j,b),null);
		jatek.setUtolsóGurítás(3);
		assertEquals(kontroller.léphetőPozíció(j,b),new BabuPozicio(2,Allapot.CÉL));
		
		b=new BabuPozicio(2,Allapot.CÉL);
		jatek.setUtolsóGurítás(4);
		assertEquals(kontroller.léphetőPozíció(j,b),null);
		jatek.setUtolsóGurítás(1);
		assertEquals(kontroller.léphetőPozíció(j,b),new BabuPozicio(3,Allapot.CÉL));
		jatek.setUtolsóGurítás(6);
		assertEquals(kontroller.léphetőPozíció(j,b),null);
		
		b=new BabuPozicio(15,Allapot.PÁLYA);
		jatek.setUtolsóGurítás(6);
		assertEquals(kontroller.léphetőPozíció(j,b),new BabuPozicio(21,Allapot.PÁLYA));
		
		b=new BabuPozicio(20,Allapot.PÁLYA);
		jatek.setUtolsóGurítás(5);
		assertEquals(kontroller.léphetőPozíció(j,b),new BabuPozicio(25,Allapot.PÁLYA));
	
	}
	@Test
	public void testLépés()
	{
		jatek.setJátékosKöre(0);
		kontroller.getJátékosok().set(0,new Jatekos(Kezdohely.ÉSZAK,Szin.PIROS,"Első",0));
		kontroller.getJátékosok().set(1,new Jatekos(Kezdohely.DÉL,Szin.KÉK,"Második",1));
		kontroller.getJátékosok().set(2,new Jatekos(Kezdohely.KELET,Szin.ZÖLD,"Harmadik",2));
		kontroller.getJátékosok().set(3,new Jatekos(Kezdohely.NYUGAT,Szin.FEKETE,"Negyedik",3));
		Jatekos j=kontroller.getJátékosok().get(0);
		
		Vector<BabuPozicio> s=new Vector<BabuPozicio>(4);
		s.add(new BabuPozicio(0,Allapot.BÁZIS));
		s.add(new BabuPozicio(5,Allapot.PÁLYA));
		s.add(new BabuPozicio(17,Allapot.PÁLYA));
		s.add(new BabuPozicio(5,Allapot.CÉL));
		kontroller.getJátékosok().get(0).setBábuk(s);
		
		Vector<BabuPozicio> e=new Vector<BabuPozicio>(4);
		e.add(new BabuPozicio(6,Allapot.PÁLYA));
		e.add(new BabuPozicio(10,Allapot.PÁLYA));
		e.add(new BabuPozicio(21,Allapot.PÁLYA));
		e.add(new BabuPozicio(2,Allapot.CÉL));
		kontroller.getJátékosok().get(1).setBábuk(e);

		Vector<BabuPozicio> e2=new Vector<BabuPozicio>(4);
		e2.add(new BabuPozicio(6,Allapot.PÁLYA));
		e2.add(new BabuPozicio(10,Allapot.PÁLYA));
		e2.add(new BabuPozicio(20,Allapot.PÁLYA));
		e2.add(new BabuPozicio(5,Allapot.CÉL));
		kontroller.getJátékosok().get(2).setBábuk(e);

		jatek.setUtolsóGurítás(5);
		kontroller.Lépés(j,s.get(0));
		assertEquals(s.get(0),new BabuPozicio(0,Allapot.BÁZIS));
		
		jatek.setUtolsóGurítás(5);
		kontroller.Lépés(j,s.get(1));
		assertEquals(s.get(1),new BabuPozicio(10,Allapot.PÁLYA));
		assertEquals(e.get(1),new BabuPozicio(10,Allapot.PÁLYA));
		assertEquals(e2.get(1),new BabuPozicio(10,Allapot.PÁLYA));
		
		jatek.setUtolsóGurítás(3);
		kontroller.Lépés(j,s.get(2));
		assertEquals(s.get(2),new BabuPozicio(20,Allapot.PÁLYA));
		assertEquals(e2.get(2),new BabuPozicio(20,Allapot.PÁLYA));
		
		jatek.setUtolsóGurítás(1);
		kontroller.Lépés(j,s.get(2));
		assertEquals(s.get(2),new BabuPozicio(21,Allapot.PÁLYA));
		assertEquals(e.get(2),new BabuPozicio(0,Allapot.BÁZIS));

		
		
		
	}
}

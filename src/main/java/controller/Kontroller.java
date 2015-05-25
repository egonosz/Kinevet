package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import model.Jatekos;
import model.Kezdohely;
import model.Szin;
import model.Jatek;
import view.KFrame;

public class Kontroller {
	private KFrame frame;
	private Jatek jatek;
	private MenuKontroller menüKontroller;
	public Kontroller(KFrame frame, Jatek jatek) {
		super();
		
		this.menüKontroller= new MenuKontroller(this);
		
		this.frame = frame;
		this.frame.setKontroller(this);
		this.jatek = jatek;
		
		
	}
	
	public void játékLétrehozása(List<String> nevek){
		List<Szin> temp_színek=new ArrayList<Szin>(4);
		temp_színek.add(Szin.PIROS);
		temp_színek.add(Szin.ZÖLD);
		temp_színek.add(Szin.KÉK);
		temp_színek.add(Szin.FEKETE);
		List<Kezdohely> temp_kezdőhelyek=new ArrayList<Kezdohely>(4);
		temp_kezdőhelyek.add(Kezdohely.ÉSZAK);
		temp_kezdőhelyek.add(Kezdohely.KELET);
		temp_kezdőhelyek.add(Kezdohely.DÉL);
		temp_kezdőhelyek.add(Kezdohely.NYUGAT);
		Random rand = new Random();
		List<Jatekos> játékosok=new ArrayList<Jatekos>(nevek.size());
		for(int i=0;i<nevek.size();i++)
			{
			int rsz=rand.nextInt(4-i);
			int rk=rand.nextInt(4-i);
			játékosok.add(new Jatekos(temp_kezdőhelyek.get(rk),temp_színek.get(rsz), nevek.get(i)));
			temp_színek.remove(rsz);
			temp_kezdőhelyek.remove(rk);
			}
	 jatek.setJatekosok(játékosok);
	 jatek.setJátékosKöre(0);
	 frame.getStatusPanel().beállítás(játékosok);
	 frame.getTábla().kezdőHelyFrissítés(játékosok);
	 frame.repaint();
	
	}
	
	public MenuKontroller getMenüKontroller() {
		return menüKontroller;
	}



}

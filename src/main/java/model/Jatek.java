package model;

import java.util.ArrayList;
import java.util.List;

public class Jatek {
private List<Jatekos> játékosok;
private int játékosKöre;

public Jatek()
{
	játékosok=new ArrayList<Jatekos>(4);
	játékosKöre=new Integer(0);
}

public List<Jatekos> getJatekosok() 
{
	return játékosok;
}

public int getJátékosokSzáma()
{
	return játékosok.size();
}
public void setJatekosok(List<Jatekos> jatekosok) 
{
	this.játékosok = jatekosok;
}

public int getJátékosKöre() 
{
	return játékosKöre;
}

public void setJátékosKöre(int játékosKöre) 
{
	this.játékosKöre = játékosKöre;
}

}

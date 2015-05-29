/**
 * Az összes játékost, az utolsó gurítás eredmményét tárolja, továbbá, hogy mely játékos van soron.
 *
 * @author  Takác Ján
 * @version 1.0
 * @since   2015-05-25 
 */


package model;

import java.util.ArrayList;
import java.util.List;

public class Jatek {
private List<Jatekos> játékosok;
private int játékosKöre;
private int utolsóGurítás;
public Jatek()
{
	játékosok=new ArrayList<Jatekos>(4);
	játékosKöre=0;
	setUtolsóGurítás(0);
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

public int getUtolsóGurítás() {
	return utolsóGurítás;
}

public void setUtolsóGurítás(int utolsóGurítás) {
	this.utolsóGurítás = utolsóGurítás;
}

}

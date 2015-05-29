package view;

/**
 * A model beli Bábu képernyőn való elhelyezkedésének tárolására való osztály.
 *
 * @author  Takác Ján
 * @version 1.0
 * @since   2015-05-25 
 */


import java.awt.Color;

public class VBabu {
private Color szín;
private Pozicio pozíció;
private int játékosId;
private int darab;
public VBabu(Color szín, Pozicio pozíció,int játékosId) {
	super();
	this.szín = szín;
	this.pozíció = pozíció;
	this.játékosId=játékosId;
	this.setDarab(0);
}
public boolean equalPozíció(VBabu b){
	if (this.pozíció.equals(b.pozíció))
		return true;
	return false;
	
}
public boolean equalSzín(VBabu c){
	if (this.szín.equals(c.szín))
		return true;
	return false;
	
}

public Color getSzín() {
	return szín;
}
public void setSzín(Color szín) {
	this.szín = szín;
}
public Pozicio getPozíció() {
	return pozíció;
}
public void setPozíció(Pozicio pozíció) {
	this.pozíció = pozíció;
}
public int getJátékosId() {
	return játékosId;
}
public void setJátékosId(int játékosId) {
	this.játékosId = játékosId;
}

public int getDarab() {
	return darab;
}


public void setDarab(int darab) {
	this.darab = darab;
}


}

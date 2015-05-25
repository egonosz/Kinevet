package view;

import java.awt.Color;

public class VBabu {
private Color szín;
private Pozicio pozíció;
private int játékosId;

public VBabu(Color szín, Pozicio pozíció,int játékosId) {
	super();
	this.szín = szín;
	this.pozíció = pozíció;
	this.játékosId=játékosId;
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
	játékosId = játékosId;
}


}

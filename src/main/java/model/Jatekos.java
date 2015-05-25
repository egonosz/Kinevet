package model;

import java.util.Vector;

public final class Jatekos {
private Vector<Babu> bábuk;
private Szin szín;
private Kezdohely kezdőHely;
private String név;
private int id;
public Jatekos(Kezdohely kezdőHely, Szin szín,String név,int id) {
	super();
	this.kezdőHely = kezdőHely;
	this.szín = szín;
	this.név =név;
	this.id=id;
	this.bábuk= new Vector<Babu>(4);
	for(int i=0;i<4; i++) bábuk.addElement(new Babu());
}


public int getId() {
	return id;
}


public void setId(int id) {
	this.id = id;
}


public Vector<Babu> getBábuk() {
	return bábuk;
}

public void setBábuk(Vector<Babu> bábuk) {
	this.bábuk = bábuk;
}

public Szin getSzín() {
	return szín;
}

public void setSzín(Szin szín) {
	this.szín = szín;
}

public Kezdohely getKezdőHely() {
	return kezdőHely;
}

public void setKezdőHely(Kezdohely kezdőHely) {
	this.kezdőHely = kezdőHely;
}

public String getNév() {
	return név;
}

public void setNév(String név) {
	this.név = név;
}


}

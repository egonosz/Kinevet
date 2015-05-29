

package model;

/**
 * A játtékosok információit tárolja. Szín,bábuk helyzete, kezdőhely, név.
 *
 * @author  Takác Ján
 * @version 1.0
 * @since   2015-05-25 
 */


import java.util.Vector;

public final class Jatekos {
private Vector<BabuPozicio> bábuk;
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
	this.bábuk= new Vector<BabuPozicio>(4);
	for(int i=0;i<4; i++) bábuk.addElement(new BabuPozicio());
}


public int getId() {
	return id;
}


public void setId(int id) {
	this.id = id;
}


public Vector<BabuPozicio> getBábuk() {
	return bábuk;
}

public void setBábuk(Vector<BabuPozicio> bábuk) {
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

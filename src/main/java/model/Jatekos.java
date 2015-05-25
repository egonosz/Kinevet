package model;

public final class Jatekos {
private Babu[] bábuk;
private Szin szín;
private Kezdohely kezdőHely;
private String név;

public Jatekos(Kezdohely kezdőHely, Szin szín,String név) {
	super();
	this.kezdőHely = kezdőHely;
	this.szín = szín;
	this.név =név;
	this.bábuk= new Babu[4];
}
public Babu[] getBábuk() {
	return bábuk;
}

public void setBábuk(Babu[] bábuk) {
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

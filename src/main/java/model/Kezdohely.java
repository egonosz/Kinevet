package model;

public enum Kezdohely {
ÉSZAK(0),
KELET(10),
DÉL(20),
NYUGAT(30);

private final int kezdőPozíció;

Kezdohely(int kezdőPozíció){
	this.kezdőPozíció=kezdőPozíció;
}

public int getKezdőPozíció() {
	return this.kezdőPozíció;
}


}

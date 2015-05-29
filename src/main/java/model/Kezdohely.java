/**
 * Enum mely összekapcsolja az égtájakat a játékosok pályán lévő kezdőpozíciójával
 *
 * @author  Takác Ján
 * @version 1.0
 * @since   2015-05-25 
 */

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

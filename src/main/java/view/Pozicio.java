/**
 * 2D-s Pozíciókat tároló osztály.
 *
 * @author  Takác Ján
 * @version 1.0
 * @since   2015-05-25 
 */



package view;

import model.BabuPozicio;

public class Pozicio {

public int x,y;

Pozicio()
{
	x=new Integer(0);
	y=new Integer(0);
}
public Pozicio(int x, int y) {
	super();
	this.x = x;
	this.y = y;
}
@Override
public boolean equals(Object obj) {
	if (!(obj instanceof Pozicio))
		return false;
	if (obj == this)
		return true;

	Pozicio b = (Pozicio) obj;
	if ((this.x == b.x)
			&& (this.y == b.y)) {
		return true;
	}
	return false;

}
}

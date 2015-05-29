
package model;

/**
 * A bábuk pozícióját tárolja és az állaptotot.
 *
 * @author  Takác Ján
 * @version 1.0
 * @since   2015-05-25 
 */


public class BabuPozicio {

	private Integer pozíció; // Kezdő hely =-1
	private Allapot állapot;

	public BabuPozicio() {
		pozíció = new Integer(0);
		setÁllapot(Allapot.BÁZIS);
	}

	/*public BabuPozicio(Integer poz) {
		pozíció = new Integer(poz);
		setÁllapot(Allapot.BÁZIS);
	}*/

	public BabuPozicio(Allapot állapot) {
		super();
		setPozíció(0);
		this.állapot = állapot;
	}

	public BabuPozicio(Integer pozíció, Allapot állapot) {
		super();
		this.pozíció = pozíció;
		this.állapot = állapot;
	}

	public Integer getPozíció() {
		return pozíció;
	}

	public void setPozíció(Integer pozíció) {
		this.pozíció = pozíció;
	}

	public Allapot getÁllapot() {
		return állapot;
	}

	public void setÁllapot(Allapot állapot) {
		this.állapot = állapot;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof BabuPozicio))
			return false;
		if (obj == this)
			return true;

		BabuPozicio b = (BabuPozicio) obj;
		if ((this.getPozíció() == b.getPozíció())
				&& (this.getÁllapot() == b.getÁllapot())) {
			return true;
		}
		return false;

	}
}

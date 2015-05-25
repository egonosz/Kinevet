package model;

public final class Babu {
 
	private Integer pozíció; // Kezdő hely =-1
	
	Babu()
	{
		pozíció=new Integer(-1);
	}
	
	Babu(Integer poz)
	{
		pozíció = new Integer(poz);
	
	}

	public Integer getPozíció() {
		return pozíció;
	}

	public void setPozíció(Integer pozíció) {
		this.pozíció = pozíció;
	}
 
}
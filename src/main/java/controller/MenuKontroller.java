

package controller;

import java.util.List;

/**
 * A menü megjelenítővel való kommunikáció a feladata.
 *
 * @author  Takác Ján
 * @version 1.0
 * @since   2015-05-25 
 */


public class MenuKontroller {
	
	private Kontroller kontroller;
	
	public MenuKontroller(Kontroller kontroller) {
		super();
		this.kontroller = kontroller;
	}

	public void újJáték(List<String> names)
	{
		kontroller.játékLétrehozása(names);
	}
	
	public void kilépés()
	{
		System.exit(0);
		
	}
}

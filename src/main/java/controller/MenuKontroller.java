package controller;

import java.util.List;

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
		System.out.print("shit");
		System.exit(0);
		
	}
}

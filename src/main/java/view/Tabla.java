package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JPanel;

import controller.Kontroller;
import model.Babu;
import model.Jatekos;

@SuppressWarnings("serial")
public class Tabla extends JPanel {

	private static final Color HÁTTÉR_SZÍN = new Color(219, 194, 94);
	private static final Color VONAL_SZÍN = new Color(0, 0, 0);
	private static final Color MEZŐ_SZÍN = new Color(255, 255, 255);
	private static final Color PIROS_MEZŐ = new Color(110, 0, 0);
	private static final Color ZÖLD_MEZŐ = new Color(0, 110, 0);
	private static final Color KÉK_MEZŐ = new Color(0, 0, 110);
	private static final Color FEKETE_MEZŐ = new Color(90, 90, 90);
	
	private static final Color PIROS_BÁBU = new Color(200, 0, 0);
	private static final Color ZÖLD_BÁBU = new Color(0, 200, 0);
	private static final Color KÉK_BÁBU = new Color(0, 0, 200);
	private static final Color FEKETE_BÁBU = new Color(180, 180, 180);
	
	private static final Color SZÜRKE_MEZŐ = new Color(150,150,150);
	private static final int MEZŐ_ÁTLÓ = 26; // legyen páros

	private Vector<Pozicio> mezőPozíciók;
	private Map<Integer, Vector<Pozicio>> bázisMezőPozíciók;
	private Map<Integer, Vector<Pozicio>> győzelmiMezőPozíciók;
	private Map<Integer, Color> kontrollMezőSzínek;
	private List<VBabu> bábuPozíciók;
	private boolean isAktív;
    private Kontroller kontroller;
	Tabla() {
		super();
		this.setBackground(HÁTTÉR_SZÍN);
		this.isAktív = false;
		this.setSize(442, 442);
		bábuPozíciók=new ArrayList<VBabu>(16);
		
		mezőPozíciókInicializálása();
		győzelmiMezőPozíciókInicializálása();
		bázisPozíciókInicializálása();
		kontrollMezőSzínekInicializálás();
		setMouseListener();
	}

	public void setKontroller(Kontroller kontroller)
	{
		this.kontroller=kontroller;
	}
	public void setMouseListener()
	{
		  this.addMouseListener(new MouseAdapter() {
              private Color background;

              @Override
              public void mousePressed(MouseEvent e) {
                  if(isAktív)
                  {
                	  List<Jatekos> játékosok=kontroller.getJátékosok();
                	  for(Jatekos j: játékosok)
                	  {
                		  if(kontroller.getAktívJátékos()==j.getId()){
                		  Vector<Babu> babuk=j.getBábuk();
                			 for(Babu b: babuk){
                			  if(b.getPozíció()!=-1){
                				if(tavolsag(bábuToPozíció(b),new Pozicio(e.getX(),e.getY()))<MEZŐ_ÁTLÓ)
                					  {
                				  		kontroller.Lépés(j,b);
                				  		return;
                					  }
                			 }
                			  else {
                				  
                				for(Pozicio ba :bázisMezőPozíciók.get(j.getKezdőHely().getKezdőPozíció()))
                				  if(tavolsag( ba,new Pozicio(e.getX(),e.getY()))<2*MEZŐ_ÁTLÓ)
                				  {
                					  System.out.println("Shit");
                					  kontroller.Lépés(j,b);
                					  return;
                				  }
                			  }
                		}
                		  }
                  }
                }}

              @Override
              public void mouseReleased(MouseEvent e) {
                  setBackground(background);
              }
          });
	}
	
	public void setAktív(boolean b) {
		isAktív=true;
		
	}
	
	public void setTáblaAKezdőállapotba(List<Jatekos> Jatekosok)
	{
		kontrollMezőSzínek.clear();
		for(Jatekos a:Jatekosok)
		{
		Color c= new Color(0,0,0);
		switch(a.getSzín())
		{
		case PIROS:
				c=PIROS_MEZŐ;
			
		break;
		case ZÖLD:
				c=ZÖLD_MEZŐ;
				
		break;
		case KÉK:
				c=KÉK_MEZŐ;
				
		break;
		case FEKETE:
				c=FEKETE_MEZŐ;
				
		}
		kontrollMezőSzínek.put(a.getKezdőHely().getKezdőPozíció(),c);
		
	
		}
		for(int i=0;i<4;i++)
		{
			if(!kontrollMezőSzínek.containsKey(i*10))
			{
				kontrollMezőSzínek.put(i*10, new Color(SZÜRKE_MEZŐ.getRGB()));
			}
		}
		
		bábukFrissítése(Jatekosok);
		
		
	}
	private int tavolsag(Pozicio x,Pozicio y)
	{
		return (int)Math.sqrt(Math.abs((x.x-y.x)*(x.x-y.x)+(x.y-y.y)*(x.y-y.y)));
	}
	private Pozicio  bábuToPozíció(Babu b)
	{
		Pozicio p=new Pozicio();
		if(b.getPozíció()!=-1){
		p=mezőPozíciók.get(b.getPozíció());
		}
		return p;
	}
	public void bábukFrissítése(List<Jatekos> jatekosok)
	{
		bábuPozíciók.clear();

		for(Jatekos a:jatekosok)
		{
		Color c= new Color(0,0,0);
		switch(a.getSzín())
		{
		case PIROS:
				c=PIROS_BÁBU;
		break;
		case ZÖLD:
				c=ZÖLD_BÁBU;
		break;
		case KÉK:
				c=KÉK_BÁBU;
		break;
		case FEKETE:
				c=FEKETE_BÁBU;
		}
		
		/*Ez teszi a megfelelő helyre a bábut*/
		for(int i=0;i<4;i++){
			if(a.getBábuk().get(i).getPozíció()==-1){
			bábuPozíciók.add(new VBabu(c ,bázisMezőPozíciók.get(a.getKezdőHely().getKezdőPozíció()).get(i),a.getId()));
			}
			else if(a.getBábuk().get(i).getPozíció()<40)
			{
				bábuPozíciók.add(new VBabu(c ,mezőPozíciók.get(a.getBábuk().get(i).getPozíció()),a.getId()));
			}
		}
		
		}
	}
	
	
	private void kontrollMezőSzínekInicializálás() {
		kontrollMezőSzínek = new HashMap<Integer, Color>();
		kontrollMezőSzínek.put(0, new Color(PIROS_MEZŐ.getRGB()));
		kontrollMezőSzínek.put(10, new Color(ZÖLD_MEZŐ.getRGB()));
		kontrollMezőSzínek.put(20, new Color(KÉK_MEZŐ.getRGB()));
		kontrollMezőSzínek.put(30, new Color(FEKETE_MEZŐ.getRGB()));
	}

	private void mezőPozíciókInicializálása() {

		mezőPozíciók = new Vector<Pozicio>(40);
		Pozicio p = new Pozicio(MEZŐ_ÁTLÓ * 7 + (MEZŐ_ÁTLÓ * 6) / 2, MEZŐ_ÁTLÓ);
		mezőPozíciók.add(new Pozicio(p.x, p.y));
		int ir = 0;// az irány iterátora
		final Pozicio[] op = { new Pozicio(0, 1), new Pozicio(-1, 0),
				new Pozicio(0, -1), new Pozicio(1, 0) }; // irányok mere kell
															// mennie
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 3; j++) {
				for (int k = 0; k < (j == 2 ? 2 : 4); k++) {
					p.x = (p.x + (op[ir].x * (MEZŐ_ÁTLÓ + MEZŐ_ÁTLÓ / 2)));
					p.y = (p.y + (op[ir].y * (MEZŐ_ÁTLÓ + MEZŐ_ÁTLÓ / 2)));
					mezőPozíciók.add(new Pozicio(p.x, p.y));
				}
				if (j == 0)
					ir = ((ir == 0) ? ir = 3 : ir - 1);
				else
					ir = ((ir == 3) ? ir = 0 : ir + 1);
			}
		}
	}

	private void bázisPozíciókInicializálása()// !!!VIGYÁZZ először a
												// mezőpozíciók
	{
		bázisMezőPozíciók = new HashMap<Integer, Vector<Pozicio>>();

		for (int i = 0; i < 4; i++) {

			int bx = (mezőPozíciók.get(i * 10 + 8).x - mezőPozíciók.get(i * 10).x)
					/ 2 + mezőPozíciók.get(i * 10).x;
			int by = (mezőPozíciók.get(i * 10 + 8).y - mezőPozíciók.get(i * 10).y)
					/ 2 + mezőPozíciók.get(i * 10).y;
			Vector<Pozicio> v = new Vector<Pozicio>(4);
			v.add(new Pozicio(bx - (MEZŐ_ÁTLÓ * 3) / 4, by - (MEZŐ_ÁTLÓ * 3)
					/ 4));
			v.add(new Pozicio(bx + (MEZŐ_ÁTLÓ * 3) / 4, by - (MEZŐ_ÁTLÓ * 3)
					/ 4));
			v.add(new Pozicio(bx - (MEZŐ_ÁTLÓ * 3) / 4, by + (MEZŐ_ÁTLÓ * 3)
					/ 4));
			v.add(new Pozicio(bx + (MEZŐ_ÁTLÓ * 3) / 4, by + (MEZŐ_ÁTLÓ * 3)
					/ 4));
			bázisMezőPozíciók.put(i * 10, new Vector<Pozicio>(v));
		}

	}

	private void győzelmiMezőPozíciókInicializálása() {
		győzelmiMezőPozíciók = new HashMap<Integer, Vector<Pozicio>>();
		final Pozicio[] op = { new Pozicio(0, 1), new Pozicio(-1, 0),
				new Pozicio(0, -1), new Pozicio(1, 0) };
		for (int i = 0; i < 4; i++) {
			int gx = mezőPozíciók.get(0 == i ? 39 : i * 10 - 1).x;
			int gy = mezőPozíciók.get(0 == i ? 39 : i * 10 - 1).y;
			Vector<Pozicio> v = new Vector<Pozicio>(4);
			for (int j = 0; j < 4; j++) {
				gx += (MEZŐ_ÁTLÓ + MEZŐ_ÁTLÓ / 2) * op[i].x;
				gy += (MEZŐ_ÁTLÓ + MEZŐ_ÁTLÓ / 2) * op[i].y;
				v.add(new Pozicio(gx, gy));
			}
			győzelmiMezőPozíciók.put(i * 10, new Vector<Pozicio>(v));
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		táblaKirajzolás(g2D);
	}

	private void táblaKirajzolás(Graphics2D g) {

		g.setPaint(HÁTTÉR_SZÍN);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setPaint(VONAL_SZÍN);
		g.setStroke(new BasicStroke(2));

		Pozicio p = new Pozicio(0, 0);
		boolean első = true;
		for (Pozicio a : mezőPozíciók) {
			if (!első) {
				g.drawLine(p.x, p.y, a.x, a.y);
			}

			p = a;
			első = false;
		}

		for (Pozicio a : mezőPozíciók) {
			g.setPaint(MEZŐ_SZÍN);
			g.fillOval(a.x - MEZŐ_ÁTLÓ / 2, a.y - MEZŐ_ÁTLÓ / 2, MEZŐ_ÁTLÓ,
					MEZŐ_ÁTLÓ);
			g.setPaint(VONAL_SZÍN);
			g.drawOval(a.x - MEZŐ_ÁTLÓ / 2, a.y - MEZŐ_ÁTLÓ / 2, MEZŐ_ÁTLÓ,
					MEZŐ_ÁTLÓ);
		}
		for(int i=0;i<4;i++)
		{
			g.setPaint(kontrollMezőSzínek.get(i*10));
			Pozicio a=mezőPozíciók.get(i*10);
			g.fillOval(a.x - MEZŐ_ÁTLÓ / 2, a.y - MEZŐ_ÁTLÓ / 2, MEZŐ_ÁTLÓ,
					MEZŐ_ÁTLÓ);
			g.setPaint(VONAL_SZÍN);
			g.drawOval(a.x - MEZŐ_ÁTLÓ / 2, a.y - MEZŐ_ÁTLÓ / 2, MEZŐ_ÁTLÓ,
					MEZŐ_ÁTLÓ);
		}
		
		for (Map.Entry<Integer, Vector<Pozicio>> e : bázisMezőPozíciók
				.entrySet()) {
			Color c = new Color(kontrollMezőSzínek.get(e.getKey()).getRGB());

			for (Pozicio v : e.getValue()) {
				g.setPaint(c);
				g.fillOval(v.x - MEZŐ_ÁTLÓ / 2, v.y - MEZŐ_ÁTLÓ / 2, MEZŐ_ÁTLÓ,
						MEZŐ_ÁTLÓ);
				g.setPaint(VONAL_SZÍN);
				g.drawOval(v.x - MEZŐ_ÁTLÓ / 2, v.y - MEZŐ_ÁTLÓ / 2, MEZŐ_ÁTLÓ,
						MEZŐ_ÁTLÓ);
			}

		}
		for (Map.Entry<Integer, Vector<Pozicio>> e : győzelmiMezőPozíciók
				.entrySet()) {
			Color c = kontrollMezőSzínek.get(e.getKey());
			for (Pozicio v : e.getValue()) {
				int gym = MEZŐ_ÁTLÓ - MEZŐ_ÁTLÓ / 5;
				g.setPaint(c);
				g.fillOval(v.x - gym / 2, v.y - gym / 2, gym, gym);
				g.setPaint(VONAL_SZÍN);
				g.drawOval(v.x - gym / 2, v.y - gym / 2, gym, gym);
			}

		}

	
		for(VBabu b:bábuPozíciók)
		{
			g.setPaint(b.getSzín());
			g.setStroke(new BasicStroke(1));
			Pozicio pb=new Pozicio(b.getPozíció().x,b.getPozíció().y);
			g.fillOval(pb.x-((MEZŐ_ÁTLÓ/2)/10)*8,pb.y-((MEZŐ_ÁTLÓ/2)/10)*8,((MEZŐ_ÁTLÓ/2)/5)*8,((MEZŐ_ÁTLÓ/2)/5)*8);
			g.setPaint(VONAL_SZÍN);
			g.drawOval(pb.x-((MEZŐ_ÁTLÓ/2)/10)*8,pb.y-((MEZŐ_ÁTLÓ/2)/10)*8,((MEZŐ_ÁTLÓ/2)/5)*8,((MEZŐ_ÁTLÓ/2)/5)*8);
			g.setPaint(new Color((int)b.getSzín().getRed()+50,(int)b.getSzín().getGreen()+50,(int)b.getSzín().getBlue()+50));
			g.fillOval(pb.x-((MEZŐ_ÁTLÓ/2)/10)*5,pb.y-((MEZŐ_ÁTLÓ/2)/10)*5,((MEZŐ_ÁTLÓ/2)/5)*5,((MEZŐ_ÁTLÓ/2)/5)*5);
		}
	}

	
}

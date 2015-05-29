package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JPanel;

import controller.Kontroller;
import model.Allapot;
import model.BabuPozicio;
import model.Jatekos;

/**
 * A táblát rajzolja ki melyen folyik a és az inputról tájékoztatja a kontrollert.
 *
 * @author  Takác Ján
 * @version 1.0
 * @since   2015-05-25 
 */


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

	private static final Color SZÜRKE_MEZŐ = new Color(150, 150, 150);
	private static final int MEZŐ_ÁTLÓ = 26; // legyen páros

	private Vector<Pozicio> mezőPozíciók;
	private Map<Integer, Vector<Pozicio>> bázisMezőPozíciók;
	private Map<Integer, Vector<Pozicio>> célMezőPozíciók;
	private Map<Integer, Color> kontrollMezőSzínek;
	private Vector<Pozicio> léphetők;
	private Pozicio léphető;
	private List<VBabu> bábuPozíciók;
	private boolean isAktív;
	private Kontroller kontroller;

	Tabla() {
		super();
		this.setBackground(HÁTTÉR_SZÍN);
		this.isAktív = false;
		this.setSize(442, 442);
		bábuPozíciók = new ArrayList<VBabu>(16);

		léphetők = new Vector<Pozicio>();
		//léphető =new Pozicio();
		mezőPozíciókInicializálása();
		győzelmiMezőPozíciókInicializálása();
		bázisPozíciókInicializálása();
		kontrollMezőSzínekInicializálás();
		setMouseListener();
	}

	public void setKontroller(Kontroller kontroller) {
		this.kontroller = kontroller;
	}

	
	public BabuPozicio miFelettAzEger(int x,int y,Jatekos j)
	{
				if (kontroller.getAktívJátékosId() == j.getId()) {
				Vector<BabuPozicio> babuk = j.getBábuk();
				int k = j.getKezdőHely().getKezdőPozíció();
				for (BabuPozicio b : babuk) {
					int p = b.getPozíció();
					if (b.getÁllapot() == Allapot.BÁZIS) {

						for (Pozicio ba : bázisMezőPozíciók.get(k))
							if (tavolsag(ba, new Pozicio(x,
									y)) < 2 * MEZŐ_ÁTLÓ) {
								
								return b;
							}
					} else if (b.getÁllapot() == Allapot.CÉL) {
						for (int i = 0; i < 4; i++) {
							Pozicio ba = célMezőPozíciók.get(k)
									.get(p);
							if (tavolsag(ba, new Pozicio(x,
									y)) < MEZŐ_ÁTLÓ) {

								return b;
							}
						}
					} else {
						Pozicio ba = mezőPozíciók.get(b
								.getPozíció());
						if (tavolsag(ba,
								new Pozicio(x, y)) < MEZŐ_ÁTLÓ) {
	
							return b;
						}
					}
				}
			}
				return null;
	}	
	
	
	
	public void setMouseListener() {
		this.addMouseMotionListener(new MouseAdapter(){
			@Override
		    public void mouseMoved(MouseEvent e) {
				if (isAktív) {
				Jatekos j=kontroller.getAktívJátékos();
				BabuPozicio b=miFelettAzEger(e.getX(),e.getY(),j);
				if(b!=null)
				{
					BabuPozicio l= kontroller.léphetőPozíció(j,b);
					if(l!=null)
					{
						setLéphezőMező(l,j);
						repaint();
						return;
					}
					else
					{
						
					}
				}
				léphető=null;
				repaint();
				}
			}
			
		});
		
		this.addMouseListener(new MouseAdapter() {
		

			@Override
			public void mousePressed(MouseEvent e) {
				if (isAktív) {
					Jatekos j=kontroller.getAktívJátékos();
					BabuPozicio b=miFelettAzEger(e.getX(),e.getY(),j);
					if(b!=null)	
						kontroller.Lépés(j, b);
					 	léphető=null;
				}
			}

			
		});
	}

	public void setAktív(boolean b) {
		this.isAktív = b;

	}
	public void setLéphezőMező(BabuPozicio b, Jatekos j){
		int k = j.getKezdőHely().getKezdőPozíció();
			if (b.getÁllapot() == Allapot.CÉL) {
				léphető=célMezőPozíciók.get(k).get(b.getPozíció());
			} else {
				léphető=mezőPozíciók.get(b.getPozíció());
			}
		}
	
	public void setLéphetőMezők(List<BabuPozicio> bL, Jatekos j) {
		léphetők.clear();
		int k = j.getKezdőHely().getKezdőPozíció();
		for (BabuPozicio b : bL) {
			if (b.getÁllapot() == Allapot.CÉL) {
				léphetők.add(célMezőPozíciók.get(k).get(b.getPozíció()));
			} else {
				léphetők.add(mezőPozíciók.get(b.getPozíció()));
			}
		}
	}

	public void setTáblaAKezdőállapotba(List<Jatekos> Jatekosok) {
		kontrollMezőSzínek.clear();
		for (Jatekos a : Jatekosok) {
			Color c = new Color(0, 0, 0);
			switch (a.getSzín()) {
			case PIROS:
				c = PIROS_MEZŐ;

				break;
			case ZÖLD:
				c = ZÖLD_MEZŐ;

				break;
			case KÉK:
				c = KÉK_MEZŐ;

				break;
			case FEKETE:
				c = FEKETE_MEZŐ;

			}
			kontrollMezőSzínek.put(a.getKezdőHely().getKezdőPozíció(), c);

		}
		for (int i = 0; i < 4; i++) {
			if (!kontrollMezőSzínek.containsKey(i * 10)) {
				kontrollMezőSzínek.put(i * 10, new Color(SZÜRKE_MEZŐ.getRGB()));
			}
		}

		bábukFrissítése(Jatekosok);

	}

	private int tavolsag(Pozicio x, Pozicio y) {
		return (int) Math.sqrt(Math.abs((x.x - y.x) * (x.x - y.x) + (x.y - y.y)
				* (x.y - y.y)));
	}

	public void bábukFrissítése(List<Jatekos> jatekosok) {
		
		List<VBabu> újBp= new ArrayList<VBabu>(16);
		for (Jatekos a : jatekosok) {
			Color c = new Color(0, 0, 0);
			switch (a.getSzín()) {
			case PIROS:
				c = PIROS_BÁBU;
				break;
			case ZÖLD:
				c = ZÖLD_BÁBU;
				break;
			case KÉK:
				c = KÉK_BÁBU;
				break;
			case FEKETE:
				c = FEKETE_BÁBU;
			}

			/* Ez teszi a megfelelő helyre a bábut */
			int k = a.getKezdőHely().getKezdőPozíció();
			Vector<BabuPozicio> bV = a.getBábuk();
			VBabu b;
			for (int i = 0; i < 4; i++) {
				if (bV.get(i).getÁllapot() == Allapot.BÁZIS) {
					b=new VBabu(c, bázisMezőPozíciók.get(k).get(
							i), a.getId());
					} else if (bV.get(i).getÁllapot() == Allapot.CÉL) {
					b=new VBabu(c, célMezőPozíciók.get(k).get(
							bV.get(i).getPozíció()), a.getId());
				} else {
					b=new VBabu(c, mezőPozíciók.get(bV.get(i)
							.getPozíció()), a.getId());
				}
			
			for(VBabu b2:újBp)
				{
					if(b2.equalPozíció(b)&&b2.equalSzín(b)){
						b.setDarab(b.getDarab()+1);
						
					}
				}
				újBp.add(b);
			}
		}
		bábuPozíciók.clear();
		
		for(VBabu b:újBp)
		{
			Pozicio p=new Pozicio(b.getPozíció().x,b.getPozíció().y);
		WAY_1: for(VBabu b2:újBp)
			{
			
					
					if(b2.equalPozíció(b)&&!(b2.equalSzín(b)))
				{
					
					switch (b.getJátékosId()) {
					case 0:
						p.x=b.getPozíció().x -MEZŐ_ÁTLÓ/3;
						p.y=b.getPozíció().y -MEZŐ_ÁTLÓ/3;
						break WAY_1;
					case 1:
						p.x=b.getPozíció().x -MEZŐ_ÁTLÓ/3;
						p.y=b.getPozíció().y +MEZŐ_ÁTLÓ/3;
						break WAY_1;
					case 2:
						p.x=b.getPozíció().x +MEZŐ_ÁTLÓ/3;
						p.y=b.getPozíció().y -MEZŐ_ÁTLÓ/3;
						
						break WAY_1;
					default:
						p.x=b.getPozíció().x +MEZŐ_ÁTLÓ/3;
						p.y=b.getPozíció().y +MEZŐ_ÁTLÓ/3;
						break WAY_1;
					}
				}
			
				}
			
			p.x-=2*b.getDarab();
			p.y-=2*b.getDarab();
			bábuPozíciók.add(new VBabu(b.getSzín(),p,b.getJátékosId()));
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
		célMezőPozíciók = new HashMap<Integer, Vector<Pozicio>>();
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
			v.add(v.get(2));
			v.add(v.get(1));
			célMezőPozíciók.put(i * 10, new Vector<Pozicio>(v));

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
		for (int i = 0; i < 4; i++) {
			g.setPaint(kontrollMezőSzínek.get(i * 10));
			Pozicio a = mezőPozíciók.get(i * 10);
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
		for (Map.Entry<Integer, Vector<Pozicio>> e : célMezőPozíciók.entrySet()) {
			Color c = kontrollMezőSzínek.get(e.getKey());
			for (Pozicio v : e.getValue()) {
				int gym = MEZŐ_ÁTLÓ - MEZŐ_ÁTLÓ / 5;
				g.setPaint(c);
				g.fillOval(v.x - gym / 2, v.y - gym / 2, gym, gym);
				g.setPaint(VONAL_SZÍN);
				g.drawOval(v.x - gym / 2, v.y - gym / 2, gym, gym);
			}

		}

		if(léphető!=null)
		{
			g.setStroke(new BasicStroke(3));
			g.setPaint(Color.yellow);
			g.drawOval(léphető.x - MEZŐ_ÁTLÓ / 2, léphető.y - MEZŐ_ÁTLÓ / 2, MEZŐ_ÁTLÓ,
					MEZŐ_ÁTLÓ);
		}
	
		for (VBabu b : bábuPozíciók) {
			g.setPaint(b.getSzín());
			g.setStroke(new BasicStroke(1));
			Pozicio pb = new Pozicio(b.getPozíció().x, b.getPozíció().y);
			g.fillOval(pb.x - ((MEZŐ_ÁTLÓ / 2) / 10) * 8, pb.y
					- ((MEZŐ_ÁTLÓ / 2) / 10) * 8, ((MEZŐ_ÁTLÓ / 2) / 5) * 8,
					((MEZŐ_ÁTLÓ / 2) / 5) * 8);
			g.setPaint(VONAL_SZÍN);
			g.drawOval(pb.x - ((MEZŐ_ÁTLÓ / 2) / 10) * 8, pb.y
					- ((MEZŐ_ÁTLÓ / 2) / 10) * 8, ((MEZŐ_ÁTLÓ / 2) / 5) * 8,
					((MEZŐ_ÁTLÓ / 2) / 5) * 8);
			g.setPaint(new Color((int) b.getSzín().getRed() + 50, (int) b
					.getSzín().getGreen() + 50,
					(int) b.getSzín().getBlue() + 50));
			g.fillOval(pb.x - ((MEZŐ_ÁTLÓ / 2) / 10) * 5, pb.y
					- ((MEZŐ_ÁTLÓ / 2) / 10) * 5, ((MEZŐ_ÁTLÓ / 2) / 5) * 5,
					((MEZŐ_ÁTLÓ / 2) / 5) * 5);
		}
	
		/*if (!léphetők.isEmpty()) {
			
			g.setStroke(new BasicStroke(3));
			for (Pozicio l : léphetők) {
				g.drawOval(l.x - MEZŐ_ÁTLÓ / 2, l.y - MEZŐ_ÁTLÓ / 2, MEZŐ_ÁTLÓ,
						MEZŐ_ÁTLÓ);
			}
		}*/
	}

}

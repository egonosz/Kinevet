/**
 * Játékos nevét tartalmazó JLabel.
 *
 * @author  Takác Ján
 * @version 1.0
 * @since   2015-05-25 
 */



package view;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class JatekosLabel extends JLabel {
	int játékosId;
	public JatekosLabel(String text) {
		super(text);
	}
	public int getJátékosId() {
		return játékosId;
	}
	public void setJátékosId(int játékosId) {
		this.játékosId = játékosId;
	}
	
}

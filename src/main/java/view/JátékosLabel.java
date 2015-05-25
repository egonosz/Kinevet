package view;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class JátékosLabel extends JLabel {
	int játékosId;
	public JátékosLabel(String text) {
		super(text);
	}
	public int getJátékosId() {
		return játékosId;
	}
	public void setJátékosId(int játékosId) {
		this.játékosId = játékosId;
	}
	
}

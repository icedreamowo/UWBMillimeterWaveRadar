package gui;

import javax.swing.JOptionPane;

public class Dialog extends JOptionPane{
	private JOptionPane dialog=new JOptionPane();
	private String message = null;
	public void setMessage(String message) {
		this.message=message;
	}
	public void hideDialog() {
		this.setVisible(false);
	}
	public void showDialog() {
		this.setVisible(true);
	}

}

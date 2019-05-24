package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JLabel;

public class MyLabel extends JLabel{
	private int width,height;   //上椭圆外接矩形的宽度和高度
	public MyLabel(int width,int height) {
		this.width=width;
		this.height=height;
	}
	public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.white);
        g2d.fillRect(250-width/2, 100, width, 300);
        g2d.setColor(new Color(203,200,194));
        g2d.fillOval(250-width/2, 100-height/2, width, height);
        g2d.setColor(Color.white);
        g2d.fillOval(250-width/2, 400-height/2, width, height);
    }
	public void setWidthandHeight(int width,int height) {
		this.width=width;
		this.height=height;
	}
}

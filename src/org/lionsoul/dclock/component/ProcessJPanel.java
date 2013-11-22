package org.lionsoul.dclock.component;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JPanel;

import org.lionsoul.dclock.util.IConstants;


/**
 * process jpanel.
 * 
 * @author chenxin <chenxin619315@gmail.com>
 */
public class ProcessJPanel extends JPanel {

	private static final long serialVersionUID = 830696990628735404L;
	
	//private String text = "Wait seconds, Processing...";
	public static final String __PROCESS_TIP = "正在处理，请稍候...";
	public static final String __DBINITERROR_TIP = "数据库尚未初始化.";
	private String text = __PROCESS_TIP;
	
	public ProcessJPanel() {
		setPreferredSize(IConstants.ADD_W_SIZE);
		setSize(IConstants.ADD_W_SIZE);
	}
	
	@Override
	public void update( Graphics g ) {
		paintComponent(g);
	}
	
	@Override
	protected void paintComponent( Graphics g ) {
		Color bg = new Color(0, 0, 0, 60);
		g.setColor(bg);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		g.setColor(Color.WHITE);
		Font f = new Font("Arial", Font.BOLD, 16);
		g.setFont(f);
		//get string info under the specified font
		FontMetrics fm = getFontMetrics(f);
		g.drawString(text,
				(this.getWidth() - fm.stringWidth(text)) / 2,
				this.getHeight() / 2);
	}
	
	/**
	 * update the text on the JPanel.
	 * 		- this need to repaint the JPanel.
	 * 
	 * @param text
	 */
	public void updateText( String text ) {
		this.text = text;
	}

}

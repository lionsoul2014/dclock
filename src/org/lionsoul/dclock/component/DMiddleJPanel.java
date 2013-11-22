package org.lionsoul.dclock.component;

import java.awt.Graphics;

import javax.swing.JPanel;

import org.lionsoul.dclock.util.CreateIcon;



/**
 * middle content pane.
 * 
 * @author chenxin <chenxin619315@gmail.com>
 */
public class DMiddleJPanel extends JPanel {
	
	private static final long serialVersionUID = -2074751113522738157L;
	
	public DMiddleJPanel() {
		//pane.setScrollSize(DClock.getInstance().getSize());
		add(JListScrollPane.getInstance());
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		//draw the bg
		g.drawImage(CreateIcon.createIcon("middle-icon.png").getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
		
		//draw content bg
		g.drawImage(CreateIcon.createIcon("c-bg.png").getImage(), 5, 0, this.getWidth() - 10, this.getHeight(), null);
	}

}

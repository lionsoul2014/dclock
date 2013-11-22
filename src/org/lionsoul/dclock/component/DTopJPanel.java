package org.lionsoul.dclock.component;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.lionsoul.dclock.DClock;
import org.lionsoul.dclock.util.CreateIcon;
import org.lionsoul.dclock.util.IConstants;


/**
 * top pane.
 * 
 * @author chenxin <chenxin619315@gmail.com>
 */
public class DTopJPanel extends JPanel {

	private static final long serialVersionUID = -2970403138510052658L;
	
	public DTopJPanel() {
		this.setPreferredSize(IConstants.W_TOP_SIZE);
		this.setSize(IConstants.W_TOP_SIZE);
		this.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
		
		JButton min = new JButton();
		min.setSize(IConstants.MIN_BUTTON_SIZE);
		min.setPreferredSize(IConstants.MIN_BUTTON_SIZE);
		min.setBorderPainted(false);
		min.setFocusPainted(false);
		min.setContentAreaFilled(false);
		min.setIcon(CreateIcon.createIcon("min.png"));
		min.setRolloverIcon(CreateIcon.createIcon("min-hover.png"));
		min.setPressedIcon(CreateIcon.createIcon("min-hover.png"));
		min.setToolTipText("最小化");
		min.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DClock.tray();
			}
		});
		add(min);
		
		JButton max = new JButton();
		max.setSize(IConstants.MAX_BUTTON_SIZE);
		max.setPreferredSize(IConstants.MAX_BUTTON_SIZE);
		max.setBorderPainted(false);
		max.setFocusPainted(false);
		max.setContentAreaFilled(false);
		max.setIcon(CreateIcon.createIcon("max.png"));
		max.setRolloverIcon(CreateIcon.createIcon("max-hover.png"));
		max.setPressedIcon(CreateIcon.createIcon("max-hover.png"));
		max.setToolTipText("最大化/恢复");
		max.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent e) {
				if ( DClock.getInstance().getSize().width < DClock.SCREEN_SIZE.width )
					DClock.getInstance().setFramesize(DClock.SCREEN_SIZE);
				else 
					DClock.getInstance().setFramesize(IConstants.W_SIZE);
				//DClock.getInstance().setExtendedState(JFrame.MAXIMIZED_BOTH);
			}
		});
		add(max);
		
		JButton close = new JButton();
		close.setSize(IConstants.CLOSE_BUTTON_SIZE);
		close.setPreferredSize(IConstants.CLOSE_BUTTON_SIZE);
		close.setBorderPainted(false);
		close.setFocusPainted(false);
		close.setContentAreaFilled(false);
		close.setIcon(CreateIcon.createIcon("close.png"));
		close.setRolloverIcon(CreateIcon.createIcon("close-hover.png"));
		close.setPressedIcon(CreateIcon.createIcon("close-hover.png"));
		close.setToolTipText("关闭");
		close.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		add(close);
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(CreateIcon.createIcon("top-icon-2.png").getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
		
		g.setColor(Color.WHITE);
		g.setFont(IConstants.TITLE_FONT);
		g.drawString(IConstants.W_TITLE, 5, 16);
		
		ImageIcon AC_TAB = CreateIcon.createIcon("ac-tab.png");
		
		g.drawImage(AC_TAB.getImage(), 5,
				this.getHeight() - AC_TAB.getIconHeight(),
				AC_TAB.getIconWidth(), AC_TAB.getIconHeight(), null);
	}

}

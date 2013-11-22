package org.lionsoul.dclock.component;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.lionsoul.dclock.util.CreateIcon;
import org.lionsoul.dclock.util.IConstants;


/**
 * the top JPanel class. 
 * 
 * @author chenxin <chenxin619315@gmail.com>
 */
public class AddTopJPanel extends JPanel {

	private static final long serialVersionUID = -5518560885991326404L;
	
	private Point MDown = null;
	private AddJDialog dialog = null;
	
	public AddTopJPanel( AddJDialog adialog ) {
		dialog = adialog;
		setSize(IConstants.ADD_WTOP_SIZE);
		setPreferredSize(IConstants.ADD_WTOP_SIZE);
		
		setLayout(new FlowLayout( FlowLayout.RIGHT, 0, 0 ));	
		
		JButton min = new JButton();
		min.setPreferredSize(IConstants.MIN_BUTTON_SIZE);
		min.setSize(IConstants.MIN_BUTTON_SIZE);
		min.setBorderPainted(false);
		min.setFocusPainted(false);
		min.setContentAreaFilled(false);
		min.setIcon(CreateIcon.createIcon("min.png"));
		min.setRolloverIcon(CreateIcon.createIcon("min-hover.png"));
		min.setPressedIcon(CreateIcon.createIcon("min-hover.png"));
		min.setToolTipText("隐藏");
		min.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.hiddenDialog();
			}
		});
		add(min);
		
		JButton close = new JButton();
		close.setPreferredSize(IConstants.CLOSE_BUTTON_SIZE);
		close.setSize(IConstants.CLOSE_BUTTON_SIZE);
		close.setBorderPainted(false);
		close.setFocusPainted(false);
		close.setContentAreaFilled(false);
		close.setIcon(CreateIcon.createIcon("close.png"));
		close.setRolloverIcon(CreateIcon.createIcon("close-hover.png"));
		close.setPressedIcon(CreateIcon.createIcon("close-hover.png"));
		close.setToolTipText("取消");
		close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.hiddenDialog();
			}
		});
		add(close);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				MDown = e.getPoint();
			}
		});
		
		addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) {
				if ( MDown == null ) return; 
				Point p = e.getLocationOnScreen();
				dialog.setPosition(p.x - MDown.x, p.y - MDown.y);
			}

			@Override
			public void mouseMoved(MouseEvent e) {}
		});
	}
	
	
	protected void paintComponent( Graphics g ) {
		g.drawImage(CreateIcon.createIcon("win-top.png").getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
		
		g.setColor(Color.WHITE);
		g.setFont(IConstants.ADD_WTITLE_FONT);
		g.drawString(IConstants.ADD_W_TITLE, 10, 20);
	}
	
	
}

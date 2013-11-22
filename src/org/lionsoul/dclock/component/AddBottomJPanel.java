package org.lionsoul.dclock.component;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.lionsoul.dclock.util.CreateIcon;
import org.lionsoul.dclock.util.IConstants;


/**
 * bottom jpanel for the item add JPanel
 * 
 * @author chenxin <chenxin619315@gmail.com>
 */
public class AddBottomJPanel extends JPanel {
	
	private static final long serialVersionUID = -343708684902819213L;
	
	private AddJDialog dialog = null;

	public AddBottomJPanel( AddJDialog adialog ) {
		dialog = adialog;
		setPreferredSize(IConstants.ADD_WBOTTOM_SIZE);
		setSize(IConstants.ADD_WBOTTOM_SIZE);
		
		setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		
		JButton sure = new JButton();
		sure.setBorderPainted(false);
		sure.setFocusPainted(false);
		sure.setContentAreaFilled(false);
		sure.setIcon(CreateIcon.createIcon("sure.png"));
		sure.setRolloverIcon(CreateIcon.createIcon("sure-hover.png"));
		sure.setPressedIcon(CreateIcon.createIcon("sure-hover.png"));
		sure.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.showProcessJPanel();
				if ( dialog.mode == AddJDialog.ADD_MODE )
					dialog.getMiddleJPanel().addItem();
				else if ( dialog.mode == AddJDialog.EDIT_MODE )
					dialog.getMiddleJPanel().editItem();
			}
		});
		add(sure);
		
		JButton cancle = new JButton();
		cancle.setBorderPainted(false);
		cancle.setFocusPainted(false);
		cancle.setContentAreaFilled(false);
		cancle.setIcon(CreateIcon.createIcon("cancle.png"));
		cancle.setRolloverIcon(CreateIcon.createIcon("cancle-hover.png"));
		cancle.setPressedIcon(CreateIcon.createIcon("cancle-hover.png"));
		cancle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.hiddenDialog();
			}
		});
		add(cancle);
	}
	
	@Override
	protected void paintComponent( Graphics g ) {
		
		g.setColor(new Color(111, 168, 218));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		g.setColor(new Color(235, 235, 235));
		g.fillRect(2, 0, this.getWidth() - 4, this.getHeight() - 4);
	}

}

package org.lionsoul.dclock.component;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.lionsoul.dclock.DClock;
import org.lionsoul.dclock.model.ActionItem;
import org.lionsoul.dclock.model.Database;
import org.lionsoul.dclock.util.CreateIcon;
import org.lionsoul.dclock.util.IConstants;
import org.lionsoul.dclock.util.Util;



/**
 * bottom pane.
 * 
 * @author chenxin <chenxin619315@gmail.com>
 */
public class DBottomJPanel extends JPanel {

	private static final long serialVersionUID = -800809906855996805L;
	public static final Dimension _SIZE = new Dimension(20, 19);
	
	public DBottomJPanel() {
		this.setPreferredSize(IConstants.W_BOTTOM_SIZE);
		this.setSize(IConstants.W_BOTTOM_SIZE);
		this.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
		
		JButton add = new JButton();
		add.setSize(_SIZE);
		add.setPreferredSize(_SIZE);
		add.setBorderPainted(false);
		add.setFocusPainted(false);
		add.setContentAreaFilled(false);
		add.setIcon(CreateIcon.createIcon("add.png"));
		add.setRolloverIcon(CreateIcon.createIcon("add-hover.png"));
		add.setPressedIcon(CreateIcon.createIcon("add-hover.png"));
		add.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent e) {
				new AddJDialog(AddJDialog.ADD_MODE).showDialog();
			}
		});
		add(add);
		
		JButton modify = new JButton();
		modify.setSize(_SIZE);
		modify.setPreferredSize(_SIZE);
		modify.setBorderPainted(false);
		modify.setFocusPainted(false);
		modify.setContentAreaFilled(false);
		modify.setIcon(CreateIcon.createIcon("modify.png"));
		modify.setRolloverIcon(CreateIcon.createIcon("modify-hover.png"));
		modify.setPressedIcon(CreateIcon.createIcon("modify-hover.png"));
		modify.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object[] o = JListScrollPane.getInstance().getScrollJList().getSelectedValues();
				if ( o.length == 0 ) {
					JOptionPane.showMessageDialog(null, "Please selected the item to modify!",
							"Modify Item: ", JOptionPane.WARNING_MESSAGE);
					return;
				} 
				if ( o.length > 1 ) {
					JOptionPane.showMessageDialog(null, "One modify for once!",
							"Modify Item: ", JOptionPane.WARNING_MESSAGE);
					return;
				}
				new AddJDialog(AddJDialog.EDIT_MODE).showDialog();
			}
		});
		add(modify);
			
		JButton remove = new JButton();
		remove.setSize(_SIZE);
		remove.setPreferredSize(_SIZE);
		remove.setBorderPainted(false);
		remove.setFocusPainted(false);
		remove.setContentAreaFilled(false);
		remove.setIcon(CreateIcon.createIcon("remove.png"));
		remove.setRolloverIcon(CreateIcon.createIcon("remove-hover.png"));
		remove.setPressedIcon(CreateIcon.createIcon("remove-hover.png"));
		remove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						Object[] items = 
							JListScrollPane.getInstance().getScrollJList().getSelectedValues();
						if ( items.length == 0 ) {
							JOptionPane.showMessageDialog(null,
									"Please select the items you want to delete!",
									"Item Delete: ", JOptionPane.WARNING_MESSAGE);
							return;
						} 
						int res = JOptionPane.showConfirmDialog(null, "Are you sure to delete the selected items?",
								"Item Delete: ", JOptionPane.WARNING_MESSAGE);
						if ( res != 0 ) return;
						
						//remove
						for ( int j = items.length - 1; j >= 0; j-- ) {
							ActionItem item = (ActionItem) items[j];
							JListModel.getInstance().removeItem(item.getIndex());
							Database.getDatabase().remove(item);
							if ( item == JListModel.getInstance().getNextItem() ) {
								JListModel.getInstance().setNextItem(null);
							} 
						}
						
						try {
							Database.getDatabase().saveDatabaseToFile(DClock.__DB_FILE);
						} catch (IOException e) {
							JOptionPane.showMessageDialog(null, "Fail to save the database!",
									"Item Delete: ", JOptionPane.ERROR_MESSAGE);
						}
						
						/**
						 * check and update the next item in the list model. 
						 */
						if ( JListModel.getInstance().getNextItem() == null ) 
							JListModel.getInstance().setNextItem(Util.findNextItem());
					}
				}).start();
			}
		});
		add(remove);	
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(CreateIcon.createIcon("bottom-icon.png").getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
	}

}

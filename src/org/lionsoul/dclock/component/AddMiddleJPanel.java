package org.lionsoul.dclock.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import org.lionsoul.dclock.DClock;
import org.lionsoul.dclock.model.ActionItem;
import org.lionsoul.dclock.model.Database;
import org.lionsoul.dclock.util.IConstants;
import org.lionsoul.dclock.util.Util;

//import com.webssky.dclock.util.TextInputDocument;

/**
 * middle JPanel for the item add frame.
 * 
 * @author chenxin <chenxin619315@gmail.com>
 */
public class AddMiddleJPanel extends JPanel {

	private static final long serialVersionUID = -5903197982372833200L;
	
	public static Dimension LABEL_SIZE = new Dimension(80, 24);
	
	private JFormattedTextField dateText = null;
	private JFormattedTextField timeText = null;
	private JTextArea descField = null;
	private JRadioButton jrb[] = null;
	private JRadioButton jsb[] = null;
	
	private AddJDialog dialog = null;
	private ActionItem item = null;
	
	public AddMiddleJPanel( AddJDialog adialog ) {
		
		dialog = adialog;
		
		//edit
		if ( dialog.mode == AddJDialog.EDIT_MODE ) {
			item = ( ActionItem ) JListScrollPane.
						getInstance().getScrollJList().getSelectedValue();
		}
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints cons = new GridBagConstraints();
		cons.anchor = GridBagConstraints.NORTHWEST;
		
		
		//layer 1
		build(cons, 0, 0, 1, 1, 0, 0);
		JLabel dateLabel = new JLabel("开始日期：", SwingConstants.CENTER);
		dateLabel.setPreferredSize(LABEL_SIZE);
		dateLabel.setSize(LABEL_SIZE);
		dateLabel.setForeground(IConstants.ADD_LABEL_COLOR);
		dateLabel.setFont(IConstants.ADD_LABEL_FONT);
		add(dateLabel, cons);
		
		build(cons, 1, 0, 1, 1, 0, 0);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateText = new JFormattedTextField(dateFormat);
		if ( item == null ) 
			dateText.setValue(new Date());
		else {
			try {
				Date date = dateFormat.parse(item.getDate());
				dateText.setValue(date);
			} catch (ParseException e) {
				dateText.setValue(new Date());
			}
		}
		dateText.setPreferredSize(new Dimension(150, 25));
		dateText.setFont(IConstants.ADD_LABEL_FONT);
		dateText.setForeground(IConstants.ADD_LABEL_COLOR);
		dateText.setBorder(IConstants.ADD_TEXT_BORDER);
		add(dateText, cons);
		
		//layer 2
		build(cons, 0, 1, 1, 1, 0, 0);
		cons.insets = new Insets(8, 0, 8, 0);
		JLabel timeLabel = new JLabel("响铃时间：", SwingConstants.CENTER);
		timeLabel.setPreferredSize(LABEL_SIZE);
		timeLabel.setSize(LABEL_SIZE);
		timeLabel.setForeground(IConstants.ADD_LABEL_COLOR);
		timeLabel.setFont(IConstants.ADD_LABEL_FONT);
		add(timeLabel, cons);
		
		build(cons, 1, 1, 1, 1, 0, 0);
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		timeText = new JFormattedTextField(timeFormat);
		//timeText = new JFormattedTextField();
		//timeText.setDocument(new TextInputDocument(5));
		if ( item == null ) 
			timeText.setValue(new Date());
		else {
			try {
				Date time = timeFormat.parse(item.getTime());
				timeText.setValue(time);
			} catch (ParseException e) {
				timeText.setValue(new Date());
			}
		}
		timeText.setPreferredSize(new Dimension(190, 25));
		timeText.setFont(IConstants.ADD_LABEL_FONT);
		timeText.setForeground(IConstants.ADD_LABEL_COLOR);
		timeText.setBorder(IConstants.ADD_TEXT_BORDER);
		add(timeText, cons);
		
		//layer 3
		build(cons, 0, 2, 1, 1, 0, 0);
		JLabel descLabel = new JLabel("闹铃说明：", SwingConstants.CENTER);
		descLabel.setPreferredSize(LABEL_SIZE);
		descLabel.setSize(LABEL_SIZE);
		descLabel.setForeground(IConstants.ADD_LABEL_COLOR);
		descLabel.setFont(IConstants.ADD_LABEL_FONT);
		add(descLabel, cons);
		
		build(cons, 1, 2, 1, 1, 0, 0);
		cons.insets = new Insets(0, 0, 5, 0);
		descField = new JTextArea();
		descField.setPreferredSize(new Dimension(250, 60));
		descField.setFont(IConstants.ADD_LABEL_FONT);
		descField.setForeground(IConstants.ADD_LABEL_COLOR);
		descField.setBorder(IConstants.ADD_TEXT_BORDER);
		descField.setWrapStyleWord(true);
		if ( item != null )
			descField.setText(item.getDesc());
		add(descField, cons);
		cons.insets = new Insets(0, 0, 0, 0);
		
		//layer 4
		build(cons, 0, 3, 1, 1, 0, 0);
		JLabel cycleLabel = new JLabel("响铃周期：", SwingConstants.CENTER);
		cycleLabel.setPreferredSize(LABEL_SIZE);
		cycleLabel.setSize(LABEL_SIZE);
		cycleLabel.setForeground(IConstants.ADD_LABEL_COLOR);
		cycleLabel.setFont(IConstants.ADD_LABEL_FONT);
		add(cycleLabel, cons);
		
		build(cons, 1, 3, 1, 1, 0, 0);
		ButtonGroup cycle = new ButtonGroup();
		jrb = new JRadioButton[4];
		jrb[0] = new JRadioButton(Util.getCycleTitle(ActionItem.EVERYDAY));
		jrb[0].setFont(IConstants.ADD_LABEL_FONT);
		jrb[0].setForeground(IConstants.ADD_LABEL_COLOR);
		jrb[0].setBackground(Color.WHITE);
		if ( item == null ) 
			jrb[0].setSelected(true);
		else if ( item.getCycle() == ActionItem.EVERYDAY )
			jrb[0].setSelected(true);
		cycle.add(jrb[0]);
		
		jrb[1] = new JRadioButton(Util.getCycleTitle(ActionItem.WORKDAYS));
		jrb[1].setFont(IConstants.ADD_LABEL_FONT);
		jrb[1].setForeground(IConstants.ADD_LABEL_COLOR);
		jrb[1].setBackground(Color.WHITE);
		if ( item != null && item.getCycle() == ActionItem.WORKDAYS )
			jrb[1].setSelected(true);
		cycle.add(jrb[1]);
		
		jrb[2] = new JRadioButton(Util.getCycleTitle(ActionItem.WEEKENDAY));
		jrb[2].setFont(IConstants.ADD_LABEL_FONT);
		jrb[2].setForeground(IConstants.ADD_LABEL_COLOR);
		jrb[2].setBackground(Color.WHITE);
		if ( item != null && item.getCycle() == ActionItem.WEEKENDAY )
			jrb[2].setSelected(true);
		cycle.add(jrb[2]);
		
		jrb[3] = new JRadioButton(Util.getCycleTitle(ActionItem.ONCETIME));
		jrb[3].setFont(IConstants.ADD_LABEL_FONT);
		jrb[3].setForeground(IConstants.ADD_LABEL_COLOR);
		jrb[3].setBackground(Color.WHITE);
		if ( item != null && item.getCycle() == ActionItem.ONCETIME )
			jrb[3].setSelected(true);
		cycle.add(jrb[3]);
		
		/*box layout*/
		Box cycleBox = Box.createHorizontalBox();
		cycleBox.add(jrb[0]);
		cycleBox.add(Box.createHorizontalStrut(6));
		cycleBox.add(jrb[1]);
		cycleBox.add(Box.createHorizontalStrut(6));
		cycleBox.add(jrb[2]);
		cycleBox.add(Box.createHorizontalStrut(6));
		cycleBox.add(jrb[3]);
		add(cycleBox, cons);
		
		//layer 4
		build(cons, 0, 4, 1, 1, 0, 0);
		JLabel openLabel = new JLabel("立即启用：", SwingConstants.CENTER);
		openLabel.setPreferredSize(LABEL_SIZE);
		openLabel.setSize(LABEL_SIZE);
		openLabel.setForeground(IConstants.ADD_LABEL_COLOR);
		openLabel.setFont(IConstants.ADD_LABEL_FONT);
		add(openLabel, cons);
		
		build(cons, 1, 4, 1, 1, 0, 0);
		jsb = new JRadioButton[2];
		ButtonGroup start = new ButtonGroup();
		jsb[0] = new JRadioButton("是");
		jsb[0].setFont(IConstants.ADD_LABEL_FONT);
		jsb[0].setForeground(IConstants.ADD_LABEL_COLOR);
		jsb[0].setBackground(Color.WHITE);
		if ( item == null )
			jsb[0].setSelected(true);
		else if ( item.getStart() == ActionItem.STARTED ) 
			jsb[0].setSelected(true);
		start.add(jsb[0]);
		
		jsb[1] = new JRadioButton("否");
		jsb[1].setFont(IConstants.ADD_LABEL_FONT);
		jsb[1].setForeground(IConstants.ADD_LABEL_COLOR);
		jsb[1].setBackground(Color.WHITE);
		if ( item != null && item.getStart() == ActionItem.CLOSED )
			jsb[1].setSelected(true);
		start.add(jsb[1]);
		
		Box startBox = Box.createHorizontalBox();
		startBox.add(jsb[0]);
		startBox.add(Box.createHorizontalStrut(18));
		startBox.add(jsb[1]);
		add(startBox, cons);
	}
	
	/**
	 * return the selected item in the cycle JRadioButton group
	 * 
	 * @return int
	 */
	private int getCycle() {
		for ( int j = 0; j < jrb.length; j++ ) {
			if ( jrb[j].isSelected() == true )
				return j;
		}
		return 0;
	}
	
	/**
	 * return the selected item in the start JRadioButton group
	 * 
	 * @return int
	 */
	private int getStart() {
		for ( int j = 0; j < jsb.length; j++ ) {
			if ( jsb[j].isSelected() == true )
				return j;
		}
		return 0;
	}
	
	/**
	 * add a new item to the database and save the database at the same time.
	 * 		also need to update the model of the JList.
	 */
	public void addItem() {
		final String date = dateText.getText();
		final String time = timeText.getText();
		String desc = descField.getText();
		int cycle = getCycle();
		final int start = getStart();
		//create a new action item
		final ActionItem item = new ActionItem();
		item.setDate(date);
		item.setTime(time);
		item.setDesc(desc);
		item.setCycle(cycle);
		item.setStart(start);
		//add the new item to the database.
		if ( Database.getDatabase() == null ) {
			JOptionPane.showMessageDialog(null, ProcessJPanel.__DBINITERROR_TIP,
					"New Item: ", JOptionPane.ERROR_MESSAGE);
			dialog.hiddenProcessJPanel();
			return;
		}
		
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				Database.getDatabase().add(item);
				try {
					Database.getDatabase().saveDatabaseToFile(DClock.__DB_FILE);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null,
							"Fail to save the item.", "New Item Tip: ", JOptionPane.ERROR_MESSAGE);
				}
				//update model
				JListModel.getInstance().addNewItem(item);
				/*update the next item, if the condition is crorrect*/
				String[] args = Util.getCurrentDateTime();
				ActionItem next =  JListModel.getInstance().getNextItem();
				if ( next != null ) {
					if ( start == ActionItem.STARTED 
							&& item.isValidCycle(args[0], Integer.parseInt(args[2]))
							&& ActionItem.strCompare(time, args[1]) > 0
							&& ActionItem.strCompare(time, next.getTime()) < 0 )
						JListModel.getInstance().setNextItem(item);
				}
				else {
					if ( start == ActionItem.STARTED 
							&& item.isValidCycle(args[0], Integer.parseInt(args[2]))
							&& ActionItem.strCompare(time, args[1]) > 0 ) 
						JListModel.getInstance().setNextItem(item);
				}
				
				//JOptionPane.showMessageDialog(null,
						//"New Items added successfully", "New Item Tip: ", JOptionPane.INFORMATION_MESSAGE);
				//dialog.hiddenProcessJPanel();
				dialog.hiddenDialog();
			}
		}).start();
	}
	
	/**
	 * edit the selected item.
	 */
	public void editItem() {
		if ( item == null ) return; 		
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				String date = dateText.getText();
				String time = timeText.getText();
				String desc = descField.getText();
				int cycle = getCycle();
				int start = getStart();
				
				//update the item
				item.setDate(date);
				item.setTime(time);
				item.setDesc(desc);
				item.setCycle(cycle);
				item.setStart(start);
				try {
					Database.getDatabase().saveDatabaseToFile(DClock.__DB_FILE);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null,
							"Fail to save the item.", "Modified Item Tip: ", JOptionPane.ERROR_MESSAGE);
				}

				/*update the next item, if the condition is crorrect*/
				String[] args = Util.getCurrentDateTime();
				ActionItem next =  JListModel.getInstance().getNextItem();
				if ( next != null ) {
					if ( start == ActionItem.STARTED 
							&& item.isValidCycle(args[0], Integer.parseInt(args[2]))
							&& ActionItem.strCompare(time, args[1]) > 0
							&& ActionItem.strCompare(time, next.getTime()) < 0 )
						JListModel.getInstance().setNextItem(item);
				}
				else {
					if ( start == ActionItem.STARTED 
							&& item.isValidCycle(args[0], Integer.parseInt(args[2]))
							&& ActionItem.strCompare(time, args[1]) > 0 ) 
						JListModel.getInstance().setNextItem(item);
				}
				
				dialog.hiddenDialog();
			}
		}).start();
	}
	
	public static void build(GridBagConstraints c, int gridx, int gridy,
			int gridwidth, int gridheight, int weightx, int weighty) {
		c.gridx		= gridx;
		c.gridy		= gridy;
		c.gridwidth = gridwidth;
		c.gridheight = gridheight;
		c.weightx 	= weightx;
		c.weighty 	= weighty;
	}
	
	@Override
	protected void paintComponent( Graphics g ) {
		g.setColor(new Color(111, 168, 218));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		g.setColor(Color.WHITE);
		g.fillRect(2, 0, this.getWidth() - 4, this.getHeight());
	}

}

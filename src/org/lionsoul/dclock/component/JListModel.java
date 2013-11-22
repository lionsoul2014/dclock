package org.lionsoul.dclock.component;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import org.lionsoul.dclock.DClock;
import org.lionsoul.dclock.TimeListenerTask;
import org.lionsoul.dclock.model.ActionItem;
import org.lionsoul.dclock.model.Database;
import org.lionsoul.dclock.util.Config;
import org.lionsoul.dclock.util.IConstants;
import org.lionsoul.dclock.util.Util;


/**
 * model for the action item jList
 * 
 * @author chenxin <chenxin619315@gmail.com>
 */
public class JListModel extends DefaultListModel {

	private static final long serialVersionUID = 8494389252494099553L;
	
	private static JListModel __instance = null;
	private ActionItem nextItem = null;
	
	public JListModel() {
		
		/**
		 * load all the items in the database.
		 * and all them to the model. 
		 */
		new Thread(new Runnable() {
			@Override
			public void run() {
				synchronized ( JListModel.this ) {					
					try {
						Database.createDatabase(DClock.__DB_FILE);
						ActionItem[] items = Database.getDatabase().query();
						if ( items.length != 0 ) {
							nextItem = Util.findNextItem(items);
							//if ( nextItem != null ) {
								//addNewItem(nextItem);
							//} 
							
							for ( int j = 0; j < items.length; j++ ) {
								/*if ( nextItem == items[j] ) 
									continue;*/
								addNewItem(items[j]);
							}
						}
						
						/*
						 * parse the properties file.
						 * 		-located in the JAR_HOME directory. 
						 */
						Properties pro = new Properties();
						File pfile = new File(DClock.JAR_HOME+"/"+IConstants.__PROPERTIES_FILE);
						if ( pfile.exists() ) {
							pro.load(new FileReader(pfile));
							if ( pro.getProperty("clock.media") != null ) 
								Config.CLOCK_MEDIA_FILE = pro.getProperty("clock.media");
							if ( pro.getProperty("clock.sctime") != null )
								Config.SELF_CLOSE_TIME = Integer.parseInt(pro.getProperty("clock.sctime"));
						} 
						
						/*
						 * start the listener thread here.
						 * wether there is items or not, or valid items
						 * we must start the listener thread. 
						 */
						DClock.getInstance().setTimeTask(new TimeListenerTask());
						DClock.getInstance().startListenerThread();
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, e);
						addElement(new String("<html><font color='red'>Fail to initialize database...</font></html>"));
					}
				}
			}
		}).start();
	}
	
	public synchronized void addNewItem( ActionItem item ) {
		/*StringBuilder sb = new StringBuilder();
		sb.append("<html>");
		sb.append("<font color='red'>"+item.getTime()+"</font>");
		sb.append(" - "+Util.getCycleTitle(item.getCycle())+"<br />");
		sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		sb.append(item.getDesc());
		sb.append("</html>");*/
		addElement(item);
	}
	
	public synchronized void removeItem( int idx ) {
		remove(idx);
	}
	
	public synchronized ActionItem getNextItem() {
		return nextItem;
	}
	
	public synchronized void setNextItem( ActionItem item ) {
		nextItem = item;
	}

	
	public static JListModel getInstance() {
		if ( __instance == null )
			__instance = new JListModel();
		return __instance;
	}
	
}

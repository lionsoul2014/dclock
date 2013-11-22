package org.lionsoul.dclock;

import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.MenuItem;
import java.awt.Point;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.RoundRectangle2D;
import java.net.URI;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.lionsoul.dclock.component.ContentJPanel;
import org.lionsoul.dclock.component.JListScrollPane;
import org.lionsoul.dclock.util.CreateIcon;
import org.lionsoul.dclock.util.IConstants;
import org.lionsoul.dclock.util.Util;

//import com.sun.jna.platform.WindowUtils;

/**
 * main class for desktop clock.
 * 
 * @author chenxin <chenxin619315@gmail.com>
 */
public class DClock extends JFrame {
	
	private static final long serialVersionUID = 9031000374783430252L;
	
	public static Dimension SCREEN_SIZE = null;
	public static String SYSTEM = System.getProperty("os.name").toUpperCase();
	public static String JAR_HOME = System.getProperty("user.dir");
	public static String __DB_FILE = null;
	public static ExecutorService threadMaster =  Executors.newCachedThreadPool();
	
	private static DClock __instance = null;
	private static TrayIcon tray = null;
	private Point MDown = null;
	private TimeListenerTask timeTask = null;
	
	private DClock() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//clear the border of the JFrame
		setUndecorated(true);
		
		setTitle(IConstants.W_TITLE);
		//setResizable(false);
		setSize(IConstants.W_SIZE);
		setLocationRelativeTo(null);
		
		__instance = this;
		JAR_HOME = Util.getJarHome(this);
		__DB_FILE = new String(JAR_HOME+"/"+IConstants.__DATA_FILE);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		/**
		 * Attension:
		 * it not work for linux (ubuntu). work for window 
		 */
		Insets sInsets = Toolkit.getDefaultToolkit()
			.getScreenInsets(this.getGraphicsConfiguration());
		SCREEN_SIZE = new Dimension(
				screenSize.width - sInsets.left - sInsets.right,
				screenSize.height - sInsets.top - sInsets.bottom);
		
		//set the content pane
		setContentPane(new ContentJPanel());
		//add(new ContentJPanel());
		
		addMouseListener(new DMouseListener());
		addMouseMotionListener(new DMouseMotionListener());
/*		addComponentListener(new ComponentAdapter() {
			public void componentResized( final ComponentEvent e ) {
				
			}
		});*/
		
		/**
		 * 1.
		 * use jna - the implmenter of jni - java native interface
		 * to set the opacity and the shape fo the window 
		 */
		//WindowUtils.setWindowAlpha(this, 0.5f);
		//RoundRectangle2D.Float mask = new RoundRectangle2D.Float(0, 0,
				//getWidth(), getHeight(), 5, 5);  
	    //WindowUtils.setWindowMask(this, mask);
		
		/**
		 * 2. 
		 * use com.sun.awt.AWTUtilities.
		 */
		//com.sun.awt.AWTUtilities.setWindowOpacity(this, 0.8f); 
	    com.sun.awt.AWTUtilities.setWindowShape(this
	    		 , new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 5, 5));
	}
	
	public static DClock getInstance() {
		return __instance;
	}
	
	/**
	 * add the window to the system tray 
	 */
	public static void tray() {
		if ( tray != null ) {
			getInstance().setVisible(false);
			return;
		}
		SystemTray systemTray = SystemTray.getSystemTray();
		tray = new TrayIcon(CreateIcon.createIcon("clock.png").getImage(), IConstants.W_TITLE);
		tray.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getInstance().setVisible(true);
			}
		});
		tray.setImageAutoSize(true);
		
		PopupMenu popupMenu = new PopupMenu();
		
		MenuItem about = new MenuItem("About DClcok");
		about.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if ( ! Desktop.isDesktopSupported()) 
					return;
				Desktop desktop = Desktop.getDesktop();
				if ( ! desktop.isSupported(Desktop.Action.BROWSE) ) {
					JOptionPane.showMessageDialog(null, "系统不支持该功能\n您可以直接访问http://www.qgzx.net",
							"系统提示：", JOptionPane.ERROR_MESSAGE);
					return;
				}
				try {
					desktop.browse(new URI("http://www.qgzx.net"));
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});
		popupMenu.add(about);
		
		MenuItem exit = new MenuItem("Exit");
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		popupMenu.add(exit);
		
		tray.setPopupMenu(popupMenu);
		
		try {
			systemTray.add(tray);
			getInstance().setVisible(false);
		} catch (AWTException e) {
			JOptionPane.showMessageDialog(null, "添加到系统托盘失败。",
					"系统提示：", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	/**
	 * set the time task
	 * 
	 * @param task
	 */
	public synchronized void setTimeTask( TimeListenerTask task  ) {
		timeTask = task;
	}
	
	/**
	 * get the time task 
	 * 
	 * @return TimeListenerTask
	 */
	public synchronized TimeListenerTask getTimeTask() {
		return timeTask;
	}
	
	/**
	 * start the time listener thread. 
	 */
	public void startListenerThread() {
		if ( getTimeTask() == null ) return;
		threadMaster.execute(getTimeTask());
	}
	
	/**
	 * mouse listener class.
	 */
	private class DMouseListener extends MouseAdapter {
		
		@Override
		public void mouseClicked( MouseEvent e ) {
			if ( e.getClickCount() != 2 ) return;
			if ( getSize().width < SCREEN_SIZE.width )
				setFramesize(SCREEN_SIZE);
			else 
				setFramesize(IConstants.W_SIZE);
		}

		@Override
		public void mousePressed(MouseEvent e) {
			MDown = e.getPoint();
		}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}
	}
	
	/**
	 * mouse motion listener class. 
	 */
	private class DMouseMotionListener implements MouseMotionListener {

		@Override
		public void mouseDragged(MouseEvent e) {
			Point p = e.getLocationOnScreen();
			//setLocation(MDown.x + (p.x - EDown.x), MDown.y + (p.y - EDown.y));
			//setLocation(p.x - (EDown.x - MDown.x), p.y - (EDown.y - MDown.y));
			setPosition(p.x - MDown.x, p.y - MDown.y);
		}

		@Override
		public void mouseMoved(MouseEvent e) {}
	}
	
	public void setPosition( final int x, final int y ) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				setLocation(x, y);
			}
		});
	}
	
	public void setFramesize( final Dimension size ) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				setSize(size);
				setLocationRelativeTo(null);
				if ( SYSTEM.equals("LINUX") && size.width == SCREEN_SIZE.width ) 
					JListScrollPane.getInstance()
						.setScrollSize(new Dimension(size.width, size.height - 30));
				else 
					JListScrollPane.getInstance().setScrollSize(size);
				//round rectangle
				com.sun.awt.AWTUtilities.setWindowShape(DClock.this
			    		 , new RoundRectangle2D.Float(0, 0, size.width, size.height, 5, 5));
			}
		});
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new DClock().setVisible(true);
			}
		});
	}

}

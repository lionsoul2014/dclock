package org.lionsoul.dclock.component;

import java.awt.BorderLayout;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JDialog;
import javax.swing.SwingUtilities;

import org.lionsoul.dclock.DClock;
import org.lionsoul.dclock.util.IConstants;


/**
 * frame to add new action item.
 * 
 * @author chenxin <chenxin619315@gmail.com>
 */
public class AddJDialog extends JDialog {

	private static final long serialVersionUID = -3437543515625561679L;
	public static final int ADD_MODE = 1;
	public static final int EDIT_MODE = 2;
	
	private AddMiddleJPanel middleJPanel = null;
	private ProcessJPanel processJPanel = null;
	public int mode = 0;
	
	public AddJDialog( int m ) {
		super(DClock.getInstance(), true);
		//clear the border of the JDialog
		setUndecorated(true);
		setSize(IConstants.ADD_W_SIZE);
		setLocationRelativeTo(null);
		
		mode = m;
		
		processJPanel = new ProcessJPanel();
		setGlassPane(processJPanel);
		
		setLayout(new BorderLayout());
		add(new AddTopJPanel(this), BorderLayout.NORTH);
		middleJPanel = new AddMiddleJPanel(this);
		add(middleJPanel, BorderLayout.CENTER);
		add(new AddBottomJPanel(this), BorderLayout.SOUTH);
		
		com.sun.awt.AWTUtilities.setWindowShape(this,
				new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 5, 5));
	}
	
	public AddMiddleJPanel getMiddleJPanel() {
		return middleJPanel;
	}
	
	public ProcessJPanel getProcessJPanel() {
		return processJPanel;
	}
	
	/**
	 * show the glass pane 
	 */
	public void showProcessJPanel() {
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				processJPanel.setVisible(true);
				processJPanel.updateText(ProcessJPanel.__PROCESS_TIP);
			}
		});
	}
	
	/**
	 * hidden the glassPane 
	 */
	public void hiddenProcessJPanel() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				processJPanel.setVisible(false);
			}
		});
	}
	
	/**
	 * set the position of the JDialog.
	 */
	public void setPosition( final int x, final int y ) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				setLocation(x, y);
			}
		});
	}
	
	/**
	 * show the Dialog 
	 */
	public void showDialog() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				setVisible(true);
			}
		});
	}
	
	/**
	 * hidden the Dialog 
	 */
	public void hiddenDialog() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				setVisible(false);
				dispose();
			}
		});
	}


}

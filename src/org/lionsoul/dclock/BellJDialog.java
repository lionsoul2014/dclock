package org.lionsoul.dclock;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JDialog;
import javax.swing.JPanel;

import org.lionsoul.dclock.model.ActionItem;
import org.lionsoul.dclock.util.ClockAudio;
import org.lionsoul.dclock.util.Config;


public class BellJDialog extends JDialog implements Runnable {

	private static final long serialVersionUID = -7852480547952147014L;
	public static final int STOP_S = 0;
	public static final int RUN_S = 1;
	
	private Font font = new Font("宋体", Font.BOLD, 60);
	private int rstate = RUN_S;
	
	private ItemJPanel pane = null;
	private FontMetrics fm = null;
	private String time = null;
	private char[] desc = null;
	
	public BellJDialog( ActionItem next ) {
		setUndecorated(true);
		setAlwaysOnTop(true);
		setSize(DClock.SCREEN_SIZE);
		setLocationRelativeTo(null);
		
		pane = new ItemJPanel();
		pane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if ( e.getClickCount() != 2 ) return;
				close();
			}
		});
		add(pane, BorderLayout.CENTER);
		setVisible(true);
		
		time = next.getTime();
		desc = next.getDesc().toCharArray();
	}
	
	/**
	 * JPanel inner class. 
	 */
	private class ItemJPanel extends JPanel {

		private static final long serialVersionUID = -2396640762423346782L;
		private String str = null;
		
		public ItemJPanel() {
			setPreferredSize(new Dimension(DClock.SCREEN_SIZE.width,
					DClock.SCREEN_SIZE.height - 30));
			fm = this.getFontMetrics(font);
		}
		
		@Override
		protected void paintComponent( Graphics g ) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			
			g.setFont(font);
			g.setColor(Color.RED);
			//draw the time.
			for ( int j = 0; j < 5; j++ ) {
				g.drawString(time, (getWidth() - fm.stringWidth(time)) / 2 - j, getHeight() / 4 - j);
			}
			
			int rd = (int) (255 * Math.random());
			int gn = (int) (255 * Math.random());
			int be = (int) (255 * Math.random());
			
			synchronized ( this ) {
				if ( str == null ) return;
				g.setColor(Color.ORANGE);
				/*g.drawString(str, (getWidth() - fm.stringWidth(str)) / 2, getHeight()/2);
				g.setColor(Color.WHITE);
				g.drawString(str, (getWidth() - fm.stringWidth(str)) / 2 + 2, getHeight()/2 + 2);*/
				for ( int j = 6; j >= 0; j-- ) {
					g.setColor(new Color(
							255 - ((255 - rd) * j) / 10,
							255 - ((255 - gn) * j) / 10,
							255 - ((255 - be) * j) / 10));
					g.drawString(str, (getWidth() - fm.stringWidth(str)) / 2 - j, getHeight()/2 - j);
				}
			}
		}
		
		public synchronized void setText( String str ) {
			this.str = str;
			repaint();
		}
		
	}
	
	public synchronized int getRState() {
		return rstate;
	}
	
	public synchronized void setRState( int s ) {
		rstate = s;
	}
	
	public void close() {
		setRState(STOP_S);
		setVisible(false);
		dispose();
	}

	@Override
	public void run() {
		ClockAudio ca = new ClockAudio();
		ca.playClockAudio();
		StringBuilder sb = new StringBuilder();
		int idx = 0;
		int counter = 0;
		while ( getRState() == RUN_S ) {
			try {
				if ( idx < desc.length ) {
					sb.append(desc[idx]);
					idx++;
					pane.setText(sb.toString());
				} 
				Thread.sleep(500);
				if ( counter++ >= 2 * Config.SELF_CLOSE_TIME ) close();
			} catch (InterruptedException e) {
				break;
			}
		}
		ca.stopClockAudio();
	}

}

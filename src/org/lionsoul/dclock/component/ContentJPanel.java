package org.lionsoul.dclock.component;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;


/**
 * main content pane.
 * 
 * @author chenxin <chenxin619315@gmail.com>
 */
public class ContentJPanel extends JPanel {

	private static final long serialVersionUID = 6776973193589128098L;
	
	public ContentJPanel() {
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints cons = new GridBagConstraints();
		cons.fill = GridBagConstraints.BOTH;
		
		//row 1
		build(cons, 0, 0, 1, 1, 100, 0);
		add(new DTopJPanel(), cons);
		
		//row 2
		build(cons, 0, 1, 1, 1, 0, 100);
		add(new DMiddleJPanel(), cons);
		
		//row 3
		build(cons, 0, 2, 1, 1, 0, 0);
		add(new DBottomJPanel(), cons);
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

}

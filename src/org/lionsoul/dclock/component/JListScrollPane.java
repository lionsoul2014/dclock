package org.lionsoul.dclock.component;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;

import org.lionsoul.dclock.util.IConstants;


/**
 * scroll jList
 * 
 * @author chenxin <chenxin619315@gmail.com>
 */
public class JListScrollPane extends JScrollPane {

	private static final long serialVersionUID = 5628421975752852588L;
	
	private static JListScrollPane __instance = null;
	private JList list = null;
	
	private JListScrollPane() {
		
		list = new JList();
		list.setModel(JListModel.getInstance());
		list.setOpaque(false);
		list.setBorder(null);
		JLabel label = (JLabel) list.getCellRenderer();
		label.setOpaque(false);
		list.setFont(IConstants.LIST_FONT);
		list.setForeground(Color.darkGray);
		list.setSelectionForeground(IConstants.LIST_SELECTION_FCOLOR);
		list.setFixedCellHeight(35);
		//list.setCellRenderer(new MCellRenderer());
		
		setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		setViewportView(list);
		setOpaque(false);
		getViewport().setOpaque(false);
		setBorder(null);
		getVerticalScrollBar().setPreferredSize(new Dimension(12, 0));
		getVerticalScrollBar().setUI(new MBasicScrollBar());
		setScrollSize(IConstants.W_SIZE);
	}
	
	public void setScrollSize( Dimension size ) {
		Dimension nSize = new Dimension( size.width - 20, size.height - 120 - 15);
		/**
		 * here
		 * do not put the resize work in the EDT.
		 * cause we call this method in the EDT. 
		 */
		/*SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				setPreferredSize(nSize);
				setSize(nSize);
				//repaint();
			}
		});*/
		setPreferredSize(nSize);
		setSize(nSize);
	}
	
	/**
	 * return teh JList quote 
	 */
	public JList getScrollJList() {
		return list;
	}
	
	public static JListScrollPane getInstance() {
		if ( __instance == null )
			__instance = new JListScrollPane();
		return __instance;
	}

}

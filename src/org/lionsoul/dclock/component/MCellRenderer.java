package org.lionsoul.dclock.component;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import org.lionsoul.dclock.util.CreateIcon;


public class MCellRenderer extends JLabel implements ListCellRenderer {

	private static final long serialVersionUID = 8380983649789307349L;
	
	

	public MCellRenderer() {
        setOpaque(true);
        setIconTextGap(5);
    }

	@Override
    public Component getListCellRendererComponent(JList list, Object value,
    		int index, boolean isSelected, boolean cellHasFocus) {
        this.setIcon(CreateIcon.createIcon("close.png"));
        this.setText(value.toString());

        if ( isSelected ) {
        	this.setIcon(CreateIcon.createIcon("close-hover.png"));
        } else {
        	this.setIcon(CreateIcon.createIcon("close.png"));
        }
        return this;
    }
	
	

}
package org.lionsoul.dclock.component;

import java.awt.Dimension;        
import java.awt.Graphics;         
import java.awt.Image;         
import java.awt.Rectangle;        
     
import javax.swing.ImageIcon;
import javax.swing.JButton;     
import javax.swing.JComponent;     
import javax.swing.JScrollBar;     
import javax.swing.plaf.basic.BasicArrowButton;     
import javax.swing.plaf.basic.BasicScrollBarUI;     

import org.lionsoul.dclock.util.CreateIcon;

    
/**
 * scroll bar UI
 * 
 * @author chenxin <chenxin619315@gmail.com>
 */
public class MBasicScrollBar extends BasicScrollBarUI {  
    
    public Dimension getPreferredSize(JComponent c) {     
        return new Dimension(12, 0);     
    }     
    
    //repaint the slider of the scroll bar.
    public void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {     
        super.paintThumb(g, c, thumbBounds); 
        //must call this
        g.translate(thumbBounds.x, thumbBounds.y);     
        
        ImageIcon icon = CreateIcon.createIcon("slider-handler.png");    
        if (this.scrollbar.getOrientation() == JScrollBar.VERTICAL) {     }     
        if (this.scrollbar.getOrientation() == JScrollBar.HORIZONTAL) {      }     
        g.drawImage(icon.getImage(), 0, 0, 12, thumbBounds.height, null);
    }     
    
    /**slider bg*/
    public void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {     
    	ImageIcon icon = CreateIcon.createIcon("slider-bg.png");
    	g.drawImage(icon.getImage(), 0, 0, 12, trackBounds.height + 26, null);     
    }       
    
    /**increase button*/
    protected JButton createIncreaseButton(int orientation) {     
        return new BasicArrowButton(orientation) {     
			private static final long serialVersionUID = 1L;
   
            public void paintTriangle(Graphics g, int x, int y, int size, int direction, boolean isEnabled) {       
                Image arrowImg = null;     
                switch (this.getDirection()) {     
                case BasicArrowButton.SOUTH:      
                    arrowImg = CreateIcon.createIcon("slider-south.png").getImage();     
                    break;     
                case BasicArrowButton.EAST:       
                    arrowImg = CreateIcon.createIcon("slider-south.png").getImage();     
                    break;     
                }        
                g.drawImage(arrowImg, 0, 0, 12, 15, null);     
            }     
        };     
    }     
    
    /**decreatse button*/
    protected JButton createDecreaseButton(int orientation) {     
        return new BasicArrowButton(orientation) {     
			private static final long serialVersionUID = 1L;
			public void paintTriangle(Graphics g, int x, int y, int size, int direction, boolean isEnabled) {         
                Image arrowImg = null;     
                switch (this.getDirection())     
                {     
                case BasicArrowButton.NORTH:       
                    arrowImg = CreateIcon.createIcon("slider-north.png").getImage();     
                    break;     
                case BasicArrowButton.WEST:     
                    arrowImg = CreateIcon.createIcon("slider-north.png").getImage();     
                    break;     
                }   
                g.drawImage(arrowImg, 0, 0, 12, 15, null);     
            }     
        };     
    }     
}  
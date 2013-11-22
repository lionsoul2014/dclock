package org.lionsoul.dclock.util;

import javax.swing.ImageIcon;

/**
 * destop clock create factory calss.
 * 
 * @author chenxin <chenxin619315@gmail.com>
 */
public class CreateIcon {
	public static ImageIcon createIcon(String filename) {
		ImageIcon icon = new ImageIcon(CreateIcon.class.getResource("/res/images/"+filename));
		return icon;
	}
}

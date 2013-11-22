package org.lionsoul.dclock.util;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public interface IConstants {
	/**
	 * size of the window 
	 */
	public static final Dimension W_SIZE = new Dimension(260, 350);
	public static final String W_TITLE = "Desktop Clock";
	
	public static final Dimension W_TOP_SIZE =  new Dimension(260, 80);
	public static final Dimension W_MIDDLE_SIZE =  new Dimension(260, 230);
	public static final Dimension W_BOTTOM_SIZE =  new Dimension(260, 40);
	
	/**
	 * item add window 
	 */
	public static final String ADD_W_TITLE = "新建事务";
	public static final Font ADD_WTITLE_FONT = new Font("Arial", Font.BOLD, 12);
	public static final Dimension ADD_W_SIZE = new Dimension(400, 270);
	public static final Dimension ADD_WTOP_SIZE = new Dimension(400, 35);
	public static final Dimension ADD_WBOTTOM_SIZE = new Dimension(400, 35);
	public static final Color ADD_LABEL_COLOR = new Color(44, 105, 161);
	public static final Font ADD_LABEL_FONT = new Font("Arial", Font.PLAIN, 12);
	public static final Border ADD_TEXT_BORDER = new LineBorder(Color.LIGHT_GRAY, 1);
	
	/**
	 * close and max min button size. 
	 */
	public static final Dimension MIN_BUTTON_SIZE = new Dimension(23, 22);
	public static final Dimension MAX_BUTTON_SIZE = new Dimension(23, 22);
	public static final Dimension CLOSE_BUTTON_SIZE = new Dimension(28, 22);
	
	//Courier New
	public static final Font TITLE_FONT =  new Font("Arial", Font.BOLD, 14);
	public static final Font LIST_FONT = new Font("宋体", Font.PLAIN, 12);
	public static final Color LIST_FCOLOR = Color.GRAY;
	public static final Color LIST_SELECTION_BCOLOR = new Color(40, 101, 156);
	public static final Color LIST_SELECTION_FCOLOR = new Color(40, 101, 156);
	//public static final Color LIST_SELECTION_FCOLOR = Color.MAGENTA;
	
	public static final Cursor H_CURSOR = new Cursor(Cursor.HAND_CURSOR);
	
	/**data file to store all the ActionItem.*/
	public static final String __DATA_FILE = "__DATA_FILE.DB";
	public static final String __PROPERTIES_FILE = "dclock.properties";
}

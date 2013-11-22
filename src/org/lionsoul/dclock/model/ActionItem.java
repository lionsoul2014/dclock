package org.lionsoul.dclock.model;

import java.io.Serializable;
import java.util.Calendar;

import org.lionsoul.dclock.util.Util;


/**
 * action item.
 * 
 * @author chenxin <chenxin619315@gmail.com>
 */
public class ActionItem implements Serializable {

	private static final long serialVersionUID = -6726144008167688232L;
	
	public static final int EVERYDAY = 0;
	public static final int WORKDAYS = 1;
	public static final int WEEKENDAY = 2;
	public static final int ONCETIME = 3;
	
	public static final int CLOSED = 1;
	public static final int STARTED = 0;
	
	/**the index in the database.*/
	private int index;
	
	/**the begain date*/
	private String date;
	
	/**the bell time*/
	private String time;
	
	/**the bell describe.*/
	private String desc;
	
	/**the cycle of the bell.*/
	private int cycle;
	
	/**start the item or not*/
	private int start = 0;
	
	public ActionItem() {}
	
	public ActionItem( String date, String time,
			String desc, int cycle, int start ) {
		this.date = date;
		this.time = time;
		this.desc = desc;
		this.cycle = cycle;
		this.start = start;
	}
	
	public void setIndex( int index) {
		this.index = index;
	}
	public int getIndex() {
		return index;
	}
	
	public void setDate( String date ) {
		this.date = date;
	}
	public String getDate() {
		return date;
	}
	
	public void setTime( String time ) {
		this.time = time;
	}
	public String getTime() {
		return time;
	}
	
	public void setDesc( String desc ) {
		this.desc = desc;
	}
	public String getDesc() {
		return desc;
	}
	
	public void setCycle( int cycle ) {
		this.cycle = cycle;
	}
	public int getCycle() {
		return cycle;
	}
	
	public void setStart( int start ) {
		this.start = start;
	}
	public int getStart() {
		return start;
	}
	
	/**
	 * get the specified value the key
	 * 
	 * @param key
	 * @return String
	 */
	public String get( String key ) {
		if ( key == null ) return null;
		
		if ( key.equalsIgnoreCase("index") )
			return index+"";
		else if ( key.equalsIgnoreCase("time") )
			return time;
		else if ( key.equalsIgnoreCase("desc") )
			return desc;
		else if ( key.equalsIgnoreCase("cycle") )
			return cycle+"";
		
		return null;
	}
	
	/**
	 * @see Object#toString() 
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append((index+1));
		if ( sb.toString().length() == 1 ) 
			sb.insert(0, '0');
			
		//return time+" - "+cycle+", "+desc;
		return "<html>"+sb.toString()+". <font color='red'>"+date+"-"+time+"</font> - "
			+Util.getCycleTitle(cycle)
			+(start==STARTED?",<font color='green'>启用中</font>":",<font color='red'>已关闭</font>")
			+"<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
			desc+"</html>";
	}
	
	/**
	 * @see Object#equals(Object) 
	 */
	public boolean equals( ActionItem a ) {
		if ( this == a )
			return true;
		if ( a.getIndex() == index
				|| (a.getTime() != null && a.getTime().equals(time)))
			return true;
		return false;
	}
	
	/**
	 * time compare
	 * 
	 * @param time
	 * @return int
	 */
	public static int strCompare( String src, String dst ) {
		if ( src == null || dst == null ) return 1; 
		if ( src.length() > dst.length() ) 
			return 1;
		else if ( src.length() < dst.length() )
			return -1;
		
		for ( int j = 0; j < src.length(); j++ ) {
			char o = src.charAt(j);
			char n = dst.charAt(j);
			if ( o > n )
				return 1;
			else if ( o < n )
				return -1;
		}
		return 0;
	}
	
	/**
	 *  check the current item is with a valid cycle.
	 *  
	 *  @param day
	 *  @return boolean
	 */
	public boolean isValidCycle( String $date, int day ) {
		if ( date == null || $date == null )
			return false;
		if ( strCompare(date, $date) > 0 )
			return false;
		
		switch ( cycle ) {
			case EVERYDAY:
				return true;
			case WORKDAYS:
				if ( day >= Calendar.MONDAY
						&& day <= Calendar.FRIDAY)
					return true;
				return false;
			case WEEKENDAY:
				if ( day == Calendar.SATURDAY
						|| day == Calendar.SUNDAY)
					return true;
				return false;
			case ONCETIME:
				return true;
		}
		return false;
	}
	
	/**
	 * check the current item is started or not.
	 * 
	 * @return boolean
	 */
	public boolean isStarted() {
		if ( start == STARTED )
			return true;
		return false;
	}

}

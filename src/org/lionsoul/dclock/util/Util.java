package org.lionsoul.dclock.util;

import java.io.File;
import java.util.Calendar;

import org.lionsoul.dclock.model.ActionItem;
import org.lionsoul.dclock.model.Database;


/**
 * common useful function.
 * 
 * @author chenxin <chenxin619315@gmail.com>
 */
public class Util {
	
	public static String getJarHome(Object o) {
		String path = o.getClass().getProtectionDomain()
					.getCodeSource().getLocation().getFile();
		File jarFile = new File(path);
		return jarFile.getParentFile().getAbsolutePath();
	}
	
	public static boolean isDigit( String str ) {
		if ( str == null ) return false;
		for ( int j = 0; j < str.length(); j++ ) {
			char c = str.charAt(j);
			if ( c < '0' || c > '9' )
				return false;
		}
		return true;
	}
	
	public static String getCycleTitle( int cycle ) {
		if ( cycle == ActionItem.EVERYDAY )
			return "每天";
		else if ( cycle == ActionItem.WORKDAYS )
			return "工作时间";
		else if ( cycle == ActionItem.WEEKENDAY )
			return "周末";
		else 
			return "一次";
	}
	
	/**
	 * find the next item that it's time close to current time most. 
	 */
	public static ActionItem findNextItem() {
		ActionItem[] items = Database.getDatabase().query();
		return findNextItem(items);
	}
	
	public static ActionItem findNextItem(ActionItem[] items) {	
		String[] args = getCurrentDateTime();
		ActionItem next = null;
		boolean findnext = false;
		for ( int j = 0; j < items.length; j++ ) {
			ActionItem item = items[j];
			if ( ! item.isStarted() ) continue; 
			if ( ! item.isValidCycle(args[0], Integer.parseInt(args[2]))) continue;
			if ( ActionItem.strCompare(item.getTime(), args[1]) < 0 ) continue;
			if ( findnext == false ) {
				next = item;
				findnext = true;
				continue;
			} 
			if ( ActionItem.strCompare(item.getTime(), next.getTime()) < 0 )
				next = item;
		}
		
		return next;
	}
	
	
	/**
	 * find the next item that it's time close to current time most.
	 * and filter the current next item. 
	 */
	public static ActionItem findNextItem(ActionItem next) {
		ActionItem[] items = Database.getDatabase().query();
		return findNextItem(items, next);
	}
	
	public static ActionItem findNextItem(ActionItem[] items, ActionItem cnext) {	
		String[] args = getCurrentDateTime();
		ActionItem next = null;
		boolean findnext = false;
		for ( int j = 0; j < items.length; j++ ) {
			ActionItem item = items[j];
			if ( item == cnext ) continue;
			if ( ActionItem.strCompare(cnext.getTime(), item.getTime()) == 0
					&& item.getIndex() < cnext.getIndex()) continue;
			if ( ! item.isStarted() ) continue; 
			if ( ! item.isValidCycle(args[0], Integer.parseInt(args[2]))) continue;
			if ( ActionItem.strCompare(item.getTime(), args[1]) < 0 ) continue;
			if ( findnext == false ) {
				next = item;
				findnext = true;
				continue;
			} 
			if ( ActionItem.strCompare(item.getTime(), next.getTime()) < 0 )
				next = item;
		}
		return next;
	}
	
	public static String[] getCurrentDateTime() {
		Calendar cal = Calendar.getInstance();
		
		//get the date
		StringBuilder date = new StringBuilder();
		date.append(cal.get(Calendar.YEAR));
		date.append('-');
		int month = cal.get(Calendar.MONTH) + 1;
		if ( month < 10 ) {
			date.append('0');
			date.append(month);
		} else 
			date.append(month);
		date.append('-');
		int day = cal.get(Calendar.DAY_OF_MONTH);
		if ( day < 10 ) {
			date.append('0');
			date.append(day);
		} else 
			date.append(day);
		
		//get the time
		StringBuilder time = new StringBuilder();
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		if ( hour < 10 ) {
			time.append('0');
			time.append(hour);
		} else 
			time.append(hour);
		time.append(':');
		
		int min  = cal.get(Calendar.MINUTE);
		if ( min < 10 ) {
			time.append('0');
			time.append(min);
		} else
			time.append(min);
		
		return new String[]{date.toString(), time.toString(),
				new String(cal.get(Calendar.DAY_OF_WEEK - 1)+"")};
	}
	
}

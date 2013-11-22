package org.lionsoul.dclock.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.lionsoul.dclock.util.Util;

//import com.webssky.dclock.util.IConstants;

/**
 * database interface for desktop clock.
 * it base on the serializable of the java object.
 * 
 * @author chenxin <chenxin619315@gmail.com>
 */
public class Database implements Serializable {

	private static final long serialVersionUID = -7469326412858191689L;
	
	/**
	 * Element data collection. 
	 */
	private ArrayList<ActionItem> data = null;
	
	/**
	 * instance of the database. 
	 */
	private static Database __instance = null;
	
	/**
	 * return the only quote to the database object. 
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 */
	public static Database createDatabase(String __file) 
					throws IOException, ClassNotFoundException {
		if ( __instance == null ) 
			__instance = loadDatabaseFromFile(__file);
		return __instance;
	}
	
	public static Database getDatabase() {
		return __instance;
	}
	
	private Database() {
		data = new ArrayList<ActionItem>();
	}
	
	
	/**
	 * add an element to the database.
	 * 
	 * @param e
	 * @return boolean
	 */
	public boolean add( ActionItem e ) {
		e.setIndex(data.size());
		return data.add(e);
	}
	
	/**
	 * remove en element from the database.
	 * 
	 * @param e
	 * @return Element
	 */
	public ActionItem remove( ActionItem e ) {
		ActionItem old =  data.remove(e.getIndex());
		/*
		 * update the all the ActionItem's index after e.getIndex(); 
		 */
		ActionItem el = null;
		for ( int j = e.getIndex();
			j < data.size();
			j++) {
			el = data.get(j);
			el.setIndex(j);
		}
		return old;
	}
	
	/**
	 * edit the specified element in the database.
	 * 
	 * @param old
	 * @param e
	 * @return Element
	 */
	public ActionItem replace( ActionItem old, ActionItem e ) {
		return data.set(old.getIndex(), e);
	}
	
	/**
	 * query.
	 * 		-return all the elements int the database.
	 * 
	 * @return Element[]
	 */
	public ActionItem[] query() {
		ActionItem[] arr = new ActionItem[data.size()];
		data.toArray(arr);
		//data.clear();
		return arr;
	}
	
	public ActionItem[] query( HashMap<String, String> map ) {
		/*
		 * id = 12
		 */
		ArrayList<String> keys = new ArrayList<String>();
		if ( map.get("index") != null 
				&& Util.isDigit(map.get("index")) )
			keys.add("index");
		if ( map.get("time") != null ) 
			keys.add("time");
		if ( map.get("desc") != null ) 
			keys.add("desc");
		if ( map.get("cycle") != null )
			keys.add("cycle");
		
		Iterator<ActionItem> it = data.iterator();
		ArrayList<ActionItem> res = new ArrayList<ActionItem>();
		while ( it.hasNext() ) {
			ActionItem e = it.next();
			String _key = null;
			String _val = null;
			int counter = 0;
			for ( int j = 0; j < keys.size(); j++ ) {
				_key = keys.get(j);
				_val = map.get(_key);
				if ( e.get(_key) != null && compare(e.get(_key), _val) )
					counter++;//res.add(e);
			}
			if ( counter == keys.size() )
				res.add(e);
		}
		
		ActionItem[] eles = new ActionItem[res.size()];
		res.toArray(eles);
		res.clear();
		
		return eles;
	}
	
	public int size() {
		return data.size();
	}
	
	
	/**
	 * load database object from data file.
	 * 		-located java.user.dir.
	 * 
	 * @param __file
	 * @throws IOException 
	 * @throws ClassNotFoundException
	 * @return Database 
	 */
	public static Database loadDatabaseFromFile( String __file ) 
			throws IOException, ClassNotFoundException {
		Database db = null;
		FileInputStream is = new FileInputStream(__file);
		ObjectInputStream ois = new ObjectInputStream(is);
		db = ( Database ) ois.readObject();
		is.close();
		ois.close();
		return db;
	}
	
	/**
	 * save the current object to the data file.
	 * 		-located java.user.dir
	 * 
	 * @param __file
	 * @throws FileNotFoundException 
	 * @return boolean
	 */
	public boolean saveDatabaseToFile( String __file ) throws IOException {
		FileOutputStream fos = new FileOutputStream(__file);
		ObjectOutputStream os = new ObjectOutputStream(fos);
		os.writeObject(this);
		fos.close();
		os.close();
		return true;
	} 
	
	public static boolean compare( String str, String cmp ) {
		return str.equalsIgnoreCase(cmp);
	}
	
	/**
	 * format the database file.
	 * 
	 * @param __file
	 * @return boolean
	 */
	public static boolean formatDatabaseFile( String __file ) throws IOException {
		FileOutputStream fos = new FileOutputStream(__file);
		ObjectOutputStream os = new ObjectOutputStream(fos);
		os.writeObject(new Database());
		fos.close();
		os.close();
		return true;
	}
	
/*	public static void main( String[] args  ) {
		try {
			System.out.println(Database.formatDatabaseFile(System.
					getProperty("user.dir")+"/"+IConstants.__DATA_FILE));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/

}

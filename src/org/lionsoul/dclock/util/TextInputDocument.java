package org.lionsoul.dclock.util;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * limit the input length
 * 
 * @author chenxin <chenxin619315@gmail.com> 
 */
public class TextInputDocument extends PlainDocument {
	
	private static final long serialVersionUID = 8509683331492704023L;
	private int MaxLength;
	
	public TextInputDocument( int MaxLength ) {
		this.MaxLength = MaxLength;
	}
	
	public TextInputDocument() {
		this(10);
	}
	
	@Override
	public void insertString(int offset, String str, AttributeSet a) {
		if ( getLength() + str.length() <= MaxLength
				&& isValidString(str)) {
			try {
				super.insertString(offset, str, a);
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		} 
	}
	
	private boolean isValidString( String str ) {
		if ( str == null ) return false;
		for ( int j = 0; j < str.length(); j++ ) {
			char c = str.charAt(j);
			if ( c != ':' && ( c < '0' || c > '9') )
				return false;
		}
		return true;
	}

}

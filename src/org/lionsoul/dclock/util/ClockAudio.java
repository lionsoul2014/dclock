package org.lionsoul.dclock.util;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.MalformedURLException;
import java.net.URL;

import org.lionsoul.dclock.DClock;



/**
 * sound control class.
 * 
 * @author ChenXin <chenxin619315@gmail.com>
 */
public class ClockAudio {
	
	private AudioClip clockClip = null;
	
	public ClockAudio() {
		try {
			clockClip = Applet.newAudioClip(new URL("file:////"+DClock.JAR_HOME+"/media/"+Config.CLOCK_MEDIA_FILE));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	public void stopClockAudio() {
		if ( clockClip == null ) return;
		//thread.interrupt();
		clockClip.stop();
	}
	
	public void playClockAudio() {
		if ( clockClip == null ) return; 
		clockClip.loop();
	}
}

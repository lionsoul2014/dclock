package org.lionsoul.dclock;

import org.lionsoul.dclock.component.JListModel;
import org.lionsoul.dclock.model.ActionItem;
import org.lionsoul.dclock.util.Util;


/**
 * time listener task.
 * 
 * @author chenxin <chenxin619315@gmail.com>
 */
public class TimeListenerTask implements Runnable {
	
	public static final int RUNNING = 1;
	public static final int STOPED = 0;
	
	private int threadstate = RUNNING;
	
	public TimeListenerTask() {}

	@Override
	public void run() {
		ActionItem item = null;
		while ( true ) {
			try {
				if ( getTaskState() == STOPED )
					break;
				String[] args = Util.getCurrentDateTime();
				if ( JListModel.getInstance().getNextItem() != null ) { 
					//System.out.print("time:"+args[1]);
					//System.out.println(", "+(ActionItem.strCompare(JListModel.getInstance()
							//.getNextItem().getTime(), args[1]) == 0));
					if ( ActionItem.strCompare((item = JListModel.getInstance()
							.getNextItem()).getTime(), args[1]) == 0 ) {
						DClock.threadMaster.execute(new BellJDialog(item));
						//generate the next item
						JListModel.getInstance().setNextItem(Util.findNextItem(item));
					} 
				}
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				//e.printStackTrace();
				break;
			}
		}
	}
	
	public synchronized int getTaskState() {
		return threadstate;
	}
	
	public synchronized void setTaskState( int state ) {
		threadstate = state;
	}

}

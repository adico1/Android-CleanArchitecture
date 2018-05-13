package com.tigaomobile.lockinapp.lockscreen.presentation.view.events;


import java.util.ArrayList;

public class EventDispatcher<E extends IEventData, T extends  IEventListener<E>> {

	private final ArrayList<T> listeners = new ArrayList<>();
//    List list = Collections.synchronizedList(yourList);

	private boolean isLocked = false;

	public synchronized void lock()
			throws InterruptedException {
		while(isLocked){
			wait();
		}
		isLocked = true;
	}

	public synchronized void unlock(){
		isLocked = false;
		notify();
	}

	public void addEventListener(T listener)  {
		synchronized(listeners) {
			listeners.add(listener);
		}
	}

	public void removeEventListener(T listener)   {
		synchronized(listeners) {
			listeners.remove(listener);
		}
	}

	public void notify(IEventData data) {
		ArrayList<T> l = (ArrayList<T>) this.listeners.clone();

		// loop on all members of l and call handleEvent(data) on each
		for(T event: l) {
            event.handleEvent((E) data);
		}

	}
}

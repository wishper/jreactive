package jreactive;

import java.util.HashSet;
import java.util.Set;

public class Dependency {
	private Set<Runnable> subscribers = new HashSet<>();
	public void depend() {
		Runnable target = JReactivity.getTarget();
		if (target != null && !this.subscribers.contains(target)) {
			this.subscribers.add(target);
		}
	}
	public void notifySubscribers() {
		for (Runnable mRunnable : subscribers) {
			JReactivity.setTarget(mRunnable);
			mRunnable.run();
		}
	}

}
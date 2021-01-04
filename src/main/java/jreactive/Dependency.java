package jreactive;

import java.util.HashSet;
import java.util.Set;

public class Dependency {
	private Set<Runnable> subscribers = new HashSet<>();
	public void depend() {
		Runnable target = Reactivity.getTarget();
		if (target != null && !this.subscribers.contains(target)) {
			this.subscribers.add(target);
		}
	}
	public void notifySubscribers() {
		for (Runnable mRunnable : subscribers) {
			Reactivity.setTarget(mRunnable);
			mRunnable.run();
		}
	}

}
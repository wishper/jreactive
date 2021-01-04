package jreactive;

public class Reactivity {
	private static Runnable target;

	public static Runnable getTarget() {
		return target;
	}

	public static void setTarget(Runnable current) {
		if(Reactivity.target != current)
			Reactivity.target = current;
	}

	public static void watch(Runnable f) {
		setTarget(f);
		f.run();
		setTarget(null);
	}
}

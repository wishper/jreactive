package jreactive;

public class JReactivity {
	private static Runnable target;

	public static Runnable getTarget() {
		return target;
	}

	public static void setTarget(Runnable current) {
		if(JReactivity.target != current)
			JReactivity.target = current;
	}

	public static void watch(Runnable f) {
		setTarget(f);
		f.run();
		setTarget(null);
	}
}

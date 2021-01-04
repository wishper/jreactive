package jreactive.util;


public class Util {
	public static boolean allNull(Object ... pVals) {
		for (Object l : pVals) {
			if (l != null)
				return false;
		}
		return true;
	}

	public static boolean allNotNull(Object ... pVals) {
		for (Object l : pVals) {
			if (l == null)
				return false;
		}
		return true;
	}
}

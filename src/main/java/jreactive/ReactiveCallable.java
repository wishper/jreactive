package jreactive;

import java.util.concurrent.Callable;

public class ReactiveCallable<T> implements Callable<T> {
	private Dependency dep = new Dependency();
	private Callable<T> callable;

	public ReactiveCallable(Callable<T> c) {
		callable = c;
	}

	@Override
	public T call() throws Exception {
		dep.depend();
		T ret = callable.call();
		//dep.notifySubscribers();
		return ret;
	}

}

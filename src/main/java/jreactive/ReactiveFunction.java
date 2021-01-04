package jreactive;

import java.util.function.Function;

public class ReactiveFunction<T,R> implements Function<T,R> {
	private Dependency dep = new Dependency();
	private Function<T,R> function;

	public ReactiveFunction(Function<T,R> f) {
		function = f;
	}

	@Override
	public R apply(T t) {
		dep.depend();
		R ret = function.apply(t);
		//dep.notifySubscribers();
		return ret;
	}

}

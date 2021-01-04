package jreactive;

public class ReactiveProperty<T> {
	public ReactiveProperty() {
	}

	public ReactiveProperty(T initialValue) {
		prop = initialValue;
	}

	protected Dependency dep = new Dependency();
	protected T prop;

	public T get() {
		dep.depend();
		return prop;
	}

	public void set(T p) {
		prop = p;
		dep.notifySubscribers();
	}

}

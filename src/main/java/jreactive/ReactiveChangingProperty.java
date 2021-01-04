package jreactive;

public class ReactiveChangingProperty<T> extends ReactiveProperty<T>{
	public ReactiveChangingProperty() {
		super();
	}

	public ReactiveChangingProperty(T initialValue) {
		super(initialValue);
	}

	@Override
	public void set(T p) {
		boolean notify = false;
		if( (p==null ^ prop==null) || (prop!=null && !prop.equals(p)) )
			notify = true;
		prop = p;
		if(notify)
			dep.notifySubscribers();
	}

}

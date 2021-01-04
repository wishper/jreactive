package jreactive;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.concurrent.Callable;
import jreactive.ReactiveCallable;
import jreactive.ReactiveProperty;
import jreactive.Reactivity;
import jreactive.util.Util;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

public class TestReactivity {
	@Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

	Runnable action = new Runnable() {
		@Override
		public void run() {
			ReactiveTestPojo mTestPojo = getTestPojo();
			if(mTestPojo == null)
				return;
			if(Util.allNotNull(mTestPojo.getName(), mTestPojo.getSurname(), mTestPojo.getAge())){
				String.format("Ciao %s %s (%d)", getTestPojo().getName(), getTestPojo().getSurname(), getTestPojo().getAge());
			}
		}
	};
	Runnable mockedAction = spy(action);

	@Test
	public void testReactivePojo() throws Exception {
		Reactivity.watch(mockedAction);
		verify(mockedAction, times(1)).run(); // one for the watch

		ReactiveTestPojo mTP = new ReactiveTestPojo();
		setTestPojo(mTP);
		verify(mockedAction, times(1+1)).run(); // one for the watch, one for each set
		getTestPojo().setName("Alessandro");
		verify(mockedAction, times(1+2)).run(); // one for the watch, one for each set
		getTestPojo().setSurname("Carraro");
		verify(mockedAction, times(1+3)).run(); // one for the watch, one for each set
		getTestPojo().setAge(44);
		verify(mockedAction, times(1+4)).run(); // one for the watch, one for each set

		getTestPojo().setName("Davide");
		verify(mockedAction, times(1+5)).run(); // one for the watch, one for each set
		getTestPojo().setAge(10);
		verify(mockedAction, times(1+6)).run(); // one for the watch, one for each set
		getTestPojo().setName("Sara");
		verify(mockedAction, times(1+7)).run(); // one for the watch, one for each set
		getTestPojo().setAge(12);
		verify(mockedAction, times(1+8)).run(); // one for the watch, one for each set

		ReactiveTestPojo dm = new ReactiveTestPojo();
		verify(mockedAction, times(1+8)).run(); // no more calls, since this is another pojo

		dm.setName("Donatella");
		verify(mockedAction, times(1+8)).run(); // no more calls, since this is another pojo
		dm.setSurname("Marin");
		verify(mockedAction, times(1+8)).run(); // no more calls, since this is another pojo
		dm.setAge(45);
		verify(mockedAction, times(1+8)).run(); // no more calls, since this is another pojo
		setTestPojo(dm);
		verify(mockedAction, times(1+9)).run(); // we set the pojo, so a new call is made

		Callable<String> f = spy(new ReactiveCallable<>(()-> String.format("Ciao RF %s %s (%d)", getTestPojo().getName(), getTestPojo().getSurname(), getTestPojo().getAge())));
		Reactivity.watch(()-> {
			try {
				f.call();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		verify(f, times(1)).call();
		assertEquals("Ciao RF Donatella Marin (45)", f.call());
		verify(f, times(2)).call();
		dm.setName("Donato");
		verify(f, times(3)).call();
		assertEquals("Ciao RF Donato Marin (45)", f.call());
		verify(f, times(4)).call();
	}

	ReactiveProperty<ReactiveTestPojo> tpp = new ReactiveProperty<>();

	public ReactiveTestPojo getTestPojo() {
		return tpp.get();
	}

	public void setTestPojo(ReactiveTestPojo tp) {
		tpp.set(tp);
	}

}

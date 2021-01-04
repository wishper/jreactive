package jreactive;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import jreactive.ReactiveFunction;
import jreactive.ReactiveProperty;
import jreactive.Reactivity;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;


public class SimpleTest {

	@Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

	ReactiveProperty<Integer> property = new ReactiveProperty<>(1);
	Runnable action = new Runnable() {
		@Override
		public void run() {
			property.get();
		}
	};
	Runnable mockedAction = spy(action);

	@Test public void testReactiveProperty(){
		Reactivity.watch(mockedAction);
		property.set(2);
		verify(mockedAction, times(1+1)).run(); // one for the watch, one for the set
		property.set(3);
		verify(mockedAction, times(1+2)).run(); // one for the watch, one for each set
	}

	@Test public void testReactivePropertyWithNoWatcer(){
		property.set(2);
		verify(mockedAction, never()).run();
	}

	@Test public void testReactivePropertyWithMultipleWatcers(){
		Reactivity.watch(mockedAction);
		verify(mockedAction, times(1)).run(); // one for the watch
		Reactivity.watch(mockedAction);
		verify(mockedAction, times(2)).run(); // calling the watch twice, calls the function twice
		property.set(2);
		verify(mockedAction, times(2+1)).run(); // one for the watch, one for each set
		property.set(3);
		verify(mockedAction, times(2+2)).run(); // one for the watch, one for each set
	}

	@Test public void testMultipleReactivePropertyWithMultipleWatcers(){
		ReactiveProperty<String> rp1 = new ReactiveProperty<>("Hello");
		ReactiveProperty<String> rp2 = new ReactiveProperty<>("world");
		Runnable mockedAction = spy(new Runnable() {
			@Override
			public void run() {
				@SuppressWarnings("unused")
				String result = rp1.get() + " " + rp2.get();
			}
		});
		Reactivity.watch(mockedAction);
		verify(mockedAction, times(1)).run(); // one for the watch
		rp1.set("Goodbye");
		verify(mockedAction, times(1+1)).run(); // one for the watch, one for each set
		rp2.set("everyone");
		verify(mockedAction, times(1+2)).run(); // one for the watch, one for each set
	}

	@Test public void testFunctionWithTwoParameters() {
		ReactiveProperty<String> rp1 = new ReactiveProperty<>("Hello");
		ReactiveProperty<String> rp2 = new ReactiveProperty<>("world");

		ReactiveFunction<Void, String> rf = new ReactiveFunction<>((p)->rp1.get() + " " + rp2.get());
		assertEquals("Hello world", rf.apply(null));

		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				rf.apply(null);
			}
		};

		Runnable mockedAction = spy(runnable);
		Reactivity.watch(mockedAction);
		verify(mockedAction, times(1)).run(); // one for the watch
		rp1.set("Goodbye");
		verify(mockedAction, times(1+1)).run(); // one for the watch, one for each set
		rp2.set("everyone");
		verify(mockedAction, times(1+2)).run(); // one for the watch, one for each set
		assertEquals("Goodbye everyone", rf.apply(null));
	}

}

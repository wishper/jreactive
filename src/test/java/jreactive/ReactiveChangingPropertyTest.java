package jreactive;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

public class ReactiveChangingPropertyTest {
	@Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

	ReactiveProperty<String> property = new ReactiveProperty<>();
	Runnable action = new Runnable() {
		@Override
		public void run() {
			property.get();
		}
	};
	Runnable mockedAction = spy(action);

	@Test public void nullPropertyDoesNotFireOnSetNull() {
		property = new ReactiveChangingProperty<>();//init with null
		assertNull(property.get());
		JReactivity.watch(mockedAction);
		verify(mockedAction, times(1)).run();
		property.set(null);
		assertNull(property.get());
		verify(mockedAction, times(1)).run();
	}

	@Test public void nullPropertyDoesFireOnSetNotNull() {
		property = new ReactiveChangingProperty<>();//init with null
		assertNull(property.get());
		JReactivity.watch(mockedAction);
		verify(mockedAction, times(1)).run();
		property.set("a");
		assertEquals("a", property.get());
		verify(mockedAction, times(2)).run();
	}

	@Test public void notNullPropertyDoesFireOnSetNull() {
		property = new ReactiveChangingProperty<>("a");//init with not null
		assertEquals("a", property.get());
		JReactivity.watch(mockedAction);
		verify(mockedAction, times(1)).run();
		property.set(null);
		assertNull(property.get());
		verify(mockedAction, times(2)).run();
	}

	@Test public void notNullPropertyDoesNotFireOnEqualSetNotNull() {
		property = new ReactiveChangingProperty<>("a");//init with not null
		assertEquals("a", property.get());
		JReactivity.watch(mockedAction);
		verify(mockedAction, times(1)).run();
		property.set("a");
		assertEquals("a", property.get());
		verify(mockedAction, times(1)).run();
	}

	@Test public void notNullPropertyDoesFireOnDifferentSetNotNull() {
		property = new ReactiveChangingProperty<>("a");//init with not null
		assertEquals("a", property.get());
		JReactivity.watch(mockedAction);
		verify(mockedAction, times(1)).run();
		property.set("b");
		assertEquals("b", property.get());
		verify(mockedAction, times(2)).run();
	}
}

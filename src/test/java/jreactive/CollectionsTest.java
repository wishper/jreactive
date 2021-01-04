package jreactive;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.concurrent.Callable;
import jreactive.ReactiveCallable;
import jreactive.ReactiveFunction;
import jreactive.ReactiveList;
import jreactive.Reactivity;
import org.junit.Test;


public class CollectionsTest {

	@Test public void testSumOfListProperty(){
		//first we create a list, and a reactive function doing the sum of the list
		ReactiveList<Integer> list = new ReactiveList<>();
		ReactiveFunction<List<Integer>, Integer> sumOfList = new ReactiveFunction<>((l) -> {
			int accumulator = 0;
			for (Integer i : l)
				accumulator += i;
			return accumulator;
		});

		list.add(1);
		assertEquals(1, (int)sumOfList.apply(list));
		list.add(2);
		assertEquals(3, (int)sumOfList.apply(list)); // 1+2 = 3

		Runnable mockedAction = spy(new Runnable() {
			@SuppressWarnings("unused")
			@Override
			public void run() {
				int result = sumOfList.apply(list);
			}
		});
		Reactivity.watch(mockedAction);
		verify(mockedAction, times(1)).run(); // one for the watch
		list.add(3);
		assertEquals(6, (int)sumOfList.apply(list)); // 1+2+3 =6
		verify(mockedAction, times(1+1)).run(); // one for the watch, one for the add
	}

	@Test
	public void testReactivePojoList() throws Exception {
		ReactiveList<ReactiveTestPojo> listOfPojo = new ReactiveList<>();
		Callable<Integer> reactiveAdder = new ReactiveCallable<>(()-> {
			int i = 0;
			for (ReactiveTestPojo addend : listOfPojo) {
				i+= addend.getAge();
			}
			return i;
		});

		Reactivity.watch(()-> {try {
			System.out.println(reactiveAdder.call());
		} catch (Exception e) {
			e.printStackTrace();
		}});

		ReactiveTestPojo mAlex = new ReactiveTestPojo("Alessandro", "Carraro", 42);
		assertEquals((Integer)0, reactiveAdder.call());

		listOfPojo.add(mAlex);
		assertEquals((Integer)42, reactiveAdder.call());

		listOfPojo.add(new ReactiveTestPojo("Sara", "Carraro", 12));
		assertEquals((Integer)54, reactiveAdder.call());

		listOfPojo.add(new ReactiveTestPojo("Davide", "Carraro", 10));
		assertEquals((Integer)64, reactiveAdder.call());

		listOfPojo.get(0).setAge(43);
		assertEquals((Integer)65, reactiveAdder.call());

		listOfPojo.remove(mAlex);
		assertEquals((Integer)22, reactiveAdder.call());
	}
}

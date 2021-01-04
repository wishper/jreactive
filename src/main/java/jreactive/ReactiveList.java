package jreactive;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class ReactiveList<E> implements List<E> {
	public ReactiveList() {
		list = new LinkedList<>();
	}
	public ReactiveList(List<E> initialValue) {
		list = initialValue;
	}

	private Dependency dep = new Dependency();
	private List<E> list;
	//
	//	public T get() {
	//		dep.depend();
	//		return list;
	//	}
	//
	//	public void set(T p) {
	//		list = p;
	//		dep.notifySubscribers();
	//	}
	//
	@Override
	public void forEach(Consumer<? super E> pAction) {
		dep.depend();
		list.forEach(pAction);
	}
	@Override
	public int size() {
		dep.depend();
		return list.size();
	}
	@Override
	public boolean isEmpty() {
		dep.depend();
		return list.isEmpty();
	}
	@Override
	public boolean contains(Object pO) {
		dep.depend();
		return list.contains(pO);
	}
	@Override
	public Iterator<E> iterator() {
		dep.depend();
		return list.iterator();
	}
	@Override
	public Object[] toArray() {
		dep.depend();
		return list.toArray();
	}
	@Override
	public <T> T[] toArray(T[] pA) {
		dep.depend();
		return list.toArray(pA);
	}
	@Override
	public boolean add(E pE) {
		boolean mAdd = list.add(pE);
		dep.notifySubscribers();
		return mAdd;
	}
	@Override
	public boolean remove(Object pO) {
		boolean mRemove = list.remove(pO);
		dep.notifySubscribers();
		return mRemove;
	}
	@Override
	public boolean containsAll(Collection<?> pC) {
		dep.depend();
		return list.containsAll(pC);
	}
	@Override
	public boolean addAll(Collection<? extends E> pC) {
		boolean mAddAll = list.addAll(pC);
		dep.notifySubscribers();
		return mAddAll;
	}
	@Override
	public boolean addAll(int pIndex, Collection<? extends E> pC) {
		boolean mAddAll = list.addAll(pIndex, pC);
		dep.notifySubscribers();
		return mAddAll;
	}
	@Override
	public boolean removeAll(Collection<?> pC) {
		boolean mRemoveAll = list.removeAll(pC);
		dep.notifySubscribers();
		return mRemoveAll;
	}
	@Override
	public boolean retainAll(Collection<?> pC) {
		boolean mRetainAll = list.retainAll(pC);
		dep.notifySubscribers();
		return mRetainAll;
	}
	@Override
	public void replaceAll(UnaryOperator<E> pOperator) {
		list.replaceAll(pOperator);
		dep.notifySubscribers();
	}
	@Override
	public boolean removeIf(Predicate<? super E> pFilter) {
		boolean mRemoveIf = list.removeIf(pFilter);
		dep.notifySubscribers();
		return mRemoveIf;
	}
	@Override
	public void sort(Comparator<? super E> pC) {
		list.sort(pC);
		dep.notifySubscribers();
	}
	@Override
	public void clear() {
		list.clear();
		dep.notifySubscribers();
	}
	@Override
	public boolean equals(Object pO) {
		dep.depend();
		return list.equals(pO);
	}
	@Override
	public int hashCode() {
		dep.depend();
		return list.hashCode();
	}
	@Override
	public E get(int pIndex) {
		dep.depend();
		return list.get(pIndex);
	}
	@Override
	public E set(int pIndex, E pElement) {
		E mSet = list.set(pIndex, pElement);
		dep.notifySubscribers();
		return mSet;
	}
	@Override
	public void add(int pIndex, E pElement) {
		list.add(pIndex, pElement);
		dep.notifySubscribers();
	}
	@Override
	public Stream<E> stream() {
		dep.depend();
		return list.stream();//TODO
	}
	@Override
	public E remove(int pIndex) {
		E mRemove = list.remove(pIndex);
		dep.notifySubscribers();
		return mRemove;
	}
	@Override
	public Stream<E> parallelStream() {
		dep.depend();
		return list.parallelStream();
	}
	@Override
	public int indexOf(Object pO) {
		dep.depend();
		return list.indexOf(pO);
	}
	@Override
	public int lastIndexOf(Object pO) {
		dep.depend();
		return list.lastIndexOf(pO);
	}
	@Override
	public ListIterator<E> listIterator() {
		dep.depend();
		return list.listIterator();
	}
	@Override
	public ListIterator<E> listIterator(int pIndex) {
		dep.depend();
		return list.listIterator(pIndex);
	}
	@Override
	public List<E> subList(int pFromIndex, int pToIndex) {
		return new ReactiveList<>(subList(pFromIndex, pToIndex));
	}
	@Override
	public Spliterator<E> spliterator() {
		dep.depend();
		return list.spliterator();
	}

}

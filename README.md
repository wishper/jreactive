# jreactive
A simple reactivity framework for java inspired by Vue.js and the article [How Reactivity works in Vue.js](https://deepsource.io/blog/reactivity-in-vue/)

Refer to unit tests for detailed usage.

## Usage
To make a property reactive, you have to define a property using the class `ReactiveProperty<Type>`.
For example, to define a reactive int/Integer, you write:

```
ReactiveProperty<Integer> property = new ReactiveProperty<>(initialValue);
```
Where `initialValue` is an int or an Integer.
You can omit the initialValue, in that case the initial value is considered to be `null`

You can then define javaBean style properties, using the `get` and `set` methods to later the property:

```
	public int getProperty() {
		return property.get();
	}

	public void setProperty(int p) {
		property.set(p);
	}
```

You can then *watch* for changes using `JReactivity.watch(Runnable)` : the runnable passed will be "executed" once to record the dependencies, then each a reactive object changes, the runnable will be re-executed again.

You can compose reactive hierarchies by declaring each node of the tree as a ReactiveProperty, since a generic Object can be made reactive. You can mix and match reactive and non-reactive code inside the runnable, however the evaluation will be triggered only when a reactive element change.

## Other reactive classes:
 Beside `ReactiveProperty`, there are other reactive objects:
 * `ReactiveList`: a reactive object where the reactivity triggers when elements are inserted, removed, replaced and so on. The element itself can be a reactive object or any other object, but in the latter case the object is treated as constant for the purpose of the reactivity
 * `ReactiveFunction` and `ReactiveCallable` are functions that work at the same time as watchers and as items to be watched. They can be composed together.
 * `ReactiveChangingProperty` triggers only if the new value is different (using `equals`) from the previous 
 
## Demo
in jreactive.swing package there is a swing Demo to show the features and intended usages. The demo create a form where the first two fields are editable, the third and the fourth are computed as the concatenation of the first two, the fith show the cumulative length. On cumulative length > 10 the fields change the background color to red.
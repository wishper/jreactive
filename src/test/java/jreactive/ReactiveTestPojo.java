package jreactive;

class ReactiveTestPojo{
	public ReactiveTestPojo() {
	}
	public ReactiveTestPojo(String name, String surname, int age) {
		setName(name);
		setSurname(surname);
		setAge(age);
	}

	private ReactiveProperty<String> nameProperty = new ReactiveProperty<>();
	private ReactiveProperty<String> surnameProperty = new ReactiveProperty<>();
	private ReactiveProperty<Integer> ageProperty = new ReactiveProperty<>();

	public void setName(String param){
		nameProperty.set(param);
	}

	public String getName() {
		return nameProperty.get();
	}

	public void setSurname(String param){
		surnameProperty.set(param);
	}

	public String getSurname() {
		return surnameProperty.get();
	}

	public Integer getAge() {
		return ageProperty.get();
	}

	public void setAge(int age) {
		ageProperty.set(age);
	}
}
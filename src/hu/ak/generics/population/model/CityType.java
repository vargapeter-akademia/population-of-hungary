package hu.ak.generics.population.model;

public class CityType {
	
	public static final String CREATE_TABLE =
			"""
			create table city_type(
				type_id int primary key auto_increment,
				name varchar(255) not null unique
			);
			""";

	private int id;
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

package hu.ak.generics.population.model;

public class County {
	
	public static final String CREATE_SQL =
			"""
			create table county (
				county_id int primary key auto_increment,
				name varchar(255) not null unique,
				capital_id int not null,
				foreign key (capital_id) references city(city_id)
			);	
			""";

	private int id;
	private String name;
	private City capital;

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

	public City getCapital() {
		return capital;
	}

	public void setCapital(City capital) {
		this.capital = capital;
	}

}

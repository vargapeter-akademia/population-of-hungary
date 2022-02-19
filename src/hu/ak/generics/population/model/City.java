package hu.ak.generics.population.model;

public class City {
	
	public static final String CREATE_SQL = 
			"""
			create table city (
				city_id int primary key auto_increment,
				name varchar(255) not null unique,
				territory int not null,
				population int not null,
				county_id int not null,
				type_id int not null,
				foreign key (type_id) references city_type(type_id)
			);
			""";
	
	public static final String[] FOREIGN_KEY_SQLS = {
			"alter table add foreign key (county_id) references county(county_id);",
	};

	private int id;
	private String name;
	private int territory;
	private int population;
	private County county;
	private CityType type;

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

	public int getTerritory() {
		return territory;
	}

	public void setTerritory(int territory) {
		this.territory = territory;
	}

	public int getPopulation() {
		return population;
	}

	public void setPopulation(int population) {
		this.population = population;
	}

	public County getCounty() {
		return county;
	}

	public void setCounty(County county) {
		this.county = county;
	}

	public CityType getType() {
		return type;
	}

	public void setType(CityType type) {
		this.type = type;
	}

}

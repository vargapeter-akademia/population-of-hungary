package hu.ak.generics.population;

// Plain Old Java Object - Pojo (maven hasonló kifejezés: Mojo)
// Java Beans
// DTO

public class RawCity {

	private String name;
	private String type;
	private String county;
	private int territory;
	private int population;

	public RawCity() {
		
	}
	
	public RawCity(String name, String type, String county, int territory, int population) {
		this.name = name;
		this.type = type;
		this.county = county;
		this.territory = territory;
		this.population = population;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
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

}

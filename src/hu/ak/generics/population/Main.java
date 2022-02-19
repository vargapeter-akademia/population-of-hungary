package hu.ak.generics.population;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import hu.ak.generics.population.model.City;
import hu.ak.generics.population.model.CityType;
import hu.ak.generics.population.model.County;

public class Main {
	
	// A teljes adatbázis legyen egy különálló sémában
	// Mi van ha egy olyan adatbázisra hivatkozok, ami még nem létezik?
	// Ha nem adjuk meg az sémát az URL-ben, akkor hivatkoznunk kell az SQL kifejezésekben a sémára
	
	private static final String URL = "jdbc:mysql://localhost:3306";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "admin";
	
	private static final int CITY_LIMIT = 500;
	
	public static void main(String[] args) {
		
//		create table population.city (..)
		
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
			
			try (Statement stm = connection.createStatement()) {
				stm.execute("drop database if exists population;");
				stm.execute("create database population;");
				System.out.println("population database created");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
			
		try (Connection connection = DriverManager.getConnection(URL + "/population", USERNAME, PASSWORD)) {
			
			List<RawCity> rawCities = new ArrayList<>(3200);
			
			File csvFile = new File("cities_of_hungary.csv");
			try (Scanner scanner = new Scanner(csvFile)) {
				
				scanner.nextLine(); // Skipping heading
//				int cityCount = 0;
				while(scanner.hasNextLine()) {
					String rawData = scanner.nextLine();
					String[] data = rawData.split(",");
					
					String cityName = data[0];
					String type = data[2];
					String county = data[3];
					int territory = Integer.parseInt(data[6]);
					int population = Integer.parseInt(data[7]);
					
					RawCity rawCity = new RawCity(cityName, type, county, territory, population);
					rawCities.add(rawCity);
					
//					cityCount++;
				}
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			Map<String, County> counties = new HashMap<>();
			Map<String, City> cities = new HashMap<>();
			Map<String, CityType> types = new HashMap<>();
			Map<String, String> capitalMapping = new HashMap<>();
			for (RawCity rawCity : rawCities) {
				
				City city = new City();
				city.setName(rawCity.getName());
				city.setPopulation(rawCity.getPopulation());
				city.setTerritory(rawCity.getTerritory());
				
				city.setType(null);
				city.setCounty(null);
				
				cities.put(city.getName(), city);
				
				if (!counties.containsKey(rawCity.getCounty())) {
					County county = new County();
					county.setName(rawCity.getCounty());
					
					county.setCapital(null);
					counties.put(county.getName(), county);
				}
				
				if (!types.containsKey(rawCity.getType())) {
					CityType type = new CityType();
					type.setName(rawCity.getType());
					
					types.put(type.getName(), type);
				}
				
				if (rawCity.getType().equals("megyeszékhely")) {
					capitalMapping.put(rawCity.getCounty(), rawCity.getName());
				}
				
			}
			
			for (RawCity rawCity : rawCities) {
				City city = cities.get(rawCity.getName());
				city.setCounty(counties.get(rawCity.getCounty()));
				city.setType(types.get(rawCity.getType()));
			}
			
			for (County county : counties.values()) {
				City capital = cities.get(capitalMapping.get(county.getName()));
				county.setCapital(capital);
			}
			
			// Kezdjük a CityType létrehozását
			
			try (Statement stm = connection.createStatement()) {
				stm.execute(CityType.CREATE_TABLE);
				stm.execute(City.CREATE_SQL);
				stm.execute(County.CREATE_SQL);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	

}

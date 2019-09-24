package com.hms.MongoMagic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.hms.MongoMagic.dao.*;
import com.hms.MongoMagic.model.Customer;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

@SpringBootApplication
public class MongoMagicApplication implements CommandLineRunner {

	@Autowired
	private CustomerRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(MongoMagicApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		repository.deleteAll();

		// save a couple of customers
		
		repository.save(new Customer("Alice", "Smith"));

		repository.save(new Customer("Bob", "Smith"));

		// fetch all customers
		System.out.println("Customers found with findAll():");
		System.out.println("-------------------------------");
		for (Customer customer : repository.findAll()) {
			System.out.println(customer);
		}
		System.out.println();

		// fetch an individual customer
		System.out.println("Customer found with findByFirstName('Alice'):");
		System.out.println("--------------------------------");
		System.out.println(repository.findByFirstName("Alice"));
		//repository.delete(repository.findByFirstName("Alice"));
		

		System.out.println("Customers found with findByLastName('Smith'):");
		System.out.println("--------------------------------");
		for (Customer customer : repository.findByLastName("Smith")) {
			System.out.println(customer);
		}
		
		
		// Mongo Based
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		
		DB database = mongoClient.getDB("test");
		database.getCollectionNames().forEach(System.out::println);
		DBCollection collection = database.getCollection("customer");
		BasicDBObject document = new BasicDBObject();
		document.put("firstName", "Shubham");
		document.put("lastName", "Godbole");
		collection.insert(document);
		
		String json = "{'firstName':'Ravi', 'lastName':'Susarla'}";
		DBObject dbObject = (DBObject)JSON.parse(json);
		collection.insert(dbObject);

	
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("lastName", "Smith");
		//DBCursor cursor = collection.find(searchQuery);
		DBCursor cursor = collection.find();
		while (cursor.hasNext()) {
		    System.out.println(cursor.next());
		}
		

		
		mongoClient.close();

	}

}
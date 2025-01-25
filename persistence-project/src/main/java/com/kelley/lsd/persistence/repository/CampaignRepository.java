package com.kelley.lsd.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.kelley.lsd.persistence.model.Campaign;

import jakarta.transaction.Transactional;

public interface CampaignRepository extends CrudRepository<Campaign, Long> {
	
	// Find campaigns by their code.
	Optional<Campaign> findByCodeEquals(String code);
	
	// Find number of campaigns with a particular name.
	int countByName(String name);
	
	// Find campaigns containing a certain substring in their name
	Iterable<Campaign> findByNameContaining(String substring);
	
	/*
	 * Some other subject keywords supported include:
	 * existsBy
	 * deleteBy
	 * queryBy
	 * 
	 * Subject can include result-limiting keywords such as:
	 * int countDistinctByName(String name);
	 * 
	 */
	
	// Find Campaigns whose names start with a value using 'StartingWith' operator
	Iterable<Campaign> findByNameStartingWith(String name);
	
	/*
	 * Other operators:
	 * EndingWith
	 * Containing
	 * Like
	 */
	
	/*
	 * Distinct keyword removes duplicates.
	 * In this example, Campaign has a one-to-many relationship with Tasks.
	 * The following query, without distinct, could return multiple duplicate campaigns.
	 */
	Iterable<Campaign> findDistinctByTasksNameContaining(String taskName);
	
	/*
	 * Delete methods
	 * All custom delete methods need to be executed within a transaction (@Transactional) 
	 */
	
	// Can use Long to return the number of entities deleted, otherwise void.
	@Transactional
	Long deleteByNameContaining(String name);
	
	// "remove" keyword can also be used instead.
	@Transactional
	void removeByNameContaining(String name);
	
	
	/*
	 * CUSTOM QUERIES
	 */
	
	// The following is written with JPQL syntax. For now (for simplicity) we hardcoded the parameters in our query.
	@Query("select c from Campaign c where c.name='Campaign 3' and c.description='About Campaign 3'")
	List<Campaign> findWithNameAndDescription();
	
	// Unlike derived methods, the method name is unimportant when using @Query
	
	// Need to be careful about return type. For example, if we used Optional and the query returned multiple entities, the query would fail.
	
	// @Query accepts JPQL by defaut unless otherwised specified.
	// Main difference between JPQL and SQL is JPQL queries are written based on entity model rather than the database table.
	
	/*
	 * select c = select campaign instance(s)
	 * from Campaign c = from Campaign entity class
	 * where c.name = where the instance's name value
	 * where c.description = where the instance's description value
	 */
	
	// Another example: A query to select only the name of a Campaign whose code equals a given value
	@Query(value = "select c.name from Campaign as c where c.code='NEW1'")
	Optional<String> findNameByCode();
	
	// Notice return type is Optional<String> as we are only selecting the name
	
	/*
	 * Use nativeQuery attribute within @Query to write native SQL queries instead of JPQL
	 */
	// Example:
	
	@Query(nativeQuery=true, value="SELECT * FROM campaign limit 1")
	Campaign findSingleCampaign();
	
}

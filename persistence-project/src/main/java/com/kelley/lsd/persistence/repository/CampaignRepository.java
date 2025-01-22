package com.kelley.lsd.persistence.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.kelley.lsd.persistence.model.Campaign;

public interface CampaignRepository extends CrudRepository<Campaign, Long> {
	
	// Find campaigns by their code.
	Optional<Campaign> findByCodeEquals(String code);
	
	// Find number of campaigns with a particular name.
	int countByName(String name);
	
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
}

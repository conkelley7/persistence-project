package com.kelley.lsd.persistence.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.kelley.lsd.persistence.model.Campaign;

/*
 * @DataJpaTest invokes several Spring Boot auto-configurations, including enabling scanning of
 * Entity classes and configuring Spring Data JPA repositories.
 * 
 * By default, all tests decorated with this annotation become transactional.
 * The transaction rolls back at the end of each test so as not to affect the initial database state
 * of other tests.
 */
@DataJpaTest
public class CampaignRepositoryIntegrationTest {
	@Autowired
	CampaignRepository campaignRepository;
	
	/*
	 * Can be used to populate database with test data or verify test results.
	 * Alternatively, repository methods can be used, but this is recommend as sometimes
	 * repositories offer a limited set of operations
	 * 
	 * An example would be a situation where we need to test a read-only repository but we need
	 * to add records to the database to execute tests successfully.
	 */
	@Autowired
	TestEntityManager entityManager;
	
	/*
	 * Given-when-then naming format for easy readability.
	 * Provides a clear understanding of what each test is testing for.
	 */
	
	@Test
	void givenNewCampaign_whenSave_thenSuccess() {
		Campaign newCampaign = new Campaign("CTEST-1", "Test Campaign 1", "Description");
		Campaign insertedCampaign = campaignRepository.save(newCampaign);
		
		assertThat(entityManager.find(Campaign.class, insertedCampaign.getId())).isEqualTo(newCampaign);
	}
	
	@Test
	void givenCampaignCreated_whenUpdate_thenSuccess() {
		Campaign newCampaign = new Campaign("CTEST-1", "Test Campaign 1", "Description");
		entityManager.persist(newCampaign);
		
		String newName = "New Campaign 001";
		newCampaign.setName(newName);
		
		newCampaign = campaignRepository.save(newCampaign);
		
		assertThat(entityManager.find(Campaign.class, newCampaign.getId()).getName()).isEqualTo(newName);
	}
	
	@Test
	void givenCampaignCreated_whenFindById_thenSuccess() {
		Campaign newCampaign = new Campaign("CTEST1", "Test Campaign 1", "Description");
		entityManager.persist(newCampaign);
		
		Optional<Campaign> retrievedCampaign = campaignRepository.findById(newCampaign.getId());
		assertThat(retrievedCampaign).contains(newCampaign);
	}
	
	@Test
	void givenCampaignCreated_whenFindByNameContaining_thenSuccess() {
		Campaign newCampaign = new Campaign("CTEST1", "Test Campaign 1", "Description");
		Campaign newCampaign2 = new Campaign("CTEST2", "Test Campaign 2", "Description");
		entityManager.persist(newCampaign);
		entityManager.persist(newCampaign2);
		
		Iterable<Campaign> campaigns = campaignRepository.findByNameContaining("Test");
		assertThat(campaigns).contains(newCampaign, newCampaign2);
	}
	
	@Test
	void givenCampaignCreated_whenDelete_thenSuccess() {
		Campaign newCampaign = new Campaign("CTest-1", "Test Campaign 1", "Description for campaign CTest-1");
		entityManager.persist(newCampaign);
		campaignRepository.delete(newCampaign);
		
		assertThat(entityManager.find(Campaign.class, newCampaign.getId())).isNull();
	}
	
	
}

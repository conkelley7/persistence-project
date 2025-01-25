package com.kelley.lsd;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.kelley.lsd.persistence.model.Campaign;
import com.kelley.lsd.persistence.model.Task;
import com.kelley.lsd.persistence.model.TaskStatus;
import com.kelley.lsd.persistence.repository.CampaignRepository;
import com.kelley.lsd.persistence.repository.TaskRepository;
import com.kelley.lsd.persistence.repository.WorkerRepository;

/**
 * Entry point for program.
 * I have extended ApplicationRunner and overriden run to add testing and demonstration methods for my repositories.
 */
@SpringBootApplication
public class PersistenceProjectApplication implements ApplicationRunner {
	
	@Autowired
	private CampaignRepository campaignRepository;
	
	@Autowired
	private WorkerRepository workerRepository;
	
	@Autowired
	private TaskRepository taskRepository;
	
	private static final Logger LOG = LoggerFactory.getLogger(PersistenceProjectApplication.class);
	
	/*
	 * Any code added to this method will be executed on startup.
	 */
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		LOG.info("Starting Spring Boot application.");
		
		
		/*
		 * Repository SELECT method implementations.
		 */
		
		
		
		
		// Commenting out for now while testing other implementations
		// Also commenting out data.sql while I test other implementation as manually inserting ID causes issues with JPA save()
		
		/*
		Iterable<Campaign> allCampaigns = campaignRepository.findAll();
		LOG.info("All Campaigns:\n{}", allCampaigns);
		
		Optional<Task> task1 = taskRepository.findById(1L);
		LOG.info("Task by id 1:\n{}", task1);
		
		long numOfWorkers = workerRepository.count();
		LOG.info("Number of workers:\n{}", numOfWorkers);
		
		Optional<Campaign> campaign1 = campaignRepository.findByCodeEquals("C1");
		LOG.info("Campaign with code C1: \n{}", campaign1);
		
		int campaignCount = campaignRepository.countByName("Campaign 1");
		LOG.info("Number of campaigns with name 'Campaign 1': \n{}", campaignCount);
		
		Iterable<Campaign> campaigns = campaignRepository.findByNameStartingWith("Campaign");
		LOG.info("Campaigns name starting with 'Campaign':");
		campaigns.forEach(campaign -> LOG.info("{}", campaign));
		
		List<Task> tasksStrictlyDue = taskRepository.findByDueDateGreaterThan(LocalDate.of(2025, 2, 10));
		LOG.info("Number of Tasks due strictly after: \"2025-02-10\"\n{}", tasksStrictlyDue.size());
		
		List<Task> overdueTasks = taskRepository.findByDueDateBeforeAndStatusEquals(LocalDate.now(), TaskStatus.TO_DO);
		LOG.info("Overdue Tasks:\n{}", overdueTasks);
		
		List<Task> tasksByAssignee = taskRepository.findByAssigneeFirstName("John");
		LOG.info("Tasks assigned to John:\n{}", tasksByAssignee);
		
		Iterable<Campaign> distinctCampaigns = campaignRepository.findDistinctByTasksNameContaining("Task");
		LOG.info("Distinct campaigns with Task name containing \"Task\":");
		distinctCampaigns.forEach(campaign -> LOG.info("{}", campaign));
		*/
		
		/*
		 * Persisting new entities.
		 */
		
		// Create a new campaign. Before persistence, ID is null.
		Campaign newCampaign = new Campaign("NEW1", "new campaign", "new campaign description");
		LOG.info("Campaign id before persisting: \n{}", newCampaign.getId());
		
		// Persist campaign. ID should be set automatically be persistence provider.
		campaignRepository.save(newCampaign);
		LOG.info("Campaign id after persisting:\n{}", newCampaign.getId());
		
		/*
		 * Updating entities
		 */
		
		// Change name
		newCampaign.setName("updated name");
		
		// Create new tasks and assign to tasks
		Set<Task> newCampaignTasks = Set.of(new Task("task name", "task description", LocalDate.of(2025, 1, 1), TaskStatus.TO_DO, newCampaign));
		newCampaign.setTasks(newCampaignTasks);
		
		/*
		 *  Spring detects this object already exists in datbase and updates it instead.
		 *  Re-assign output to newCampaign to ensure child Task entities get populated with generated id from database as well.
		 */
		newCampaign = campaignRepository.save(newCampaign);
		
		LOG.info("Child Task after updating\n{}", newCampaign.getTasks());
		
		/*
		 * Saving and/or updating multiple entities at once using saveAll()
		 */
		
		// Updating existing campaign
		newCampaign.setName("updated again");
		
		// Create a new campaign
		Campaign newCampaign2 = new Campaign("NEW2", "another campaign", "another campaign description");
		
		// Save to Iterable and use saveAll() to save, making sure to reassign to severalCampaigns Iterable
		Iterable<Campaign> severalCampaigns = Arrays.asList(newCampaign, newCampaign2);
		severalCampaigns = campaignRepository.saveAll(severalCampaigns);
		
		/*
		 * It is worth noting that all Spring Data JPA operations offered out-of-the-box (such as saveAll()) are handled as transactions.
		 * This means if one of the save operations fails, then nothing is committed to the DB.
		 */
		
		/*
		 * Deleting entities from database
		 */
		
		/*
		 *  Use an entity reference and call delete method of the repository
		 *
		 *  Campaign c1 = campaignRepository.findById(1L).get();
		 *	campaignRepository.delete(c1);
		 */
		
		
		/*
		 *  Delete an entity by providing an ID
		 *  
		 *  campaignRepository.deleteById(2L);
		 */
		
		// Delete entities with name containing "campaign" (custom method)
		// Long deleteCount = campaignRepository.deleteByNameContaining("campaign");
		// LOG.info("Number of removed campaigns:\n{}", deleteCount);
		
		// Can also fetch entities using findBy read query and then delete using deleteAll()
		
		/*
		 * Custom queries - demonstration usage
		 */
		
		Optional<String> campaignName = campaignRepository.findNameByCode();
		// If a value is assigned to campaignName, it is passed to the lambda function as 'c'
		campaignName.ifPresent(c -> LOG.info("Campaign Name:\n{}", c));
		
		// Calling native SQL query
		Campaign campaign = campaignRepository.findSingleCampaign();
		LOG.info("Single campaign:\n{}", campaign);
		
		
		
	}
	
	public static void main(String[] args) {
		SpringApplication.run(PersistenceProjectApplication.class, args);
	}

}

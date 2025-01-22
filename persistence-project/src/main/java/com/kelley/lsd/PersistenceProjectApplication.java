package com.kelley.lsd;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
	}
	
	public static void main(String[] args) {
		SpringApplication.run(PersistenceProjectApplication.class, args);
	}

}

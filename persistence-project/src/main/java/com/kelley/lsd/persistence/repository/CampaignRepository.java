package com.kelley.lsd.persistence.repository;

import org.springframework.data.repository.CrudRepository;

import com.kelley.lsd.persistence.model.Campaign;

public interface CampaignRepository extends CrudRepository<Campaign, Long> {
}

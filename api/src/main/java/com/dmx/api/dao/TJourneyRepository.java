package com.dmx.api.dao;

import com.dmx.api.entity.TJourneyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TJourneyRepository extends JpaRepository<TJourneyEntity, String> {
    TJourneyEntity findByName(String name);
}

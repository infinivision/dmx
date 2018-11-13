package com.dmx.api.dao.meta;

import com.dmx.api.entity.meta.TJourneyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TJourneyRepository extends JpaRepository<TJourneyEntity, String> {
    TJourneyEntity findByName(String name);
}

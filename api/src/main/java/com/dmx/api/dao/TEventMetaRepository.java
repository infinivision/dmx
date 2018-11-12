package com.dmx.api.dao;

import com.dmx.api.entity.TEventMetaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TEventMetaRepository extends JpaRepository<TEventMetaEntity, String> {
    TEventMetaEntity findByName(String name);
}

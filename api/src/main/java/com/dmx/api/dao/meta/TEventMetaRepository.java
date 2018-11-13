package com.dmx.api.dao.meta;

import com.dmx.api.entity.meta.TEventMetaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TEventMetaRepository extends JpaRepository<TEventMetaEntity, String> {
    TEventMetaEntity findByName(String name);
}

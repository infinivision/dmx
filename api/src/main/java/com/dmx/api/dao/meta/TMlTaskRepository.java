package com.dmx.api.dao.meta;

import com.dmx.api.entity.meta.TMlTaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TMlTaskRepository extends JpaRepository<TMlTaskEntity, String> {
    TMlTaskEntity findByName(String name);
}

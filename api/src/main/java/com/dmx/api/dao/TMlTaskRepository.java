package com.dmx.api.dao;

import com.dmx.api.entity.TMlTaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TMlTaskRepository extends JpaRepository<TMlTaskEntity, String> {
    TMlTaskEntity findByName(String name);
}

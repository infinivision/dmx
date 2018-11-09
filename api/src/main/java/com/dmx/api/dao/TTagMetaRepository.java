package com.dmx.api.dao;

import com.dmx.api.entity.TTagMetaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TTagMetaRepository extends JpaRepository<TTagMetaEntity, String>  {
    TTagMetaEntity findByCategory(String category);
    TTagMetaEntity findByName(String name);
}

package com.dmx.api.dao.meta;

import com.dmx.api.entity.meta.TTagMetaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TTagMetaRepository extends JpaRepository<TTagMetaEntity, String>  {
    TTagMetaEntity findByCategory(String category);
    TTagMetaEntity findByName(String name);
}

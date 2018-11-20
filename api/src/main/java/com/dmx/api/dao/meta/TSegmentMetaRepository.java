package com.dmx.api.dao.meta;

import com.dmx.api.entity.meta.TSegmentMetaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TSegmentMetaRepository extends JpaRepository<TSegmentMetaEntity, String> {
    TSegmentMetaEntity findByCategory(String category);
    TSegmentMetaEntity findByName(String name);
}

package com.dmx.api.dao.meta;

import com.dmx.api.entity.meta.TSegmentMetaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TSegmentMetaRepository extends JpaRepository<TSegmentMetaEntity, String> {
    Page<TSegmentMetaEntity> findByCategory(String category, Pageable pageable);
    TSegmentMetaEntity findByName(String name);
    Page<TSegmentMetaEntity> findByNameContains(String name, Pageable pageable);
}

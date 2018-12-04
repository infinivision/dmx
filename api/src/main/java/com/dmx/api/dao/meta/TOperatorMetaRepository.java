package com.dmx.api.dao.meta;

import com.dmx.api.entity.meta.TOperatorMetaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TOperatorMetaRepository extends JpaRepository<TOperatorMetaEntity, String> {
    List<TOperatorMetaEntity> findByType(Integer type);
}

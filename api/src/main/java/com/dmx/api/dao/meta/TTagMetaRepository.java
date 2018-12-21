package com.dmx.api.dao.meta;

import com.dmx.api.entity.meta.TTagMetaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TTagMetaRepository extends JpaRepository<TTagMetaEntity, String>  {
    Page<TTagMetaEntity> findByCategory(String category, Pageable pageable);
    TTagMetaEntity findByName(String name);
    List<TTagMetaEntity> findByIsSystemAndIsStaticAndType(Integer isSystem, Integer isStatic, Integer type);
    Page<TTagMetaEntity> findByNameContains(String name, Pageable pageable);
}

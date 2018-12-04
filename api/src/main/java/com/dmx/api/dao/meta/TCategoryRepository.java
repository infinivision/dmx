package com.dmx.api.dao.meta;

import com.dmx.api.entity.meta.TCategoryEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TCategoryRepository extends JpaRepository<TCategoryEntity, String> {
    List<TCategoryEntity> findByLevelAndType(Integer level, Integer type);
    List<TCategoryEntity> findByParent(String parent);
    TCategoryEntity findByName(String name);
    void deleteByCategoryTreeContaining(String id);
}

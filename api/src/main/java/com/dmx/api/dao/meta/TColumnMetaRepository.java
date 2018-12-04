package com.dmx.api.dao.meta;

import com.dmx.api.entity.meta.TColumnMetaEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TColumnMetaRepository extends JpaRepository<TColumnMetaEntity, String> {
    List<TColumnMetaEntity> findByCategoryAndCalculateFlag(String category, Integer calculate_flag);

    TColumnMetaEntity findByColumnName(String column_name);

    @Query(value = "SELECT DISTINCT category FROM t_column_meta where calculate_flag = :calculate_flag",nativeQuery = true)
    List<String> getCategoryByCalculateFlag(@Param("calculate_flag") Integer calculate_flag);
}

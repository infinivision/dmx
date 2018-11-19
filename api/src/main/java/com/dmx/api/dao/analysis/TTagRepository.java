package com.dmx.api.dao.analysis;

import com.dmx.api.entity.analysis.TTagEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.List;

public interface TTagRepository extends JpaRepository<TTagEntity, String> {
    @Query(value = "SELECT RB_CARDINALITY(id_list) FROM t_tag where id in (:id)",nativeQuery = true)
    Long getTagCustomerCountById(@Param("id") String id);

    @Query(value = "SELECT RB_OR_CARDINALITY_AGG(id_list) FROM t_tag where id in (:ids)",nativeQuery = true)
    Long getTagCustomerCountByIds(@Param("ids") List<String> ids);

    @Query(value = "SELECT RB_ITERATE(id_list) AS customer_id FROM t_tag where id in (:id)",
            countQuery = "SELECT RB_CARDINALITY(id_list) FROM t_tag where id in (:id)",
            nativeQuery = true)
    Page<BigInteger> getTagCustomerIdsByTagId(@Param("id") String id, Pageable pageable);

    @Query(value = "select distinct RB_ITERATE((SELECT RB_OR_AGG(id_list) FROM t_tag where id in (:ids))) as customer_id from t_tag",
            countQuery = "SELECT RB_OR_CARDINALITY_AGG(id_list) FROM t_tag where t_tag.id in (:ids)",
            nativeQuery = true)
    Page<BigInteger> getTagCustomerIdsByTagIds(@Param("ids") List<String> ids, Pageable pageable);
}

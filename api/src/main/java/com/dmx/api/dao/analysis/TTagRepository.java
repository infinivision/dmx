package com.dmx.api.dao.analysis;

import com.dmx.api.entity.analysis.TTagEntity;
import java.sql.Array;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Sort;

import java.math.BigInteger;
import java.util.List;

public interface TTagRepository extends JpaRepository<TTagEntity, String> {
    @Query(value = "SELECT rb_cardinality(id_list) FROM t_tag where id in (:id)", nativeQuery = true)
    Long getTagCountById(@Param("id") String id);

    @Query(value = "SELECT  rb_cardinality(rb_or_agg(id_list)) FROM t_tag where id in (:ids)", nativeQuery = true)
    Long getTagCountByIdsOr(@Param("ids") List<String> ids);

    @Query(value = "SELECT id, rb_cardinality(id_list) AS count FROM t_tag where id in (:ids) order by id", nativeQuery = true)
    List<Object[]> getTagCountByIds(@Param("ids") List<String> ids);

//    @Query(value = "SELECT RB_ITERATE(id_list) AS customer_id FROM t_tag where id in (:id)",
//            countQuery = "SELECT RB_CARDINALITY(id_list) FROM t_tag where id in (:id)",
//            nativeQuery = true)
//    Page<BigInteger> getTagCustomerIdsByTagId(@Param("id") String id, Pageable pageable);

    @Query(value = "SELECT CAST(rb_select(id_list,:size,(:page-1)*:size) AS TEXT) AS customer_id FROM t_tag where id in (:id)",
            nativeQuery = true)
    String getTagCustomerIdsByTagId(@Param("id") String id, int page, int size);

    @Query(value = "SELECT rb_to_array(id_list) AS customer_id FROM t_tag where id in (:id) ",
            countQuery = "SELECT rb_cardinality(id_list) FROM t_tag where id in (:id)",
            nativeQuery = true)
    List<BigInteger> getTagCustomerIdsByTagId(@Param("id") String id);
//    @Query(value = "select distinct RB_ITERATE((SELECT rb_or_agg(id_list) FROM t_tag where id in (:ids))) as customer_id from t_tag",
//            countQuery = "SELECT  rb_cardinality(rb_or_agg(id_list)) FROM t_tag where t_tag.id in (:ids)",
//            nativeQuery = true)
//    Page<BigInteger> getTagIdsByTagIds(@Param("ids") List<String> ids, Pageable pageable);

    @Query(value = "select CAST(rb_select((SELECT rb_or_agg(id_list) FROM t_tag where id in (:ids)),:size,(:page-1)*:size) AS TEXT) AS customer_id",
            nativeQuery = true)
    String getTagIdsByTagIds(@Param("ids") List<String> ids, int page, int size);

    @Query(value = "SELECT  rb_cardinality(rb_or_agg(id_list)) FROM t_tag where t_tag.id in (:ids)", nativeQuery = true)
    Long getTagIdsCountByTagIds(@Param("ids") List<String> ids);


    @Query(value = "SELECT id AS tag_id FROM t_tag where id in (:ids) and update_time >= :updateTime", nativeQuery = true)
    List<String> getIdByIdsAndUpdateTime(@Param("ids") List<String> ids, @Param("updateTime") Long updateTime);


    @Transactional
    @Modifying
    @Query(value = "update t_tag set id_list=rb_and(id_list, (select rb_and_agg(id_list) from t_tag where id in (:ids))),update_time=:now where id = :id", nativeQuery = true)
    void updateTagAndAnd(@Param("ids") List<String> ids, @Param("id") String id, @Param("now") Long now);

    @Transactional
    @Modifying
    @Query(value = "update t_tag set id_list=rb_and(id_list, (select rb_or_agg(id_list) from t_tag where id in (:ids))),update_time=:now where id = :id", nativeQuery = true)
    void updateTagAndOr(@Param("ids") List<String> ids, @Param("id") String id, @Param("now") Long now);

    @Transactional
    @Modifying
    @Query(value = "update t_tag set id_list=rb_or(id_list, (select rb_or_agg(id_list) from t_tag where id in (:ids))),update_time=:now where id = :id", nativeQuery = true)
    void updateTagOrOr(@Param("ids") List<String> ids, @Param("id") String id, @Param("now") Long now);

    @Transactional
    @Modifying
    @Query(value = "update t_tag set id_list=rb_or(id_list, (select rb_and_agg(id_list) from t_tag where id in (:ids))),update_time=:now where id = :id", nativeQuery = true)
    void updateTagOrAnd(@Param("ids") List<String> ids, @Param("id") String id, @Param("now") Long now);

    @Transactional
    @Modifying
    @Query(value = "update t_tag set id_list=(select rb_or_agg(id_list) from t_tag where id in (:ids)),update_time=:now where id = :id", nativeQuery = true)
    void updateTagOr(@Param("ids") List<String> ids, @Param("id") String id, @Param("now") Long now);

    @Transactional
    @Modifying
    @Query(value = "update t_tag set id_list=(select rb_and_agg(id_list) from t_tag where id in (:ids)),update_time=:now where id = :id", nativeQuery = true)
    void updateTagAnd(@Param("ids") List<String> ids, @Param("id") String id, @Param("now") Long now);

    @Transactional
    @Modifying
    @Query(value = "insert into t_tag (id, type, id_list, start_date, end_date, update_time, create_time, create_user_name, create_group_name, platform_id, platform_name)" +
            " values (:id, 1, (select rb_or_agg(id_list) from t_tag where id in (:ids)), (select to_date(:cur_date, 'YYYY-MM-DD')), '2099-12-31', :now, :now, :create_user, :create_group, :platform_id, :platform_name)", nativeQuery = true)
    void insertAnd(@Param("ids") List<String> ids, @Param("id") String id, @Param("cur_date") String cur_date,
                   @Param("now") Long now,
                   @Param("create_user") String create_user, @Param("create_group") String create_group,
                   @Param("platform_id") Integer platform_id, @Param("platform_name") String platform_name);

    @Transactional
    @Modifying
    @Query(value = "insert into t_tag (id, type, id_list, start_date, end_date, update_time, create_time, create_user_name, create_group_name, platform_id, platform_name)" +
            " values (:id, 1, (select rb_or_agg(id_list) from t_tag where id in (:ids)), (select to_date(:cur_date, 'YYYY-MM-DD')), '2099-12-31', :now, :now, :create_user, :create_group, :platform_id, :platform_name)", nativeQuery = true)
    void insertOr(@Param("ids") List<String> ids, @Param("id") String id, @Param("cur_date") String cur_date,
                  @Param("now") Long now,
                  @Param("create_user") String create_user, @Param("create_group") String create_group,
                  @Param("platform_id") Integer platform_id, @Param("platform_name") String platform_name);
}

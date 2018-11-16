package com.dmx.api.dao.analysis;

import com.dmx.api.entity.analysis.TCustomerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TCustomerRepository extends JpaRepository<TCustomerEntity, String> {
    Page<TCustomerEntity> findAllByIdIn(List<Integer> ids, Pageable pageable);
}

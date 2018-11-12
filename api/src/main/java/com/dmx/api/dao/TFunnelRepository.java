package com.dmx.api.dao;

import com.dmx.api.entity.TFunnelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TFunnelRepository extends JpaRepository<TFunnelEntity, String> {
    TFunnelEntity findByName(String name);
}

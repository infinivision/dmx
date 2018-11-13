package com.dmx.api.dao.meta;

import com.dmx.api.entity.meta.TFunnelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TFunnelRepository extends JpaRepository<TFunnelEntity, String> {
    TFunnelEntity findByName(String name);
}

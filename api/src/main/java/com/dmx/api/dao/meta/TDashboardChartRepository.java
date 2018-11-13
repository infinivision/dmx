package com.dmx.api.dao.meta;

import com.dmx.api.entity.meta.TDashboardChartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TDashboardChartRepository extends JpaRepository<TDashboardChartEntity, String>  {
    TDashboardChartEntity findByName(String name);
}

package com.dmx.api.dao;

import com.dmx.api.entity.TDashboardChartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TDashboardChartRepository extends JpaRepository<TDashboardChartEntity, String>  {
    TDashboardChartEntity findByName(String name);
}

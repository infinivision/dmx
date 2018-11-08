package com.dmx.api.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dmx.api.entity.TTagMetaEntity;

public interface TTagMetaRepository extends CrudRepository<TTagMetaEntity, Long>  {
}

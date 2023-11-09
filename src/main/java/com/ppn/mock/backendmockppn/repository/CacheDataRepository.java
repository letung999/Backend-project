package com.ppn.mock.backendmockppn.repository;

import com.ppn.mock.backendmockppn.dto.CacheData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CacheDataRepository extends CrudRepository<CacheData, String> {
    List<CacheData> findByKeyContainsIgnoreCase(String keyword);
}

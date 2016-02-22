package com.themoneypies.repository;

import com.themoneypies.domain.Entry;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EntryRepository extends CrudRepository<Entry, Long> {
    List<Entry> findAll();
    List<Entry> findAllByOrderByDateDesc();
}
package ru.job4j.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Site;

@Repository
public interface SiteRepository extends CrudRepository<Site, Integer> {

    public Site findByName(String name);
}
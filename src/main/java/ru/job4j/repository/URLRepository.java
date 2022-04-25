package ru.job4j.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.model.URL;

public interface URLRepository extends CrudRepository<URL, Integer> {

    public URL findByValue(String value);

    @Modifying
    @Query(value = "update URL u set u.count = u.count + 1 where u.value = ?1")
    public void updateUrlOrCount(String value);


}

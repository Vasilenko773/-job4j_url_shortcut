package ru.job4j.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.model.URL;
import ru.job4j.repository.URLRepository;

@Service
public class URLService {

    @Autowired
    private URLRepository repository;

    public String findByValue(String value) {
        URL url = repository.findByValue(value);
        return  url != null ? "url : " + url.getValue() + ", total : " + url.getCount()
                : "url отсутствует в системе";
    }
}

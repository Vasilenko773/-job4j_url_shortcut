package ru.job4j.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.job4j.model.Site;
import ru.job4j.model.URL;
import ru.job4j.repository.SiteRepository;
import ru.job4j.repository.URLRepository;

import javax.transaction.Transactional;
import java.util.*;

import static java.util.Collections.emptyList;

@Service
public class SiteService implements UserDetailsService {

    @Autowired
    private SiteRepository repository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private URLRepository urlRepository;

    public Site findByName(String name) {
        return repository.findByName(name) != null ? repository.findByName(name) : null;
    }

    public List<Site> findAllSite() {
        return (ArrayList<Site>) repository.findAll();
    }

    public String registerSite(Site site) {
        Site site1 = repository.findByName(site.getName());
        if (site1 == null) {
            String login = generateLoginAndPassword();
            String password = generateLoginAndPassword();
            site.setLogin(login);
            site.setPassword(password);
            site.setPassword(encoder.encode(site.getPassword()));
            repository.save(site);

            return "registration : true, login: " + login
                    + ", password: " + password;
        }
        return "registration : false - сайт с такис названием есть в системе";
    }

    public String registrationUrl(URL url) {
        String name = url.getValue().split("/")[2];
        Site site1 = findByName(name);
        if (site1 != null) {
            String code = generateLoginAndPassword();
            url.setCode(code);
            site1.addUrls(code, url);
            repository.save(site1);
            return "code : " + code;
        }
        return "The site with this name is not registered. Please register the site";
    }

    private String generateLoginAndPassword() {
        int leftLimit = 97; /* letter 'a' */
        int rightLimit = 122; /* letter 'z' */
        int targetStringLength = 6;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }

    @Transactional
    public ResponseEntity<String> getHTTPPage(String code) {
        Site site = findSiteByCode(code);
        if (site != null) {
            URL url = site.getUrls().get(code);
            if (url != null) {
              updateURLCount(url.getValue());
                site.getUrls().put(code, url);
                repository.save(site);
                return ResponseEntity.of(Optional.of("HTTP CODE - 302 REDIRECT " + url.getValue()));
            }
            return ResponseEntity.of(Optional.of("Данный код отсутствуетв системе."
                    + " Убелитесь что url зарегистрированно"));
        }
        return ResponseEntity.of(Optional.of("Cайт отсутствует в системе. Зарегистрируйте сатй"));
    }

    private Site findSiteByCode(String code) {
        List<Site> sites = findAllSite();
        for (Site site : sites) {
            if (site.getUrls().containsKey(code)) {
                return site;
            }
        }
        return null;
    }

    public String findSiteAndUrl(String name) {
        Site site = findByName(name);
        StringBuilder message = new StringBuilder();
        Map<String, URL> rls = new HashMap<>();
        if (site != null) {
            rls = site.getUrls();
            if (rls != null) {
                for (URL url : rls.values()) {
                    message.append("\n").append("url : ").append(url.getValue()).append(" , total : ").append(url.getCount());
                }
                return message.toString();
            }
            return "У сайта отсутствуют зарегистрированные url";
        }
        return "Сайт отсутсвует в системе, зарегистрируйте сайт";
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Site site = repository.findByName(name);
        if (site == null) {
            throw new UsernameNotFoundException(name);
        }
        return new User(site.getName(), site.getPassword(), emptyList());
    }

    private void updateURLCount(String value) {
        urlRepository.updateUrlOrCount(value);
    }

}





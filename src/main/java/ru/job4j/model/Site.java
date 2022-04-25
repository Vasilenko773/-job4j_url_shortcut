package ru.job4j.model;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Entity
@Table(name = "sites")
@Component
public class Site {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "hash",
            joinColumns = {@JoinColumn(name = "site_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "urls_id", referencedColumnName = "id")})
    @MapKey(name = "code")
    private Map<String, URL> urls = new HashMap<>();

    private String login;

    private String password;

    public Site() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map<String, URL> getUrls() {
        return urls;
    }

    public void setUrls(Map<String, URL> urls) {
        this.urls = urls;
    }

    public void addUrls(String code, URL url) {
        urls.put(code, url);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Site site = (Site) o;
        return id == site.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Site{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", urls=" + urls
                + ", login='" + login + '\''
                + ", password='" + password + '\''
                + '}';
    }
}

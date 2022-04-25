package ru.job4j.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.job4j.model.Site;
import ru.job4j.model.URL;
import ru.job4j.service.SiteService;
import ru.job4j.service.URLService;

import java.util.Optional;

@Controller
@RequestMapping("/shortcut")
public class ShortcutController {

    @Autowired
    private SiteService service;

    @Autowired
    private URLService urlService;


    @PostMapping("/registration")
    public ResponseEntity<String> registration(@RequestBody Site site) {
        String message = service.registerSite(site);
        return ResponseEntity.of(Optional.of(message));
    }

    @PostMapping("/convert")
    public ResponseEntity<String> registrationURL(@RequestBody URL url) {
        String message = service.registrationUrl(url);
        return ResponseEntity.of(Optional.of(message));
    }

    @GetMapping("/redirect/{code}")
    public HttpEntity getHTTP(@PathVariable String code) {
        return service.getHTTPPage(code);
    }

    @GetMapping("/statistic/site")
    public ResponseEntity<String> statistic(@RequestBody Site site) {
        String message = service.findSiteAndUrl(site.getName());
       return ResponseEntity.of(Optional.of(message));
    }

    @GetMapping("/statistic/url")
    public ResponseEntity<String> statisticUrl(@RequestBody URL url) {
        String message = urlService.findByValue(url.getValue());
        return ResponseEntity.of(Optional.of(message));
    }

 /*  @PostMapping("/sign-up")
    public void signUp(@RequestBody Site site) {
        site.setPassword(encoder.encode(site.getPassword()));
        service.registerSite(site);
    }*/
}



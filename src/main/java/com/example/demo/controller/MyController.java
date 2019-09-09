package com.example.demo.controller;

import com.example.demo.beans.Cache;
import com.example.demo.potroshitel.TerminatorQuoter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class MyController {

    @Autowired
    Cache cache;
    @Autowired
    private ApplicationContext context;

    @GetMapping(value = "/")
    public String get() {
        String rslt = "";
        for (String s : cache.getAll().keySet()) {
            rslt = rslt.concat("<div>").concat(s).concat(" : ").concat(cache.getByKey(s)).concat("</div>");
            }

        return new String("<html><head><title>9th of May" + context.getBean("tq", TerminatorQuoter.class).returnQuote()+ "</title></head><body><h2>Welcome to Wonderland</h2>" + rslt + "</body></html>");
    }

    @PutMapping(value = "/")
    public HttpStatus put(@RequestParam String key,
                           @RequestParam String val) {
        try {
            cache.put(key, val);
            return HttpStatus.OK;
        } catch (Exception e) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }

    }

    @DeleteMapping(value = "/")
    public HttpStatus del(@RequestParam String key) {
        try {
            cache.getAll().remove(key);
            return HttpStatus.OK;
        } catch (Exception e) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    @PostMapping
    public HttpStatus post(@RequestParam String val) {
        try {
            cache.post(val);
            return HttpStatus.CREATED;
        } catch (Exception e) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }


}

package com.enigmacamp.tokonyadia.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class Hello {

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "world") String name) {
        return "<h1 style='color: red'>Hello " + name + "</h1>";
    }

    @RequestMapping(value = "mapping", method = RequestMethod.GET)
    public String hello() {
        return "<h1 style='color: red'>Hello req mapping</h1>";
    }

    @PostMapping("hello")
    public String helloPost(@RequestBody String body) {
        return "<h1 style='color: red'>Hello posth jsjsjsj</h1> " + body;
    }

    // Path param
    @GetMapping("hello/{name}")
    public String helloPath(@PathVariable String name) {
        return "<h1 style='color: red'>Hello " + name + "</h1>";
    }



}

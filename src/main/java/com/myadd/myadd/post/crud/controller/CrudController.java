package com.myadd.myadd.post.crud.controller;

import com.myadd.myadd.post.crud.dto.CrudDto;
import com.myadd.myadd.post.crud.service.CrudService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CrudController {

    private final CrudService crudService;

    @GetMapping("/home")
    public String list() {
        return "home";
    }

    @GetMapping(value = "/posts/add-post")
    public String create() {
        return "post/write";
    }

    @PostMapping(value = "/posts/add-post")
    public String create(CrudDto post) {
        crudService.savePost(post);
        return "redirect:/home";
    }
}

package com.myadd.myadd.post.crud.controller;

import com.myadd.myadd.post.crud.dto.PostCrudDto;
import com.myadd.myadd.post.crud.service.PostCrudService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PostCrudController {

    private final PostCrudService postCrudService;

    @GetMapping("/home")
    public String list() {
        return "home";
    }

    @GetMapping(value = "/posts/add-post")
    public String create() {
        return "post/write";
    }

    @PostMapping(value = "/posts/add-post")
    public String create(PostCrudDto post) {
        postCrudService.savePost(post);
        return "redirect:/home";
    }

    @ResponseBody
    @DeleteMapping(value = "/posts/delete-post/{postId}")
    public String delete(@PathVariable("postId") Long id) {
       postCrudService.deletePost(id);

       return "delete" + id;
    }

    @ResponseBody
    @GetMapping("/posts/update-post/{postId}")
    public String edit(@PathVariable("postId") Long id, Model model) {
        PostCrudDto postCrudDto = postCrudService.getPost(id);

        model.addAttribute("postCrudDto", postCrudDto);
        return "GET mapping " + postCrudDto.toString();
    }
//
//    @ResponseBody
//    @PutMapping("/posts/update-post/{postId}")
//    public String update(@RequestBody PostCrudDto postCrudDto) {
//        log.info(String.valueOf(postCrudDto.getPost_id()));
//        postCrudService.savePost(postCrudDto);
//        return "수정완료";
//    }
}

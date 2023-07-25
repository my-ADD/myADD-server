package com.myadd.myadd.post.crud.controller;

import com.myadd.myadd.post.crud.dto.PostCrudDto;
import com.myadd.myadd.post.crud.service.PostCrudService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PostCrudController {

    private final PostCrudService postCrudService;

    //포토카드 전체 조회(for test)
    @GetMapping("/home")
    public String list(Model model) {
        List<PostCrudDto> postList = postCrudService.getPostList();

        model.addAttribute("PostList", postList);
        return "home";
    }

    //포토카드 작성화면으로 이동(for test)
    @GetMapping(value = "/posts/add-post")
    public String create() {
        return "post/write";
    }

    //포토카드 글작성
    @PostMapping(value = "/posts/add-post")
    public String create(PostCrudDto post) {
        postCrudService.savePost(post);
        return "redirect:/home";
    }

    //포토카드 삭제
    @DeleteMapping(value = "/posts/delete-post/{postId}")
    public String delete(@PathVariable("postId") Long id) {
       postCrudService.deletePost(id);

       return "redirect:/home";
    }

    //포토카드 글 조회(for test)
    @GetMapping("/posts/{postId}")
    public String detail(@PathVariable("postId") Long id, Model model) {
        PostCrudDto postCrudDto = postCrudService.findOne(id);

        model.addAttribute("postCrudDto", postCrudDto);
        return "post/detail";
    }

    //포토카드 수정 폼 화면(for test)
    @GetMapping("/posts/update-post/{postId}")
    public String edit(@PathVariable("postId") Long id, Model model) {
        PostCrudDto postCrudDto = postCrudService.findOne(id);

        model.addAttribute("postCrudDto", postCrudDto);
        return "post/update";
    }

    //포토카드 수정
    @PutMapping("/posts/update-post/{postId}")
    public String update(PostCrudDto postCrudDto) {
        log.info(String.valueOf(postCrudDto.getPostId()));
        postCrudService.savePost(postCrudDto);
        return "redirect:/home";
    }
}

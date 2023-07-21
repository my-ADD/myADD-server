package com.myadd.myadd.post.search.controller;

import com.myadd.myadd.post.search.dto.PostSearchDto;
import com.myadd.myadd.post.search.service.PostSearchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class PostSearchController {
    private PostSearchService postSearchService;
    public PostSearchController(PostSearchService postSearchService){
        this.postSearchService = postSearchService;
    }
    /**
    특정 유저의 포토카드 전체 목록 조회 API
     [GET] /posts/get-post-list/:user_id
     */
    //PathVariable
    @GetMapping("/posts/get-post-list/{user_id}")
    @ResponseBody
    public List<PostSearchDto> postList(@PathVariable("user_id") Long user_id, Model model){
        List<PostSearchDto> postSearchDtoList = postSearchService.getPostList(user_id);
        model.addAttribute("postList",postSearchDtoList);

        return postSearchDtoList;
    }
}

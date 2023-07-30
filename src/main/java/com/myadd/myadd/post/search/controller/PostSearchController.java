package com.myadd.myadd.post.search.controller;

import com.myadd.myadd.post.crud.dto.PostBackDto;
import com.myadd.myadd.post.search.dto.PostSearchBackDto;
import com.myadd.myadd.post.search.dto.PostSearchFrontDto;
import com.myadd.myadd.post.search.service.PostSearchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class PostSearchController {
    private PostSearchService postSearchService;
    public PostSearchController(PostSearchService postSearchService){
        this.postSearchService = postSearchService;
    }
    /**
    특정 유저의 포토카드 전체 목록 조회 API(기록순)
     [GET] /posts/get-post-list/createdAt?userId={userId}&page={page}
     */
    @GetMapping("/posts/get-post-list/createdAt")
    @ResponseBody
    public List<PostBackDto> postListByCreatedAt(@RequestParam(required = false) Long userId, @RequestParam(required = false, defaultValue = "0") int page, Model model){
        List<PostBackDto> postSearchDtoList = postSearchService.getPostList(userId,0,page);
        model.addAttribute("postList",postSearchDtoList);

        return postSearchDtoList;
    }
    /**
     특정 유저의 포토카드 전체 목록 조회 API(이름순)
     [GET] /posts/get-post-list/title?userId={userId}&page={page}
     */
    //PathVariable
    @GetMapping("/posts/get-post-list/title")
    @ResponseBody
    public List<PostBackDto> postListByTitle(@RequestParam(required = false) Long userId,@RequestParam(required = false, defaultValue = "0") int page, Model model){
        List<PostBackDto> postSearchDtoList = postSearchService.getPostList(userId,1,page);
        model.addAttribute("postList",postSearchDtoList);

        return postSearchDtoList;
    }

    /**
     포토 카드 하나의 앞장 상세 정보 조회
     [GET] /posts/get-post/:post_id/front
     */
    //PathVariable
    @GetMapping("/posts/get-post/{post_id}/front")
    @ResponseBody
    public PostSearchFrontDto frontPage(@PathVariable("post_id") Long postId,Model model){
        PostSearchFrontDto postSearchFrontDto = postSearchService.getFrontPage(postId);

        return postSearchFrontDto;
    }

    /**
     포토 카드 하나의 뒷장 상세 정보 조회
     [GET] /posts/get-post/:post_id/back
     */
    //PathVariable
    @GetMapping("/posts/get-post/{post_id}/back")
    @ResponseBody
    public PostSearchBackDto backPage(@PathVariable("post_id") Long postId,Model model){
        PostSearchBackDto postSearchBackDto = postSearchService.getBackPage(postId);

        return postSearchBackDto;
    }
    /**
     특정 유저의 포토카드 플랫폼에 따른 목록 조회 API(기록순)
     [GET] /posts/get-post-list/:category/:platform/createdAt?userId={userId}&page={page}
     */
    @GetMapping("/posts/get-post-list/{category}/{platform}/createdAt")
    @ResponseBody
    public List<PostBackDto> postListByPlatformByCreatedAt(@PathVariable String category,@PathVariable int platform,@RequestParam(required = false) Long userId,@RequestParam(required = false, defaultValue = "0") int page, Model model){
        List<PostBackDto> postSearchDtoList = postSearchService.getPostListByPlatform(userId,0,category,platform,page);
        model.addAttribute("postList",postSearchDtoList);

        return postSearchDtoList;
    }
    /**
     특정 유저의 포토카드 플랫폼에 따른 목록 조회 API(이름순)
     [GET] /posts/get-post-list/:category/:platform/title?userId={userId}&page={page}
     */
    //PathVariable
    @GetMapping("/posts/get-post-list/{category}/{platform}/title")
    @ResponseBody
    public List<PostBackDto> postListByPlatformByTile(@PathVariable String category,@PathVariable int platform,@RequestParam(required = false) Long userId,@RequestParam(required = false, defaultValue = "0") int page, Model model){
        List<PostBackDto> postSearchDtoList = postSearchService.getPostListByPlatform(userId,1,category,platform,page);
        model.addAttribute("postList",postSearchDtoList);

        return postSearchDtoList;
    }

}

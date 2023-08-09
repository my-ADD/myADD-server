package com.myadd.myadd.post.search.controller;

import com.myadd.myadd.post.crud.dto.PostBackDto;
import com.myadd.myadd.post.search.dto.PostSearchBackDto;
import com.myadd.myadd.post.search.dto.PostSearchFrontDto;
import com.myadd.myadd.post.search.service.PostSearchService;
import com.myadd.myadd.user.security.PrincipalDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
     [GET] /posts/get-post-list-all/createdAt?page={page}
     */
    @GetMapping("/posts/get-post-listAll/createdAt")
    @ResponseBody
    public List<PostBackDto> postListByCreatedAt(@RequestParam(required = false, defaultValue = "0") int page, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = ((PrincipalDetails) authentication.getPrincipal()).getId(); // UserDetailsImpl은 사용자의 상세 정보를 구현한 클래스
        List<PostBackDto> postSearchDtoList = postSearchService.getPostList(userId,0,page);
        model.addAttribute("postList",postSearchDtoList);
        return postSearchDtoList;
    }
    /**
     특정 유저의 포토카드 전체 목록 조회 API(이름순)
     [GET] /posts/get-post-list-all/title?page={page}
     */
    //PathVariable
    @GetMapping("/posts/get-post-listAll/title")
    @ResponseBody
    public List<PostBackDto> postListByTitle(@RequestParam(required = false, defaultValue = "0") int page, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = ((PrincipalDetails) authentication.getPrincipal()).getId(); // UserDetailsImpl은 사용자의 상세 정보를 구현한 클래스
        List<PostBackDto> postSearchDtoList = postSearchService.getPostList(userId,1,page);
        model.addAttribute("postList",postSearchDtoList);

        return postSearchDtoList;
    }

    /**
     포토 카드 하나의 앞장 상세 정보 조회
     [GET] /posts/get-post/front?postId={postId}
     */
    //PathVariable
    @GetMapping("/posts/get-post/front")
    @ResponseBody
    public PostSearchFrontDto frontPage(@RequestParam("postId") Long postId,Model model){
        PostSearchFrontDto postSearchFrontDto = postSearchService.getFrontPage(postId);

        return postSearchFrontDto;
    }

    /**
     포토 카드 하나의 뒷장 상세 정보 조회
     [GET] /posts/get-post/back?postId={postId}
     */
    //PathVariable
    @GetMapping("/posts/get-post/back")
    @ResponseBody
    public PostSearchBackDto backPage(@RequestParam("postId") Long postId,Model model){
        PostSearchBackDto postSearchBackDto = postSearchService.getBackPage(postId);

        return postSearchBackDto;
    }
    /**
     특정 유저의 포토카드 플랫폼에 따른 목록 조회 API(기록순)
     [GET] /posts/get-post-list/createdAt?category={category}&platform={platform}&page={page}
     */
    @GetMapping("/posts/get-post-list/createdAt")
    @ResponseBody
    public List<PostBackDto> postListByPlatformByCreatedAt(@RequestParam String category,@RequestParam String platform,@RequestParam(required = false, defaultValue = "0") int page, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = ((PrincipalDetails) authentication.getPrincipal()).getId(); // UserDetailsImpl은 사용자의 상세 정보를 구현한 클래스
        List<PostBackDto> postSearchDtoList = postSearchService.getPostListByPlatform(userId,0,category,platform,page);
        model.addAttribute("postList",postSearchDtoList);

        return postSearchDtoList;
    }
    /**
     특정 유저의 포토카드 플랫폼에 따른 목록 조회 API(이름순)
     [GET] /posts/get-post-list/title?category={category}&platform={platform}&page={page}
     */
    //PathVariable
    @GetMapping("/posts/get-post-list/title")
    @ResponseBody
    public List<PostBackDto> postListByPlatformByTile(@RequestParam String category,@RequestParam String platform,@RequestParam(required = false, defaultValue = "0") int page, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = ((PrincipalDetails) authentication.getPrincipal()).getId(); // UserDetailsImpl은 사용자의 상세 정보를 구현한 클래스
        List<PostBackDto> postSearchDtoList = postSearchService.getPostListByPlatform(userId,1,category,platform,page);
        model.addAttribute("postList",postSearchDtoList);

        return postSearchDtoList;
    }

}

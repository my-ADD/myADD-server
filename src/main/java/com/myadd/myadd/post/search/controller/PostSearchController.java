package com.myadd.myadd.post.search.controller;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.myadd.myadd.post.crud.dto.PostBackDto;
import com.myadd.myadd.post.search.dto.PostSearchBackDto;
import com.myadd.myadd.post.search.dto.PostSearchFrontDto;
import com.myadd.myadd.post.search.service.PostSearchService;
import com.myadd.myadd.response.BaseResponse;
import com.myadd.myadd.response.BaseResponseStatus;
import com.myadd.myadd.user.security.service.PrincipalDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PostSearchController {
    private PostSearchService postSearchService;
    public PostSearchController(PostSearchService postSearchService){
        this.postSearchService = postSearchService;
    }
    public Long getAuthentication(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null) return null;
        Long userId = ((PrincipalDetails) authentication.getPrincipal()).getId(); // UserDetailsImpl은 사용자의 상세 정보를 구현한 클래스

        return userId;
    }
    /**
     특정 유저의 포토카드 전체 생성날짜 조회
     [GET] /posts/createdAt
     */
    @GetMapping("/posts/createdAt")
    @ResponseBody
    public BaseResponse<List<String>> getCreatedAt(Model model){
        Long userId = getAuthentication();
        if(userId == null) return new BaseResponse<>(BaseResponseStatus.FAILED_NOT_AUTHENTICATION);
        List<String> createdAtList = new ArrayList<>();
        createdAtList=postSearchService.getPostCreatedAt(userId);

        return new BaseResponse<>(createdAtList,BaseResponseStatus.SUCCESS);
    }
    /**
    특정 유저의 포토카드 전체 목록 조회 API(기록순)
     [GET] /posts/get-post-list-all/createdAt?page={page}
     */
    @GetMapping("/posts/get-post-listAll/createdAt")
    @ResponseBody
    public BaseResponse<List<PostBackDto>>  postListByCreatedAt(@RequestParam(defaultValue = "-1") int page, Model model){
        Long userId = getAuthentication();
        if(userId == null) return new BaseResponse<>(BaseResponseStatus.FAILED_NOT_AUTHENTICATION);
        if(page==-1) // 클라이언트가 입력하지 않은 경우
            return new BaseResponse<>(BaseResponseStatus.POST_SEARCH_EMPTY_PAGE);

        List<PostBackDto> postSearchDtoList = postSearchService.getPostList(userId,0,page);
        if(page!=0 && postSearchDtoList.size()==0){ // 없는 페이지를 요청하는 경우(첫번째 페이지 제외)
            return new BaseResponse<>(BaseResponseStatus.GET_PAGE_NOT_EXISTS);
        }
        model.addAttribute("postList",postSearchDtoList);
        return new BaseResponse<>(postSearchDtoList,BaseResponseStatus.SUCCESS);
    }
    /**
     특정 유저의 포토카드 전체 목록 조회 API(이름순)
     [GET] /posts/get-post-list-all/title?page={page}
     */
    //PathVariable
    @GetMapping("/posts/get-post-listAll/title")
    @ResponseBody
    public BaseResponse<List<PostBackDto>> postListByTitle(@RequestParam(defaultValue = "-1") int page, Model model) {
        Long userId = getAuthentication();
        if(userId == null) return new BaseResponse<>(BaseResponseStatus.FAILED_NOT_AUTHENTICATION);
        if (page == -1) // 클라이언트가 입력하지 않은 경우
            return new BaseResponse<>(BaseResponseStatus.POST_SEARCH_EMPTY_PAGE);

        List<PostBackDto> postSearchDtoList = postSearchService.getPostList(userId, 1, page);
        if (page != 0 && postSearchDtoList.size()==0) // 없는 페이지를 요청하는 경우(첫번째 페이지 제외)
            return new BaseResponse<>(BaseResponseStatus.GET_PAGE_NOT_EXISTS);

        model.addAttribute("postList", postSearchDtoList);
        return new BaseResponse<>(postSearchDtoList, BaseResponseStatus.SUCCESS);
    }

    /**
     포토 카드 하나의 앞장 상세 정보 조회
     [GET] /posts/get-post/front?postId={postId}
     */
    //PathVariable
    @GetMapping("/posts/get-post/front")
    @ResponseBody
    public BaseResponse<PostSearchFrontDto> frontPage(@RequestParam(value = "postId", defaultValue = "-1") Long postId,Model model){
        if(postId==-1) // 포토카드 아이디를 입력하지 않은 경우
            return new BaseResponse<>(BaseResponseStatus.POST_SEARCH_EMPTY_POSTID);

        PostSearchFrontDto postSearchFrontDto = postSearchService.getFrontPage(postId);
        if(postSearchFrontDto==null) return new BaseResponse<>(BaseResponseStatus.GET_POST_NOT_EXISTS);
        if(postSearchFrontDto==null) // 없는 포토카드인 경우
            return new BaseResponse<>(BaseResponseStatus.GET_POST_NOT_EXISTS);

        return new BaseResponse<>(postSearchFrontDto,BaseResponseStatus.SUCCESS);
    }

    /**
     포토 카드 하나의 뒷장 상세 정보 조회
     [GET] /posts/get-post/back?postId={postId}
     */
    //PathVariable
    @GetMapping("/posts/get-post/back")
    @ResponseBody
    public BaseResponse<PostSearchBackDto> backPage(@RequestParam(value = "postId",defaultValue = "-1") Long postId,Model model){
        if(postId==-1) // 포토카드 아이디를 입력하지 않은 경우
            return new BaseResponse<>(BaseResponseStatus.POST_SEARCH_EMPTY_POSTID);

        PostSearchBackDto postSearchBackDto = postSearchService.getBackPage(postId);
        if(postSearchBackDto==null) return new BaseResponse<>(BaseResponseStatus.GET_POST_NOT_EXISTS);
        if(postSearchBackDto==null) // 없는 포토카드인 경우
            return new BaseResponse<>(BaseResponseStatus.GET_POST_NOT_EXISTS);

        return new BaseResponse<>(postSearchBackDto,BaseResponseStatus.SUCCESS);
    }
    /**
     특정 유저의 포토카드 플랫폼에 따른 목록 조회 API(기록순)
     [GET] /posts/get-post-list/createdAt?category={category}&platform={platform}&page={page}
     */
    @GetMapping("/posts/get-post-list/createdAt")
    @ResponseBody
    public BaseResponse<List<PostBackDto>> postListByPlatformByCreatedAt(@RequestParam(defaultValue = "null") String category,
                                                                         @RequestParam(defaultValue = "null") String platform,
                                                                         @RequestParam(defaultValue = "-1") int page,
                                                                         Model model){
        Long userId = getAuthentication();
        if(userId == null) return new BaseResponse<>(BaseResponseStatus.FAILED_NOT_AUTHENTICATION);
        if(category.equals("null")||platform.equals("null")) // 카테고리나 플랫폼을 입력하지 않은 경우
            return new BaseResponse<>(BaseResponseStatus.FAILED_INVALID_INPUT);
        if (page == -1) // 클라이언트가 입력하지 않은 경우
            return new BaseResponse<>(BaseResponseStatus.POST_SEARCH_EMPTY_PAGE);

        List<PostBackDto> postSearchDtoList = postSearchService.getPostListByPlatform(userId,0,category,platform,page);
        if (page != 0 && postSearchDtoList.size()==0) // 없는 페이지를 요청하는 경우(첫번째 페이지 제외)
            return new BaseResponse<>(BaseResponseStatus.GET_PAGE_NOT_EXISTS);
        model.addAttribute("postList",postSearchDtoList);

        return new BaseResponse<>(postSearchDtoList,BaseResponseStatus.SUCCESS);
    }
    /**
     특정 유저의 포토카드 플랫폼에 따른 목록 조회 API(이름순)
     [GET] /posts/get-post-list/title?category={category}&platform={platform}&page={page}
     */
    //PathVariable
    @GetMapping("/posts/get-post-list/title")
    @ResponseBody
    public BaseResponse<List<PostBackDto>> postListByPlatformByTile(@RequestParam(defaultValue = "null") String category,
                                                                    @RequestParam(defaultValue = "null") String platform,
                                                                    @RequestParam(defaultValue = "-1") int page,
                                                                    Model model){
        Long userId = getAuthentication();
        if(userId == null) return new BaseResponse<>(BaseResponseStatus.FAILED_NOT_AUTHENTICATION);
        if(category.equals("null")||platform.equals("null")) // 카테고리나 플랫폼을 입력하지 않은 경우
            return new BaseResponse<>(BaseResponseStatus.FAILED_INVALID_INPUT);
        if (page == -1) // 클라이언트가 입력하지 않은 경우
            return new BaseResponse<>(BaseResponseStatus.POST_SEARCH_EMPTY_PAGE);

        List<PostBackDto> postSearchDtoList = postSearchService.getPostListByPlatform(userId,1,category,platform,page);
        if (page != 0 && postSearchDtoList.size()==0) // 없는 페이지를 요청하는 경우(첫번째 페이지 제외)
            return new BaseResponse<>(BaseResponseStatus.GET_PAGE_NOT_EXISTS);
        model.addAttribute("postList",postSearchDtoList);

        return new BaseResponse<>(postSearchDtoList,BaseResponseStatus.SUCCESS);
    }
}

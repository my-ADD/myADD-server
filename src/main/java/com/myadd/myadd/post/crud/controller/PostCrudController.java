package com.myadd.myadd.post.crud.controller;

import com.myadd.myadd.post.crud.dto.PostBackDto;
import com.myadd.myadd.post.crud.dto.PostFrontDto;
import com.myadd.myadd.post.crud.service.PostCrudService;
import com.myadd.myadd.post.domain.PostEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PostCrudController {

    private final PostCrudService postCrudService;


    //포토카드 글작성1
    @PostMapping(value = "/posts/add")
    public ModelAndView create(@ModelAttribute("postFrontDto") PostFrontDto postFrontDto, Model model) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("post/write2");
        mv.addObject("image", postFrontDto.getImage());
        mv.addObject("comment", postFrontDto.getComment());
        return mv;
    }

//    @PostMapping(value = "/posts/add-backPost")
//    public String backCreate(@Valid @ModelAttribute("postBackDto") PostBackDto postBackDto, BindingResult bindingResult, Model model) {
//        //오류시 입력값 다 지워지는 부분 수정 필요
//        if(bindingResult.hasErrors()) {
//            model.addAttribute("image", postBackDto.getImage());
//            model.addAttribute("comment", postBackDto.getComment());
//            model.addAttribute("postBackDto", postBackDto);
//            log.info("errors = {}", bindingResult);
//            return "post/write2";
//        }
//
//        log.info(postBackDto.getTitle());
//        postCrudService.savePost(postBackDto);
//        return "redirect:/home";
//    }

    //작성2
    @PostMapping(value = "/posts/add-post",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody // 대신 @RequestPart 로 적기
    public String create(@RequestPart PostBackDto post, @RequestPart(value = "imageURL", required = false)MultipartFile imageURL) throws IOException {
        log.info(String.valueOf(imageURL));
        postCrudService.savePost(post, imageURL);
        log.info("입력완료");
        return "저장완료";
    }


    //포토카드 삭제
    @ResponseBody
    @DeleteMapping(value = "/posts/delete-post/{postId}")
    public String delete(@PathVariable("postId") Long id) {
       postCrudService.deletePost(id);

       return "삭제";
    }


    //포토카드 수정
    @PutMapping(value = "/posts/update-post/{postId}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody // 대신 @RequestPart 로 적기
    public String update(@PathVariable("postId") Long postId, @RequestPart PostBackDto post, @RequestPart(value = "imageURL", required = false)MultipartFile imageURL) throws IOException {
        log.info(post.toString());
        postCrudService.modifyPost(postId, post, imageURL);
        return "수정 완료";
    }
}

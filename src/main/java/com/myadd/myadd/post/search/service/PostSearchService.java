package com.myadd.myadd.post.search.service;

import com.myadd.myadd.post.crud.dto.PostBackDto;
import com.myadd.myadd.post.domain.PostEntity;
import com.myadd.myadd.post.repository.PostRepository;
import com.myadd.myadd.post.search.dto.PostSearchBackDto;
import com.myadd.myadd.post.search.dto.PostSearchFrontDto;
import com.myadd.myadd.user.domain.entity.UserEntity;
import com.myadd.myadd.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostSearchService {

    private PostRepository postRepository;
    private UserRepository userRepository;
    public PostSearchService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    //특정 사용자의 포토카드 전체 목록 조회(기록순,이름순)
    //flag 0: 기록순, 1:이름순
    @Transactional
    public List<PostBackDto> getPostList(Long userId, int flag) {
        List<PostEntity> posts;
        UserEntity user = userRepository.findByUserId(userId);
        if (flag == 0) // 기록순
            posts = postRepository.findByUserOrderByCreatedAtDesc(user);
        else // 이름순
            posts = postRepository.findByUserOrderByTitle(user);
        List<PostBackDto> postSearchDtoList = new ArrayList<>();
        for (PostEntity post : posts) {
            PostBackDto postSearchDto = post.toPostBackDto(post);
            postSearchDtoList.add(postSearchDto);
        }
        return postSearchDtoList;
    }

    // 특정 사용자의 전체 포토카드 생성날짜 조회
    @Transactional
    public List<String> getPostCreatedAt(Long userId){
        List<PostEntity> posts;
        List<String> createdAtList=new ArrayList<>();
        UserEntity user = userRepository.findByUserId(userId);
        posts=postRepository.findByUserOrderByCreatedAtDesc(user);
        for (PostEntity post : posts) {
            createdAtList.add(post.getCreatedAt().toString());
        }

        return createdAtList;
    }

    //특정 사용자의 포토카드 플랫폼 목록 조회(기록순,이름순)
    //flag 0: 기록순, 1:이름순
    @Transactional
    public List<PostBackDto> getPostListByPlatform(Long userId, int flag, String category,String platform) {
        List<PostEntity> posts;
        UserEntity user = userRepository.findByUserId(userId);

        if (flag == 0)
            posts = postRepository.findByPlatformAndCategoryAndUserOrderByCreatedAtDesc(platform,category,user);
        else
            posts = postRepository.findByPlatformAndCategoryAndUserOrderByTitle(platform,category,user);
        List<PostBackDto> postSearchDtoList = new ArrayList<>();

        for (PostEntity post : posts) {
            PostBackDto postSearchDto = post.toPostBackDto(post);
            postSearchDtoList.add(postSearchDto);
        }
        return postSearchDtoList;
    }

    // 포토 카드 하나의 상세정보 (앞페이지)
    @Transactional
    public PostSearchFrontDto getFrontPage(Long postId) {
        PostEntity post = postRepository.findByPostId(postId);
        if(post==null) return null;
        PostSearchFrontDto postSearchFrontDto = new PostSearchFrontDto();
        postSearchFrontDto.setComment(post.getComment());
        postSearchFrontDto.setImage(post.getImage());

        return postSearchFrontDto;
    }
    // 포토 카드 하나의 상세정보 (뒷페이지)
    @Transactional
    public PostSearchBackDto getBackPage(Long postId) {
        PostEntity post = postRepository.findByPostId(postId);
        if(post==null) return null;
        PostSearchBackDto postSearchBackDto = new PostSearchBackDto();
        postSearchBackDto.setMemo(post.getMemo());
        postSearchBackDto.setEmoji(post.getEmoji());
        postSearchBackDto.setGenre(post.getGenre());
        postSearchBackDto.setPlatform(post.getPlatform());
        postSearchBackDto.setTitle(post.getTitle());
        postSearchBackDto.setCreatedAt(post.getCreatedAt());
        postSearchBackDto.setEndedAt(post.getEndedAt());
        postSearchBackDto.setStartedAt(post.getStartedAt());
        postSearchBackDto.setViews(post.getViews());

        return postSearchBackDto;
    }
}

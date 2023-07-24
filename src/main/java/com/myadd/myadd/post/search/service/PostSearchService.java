package com.myadd.myadd.post.search.service;

import com.myadd.myadd.post.domain.PostEntity;
import com.myadd.myadd.post.repository.PostRepository;
import com.myadd.myadd.post.search.dto.PostSearchBackDto;
import com.myadd.myadd.post.search.dto.PostSearchDto;
import com.myadd.myadd.post.search.dto.PostSearchFrontDto;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostSearchService {

    private PostRepository postRepository;

    public PostSearchService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    //특정 사용자의 전체 게시글을 불러옴
    @Transactional
    public List<PostSearchDto> getPostList(Long userId) {
        List<PostEntity> posts = postRepository.findAll();
        List<PostSearchDto> postSearchDtoList = new ArrayList<>();

        for (PostEntity post : posts) {
            if(post.getUser().getUserId() == userId) {
                PostSearchDto postSearchDto = post.toPostSearchDto(post);
                postSearchDtoList.add(postSearchDto);
            }
        }
        return postSearchDtoList;
    }

    @Transactional
    public PostSearchFrontDto getFrontPage(Long postId){
        PostEntity post = postRepository.findByPostId(postId);
        PostSearchFrontDto postSearchFrontDto = new PostSearchFrontDto();
        postSearchFrontDto.setComment(post.getComment());
        postSearchFrontDto.setImage(post.getImage());

        return postSearchFrontDto;
    }

    @Transactional
    public PostSearchBackDto getBackPage(Long postId){
        PostEntity post = postRepository.findByPostId(postId);
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

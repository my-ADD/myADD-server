package com.myadd.myadd.post.search.service;

import com.myadd.myadd.post.domain.PostEntity;
import com.myadd.myadd.post.repository.PostRepository;
import com.myadd.myadd.post.search.dto.PostSearchDto;
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
    public List<PostSearchDto> getPostList(Long user_id) {
        List<PostEntity> posts = postRepository.findAll();
        List<PostSearchDto> postSearchDtoList = new ArrayList<>();

        for (PostEntity post : posts) {
            if(post.getUser().getUserId() == user_id) {
                PostSearchDto postSearchDto = post.toPostSearchDto(post);
                postSearchDtoList.add(postSearchDto);
            }
        }
        return postSearchDtoList;
    }
}

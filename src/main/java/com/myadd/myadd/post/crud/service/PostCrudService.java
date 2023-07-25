package com.myadd.myadd.post.crud.service;

import com.myadd.myadd.post.crud.dto.PostCrudDto;
import com.myadd.myadd.post.crud.repository.PostCrudRepository;
import com.myadd.myadd.post.domain.PostEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostCrudService {

    private final PostCrudRepository postCrudRepository;

    public List<PostCrudDto> getPostList() {
        List<PostEntity> postEntities = postCrudRepository.findAll();
        List<PostCrudDto> postCrudDtoList = new ArrayList<>();

        for (PostEntity postEntity : postEntities) {
            PostCrudDto postCrudDto = new PostCrudDto();
            postCrudDto.setPostId(postEntity.getPostId());
            postCrudDto.setTitle(postEntity.getTitle());
            postCrudDto.setCategory(postEntity.getCategory());

            postCrudDtoList.add(postCrudDto);
        }

        return postCrudDtoList;
    }

    @Transactional
    public void savePost(PostCrudDto postDto) {
        postCrudRepository.save(postDto.toPostEntity(postDto));
    }

    @Transactional
    public void deletePost(Long id) {
        postCrudRepository.deleteById(id);
    }

    @Transactional
    public PostCrudDto findOne(Long id) {
        PostEntity postEntity = postCrudRepository.findByPostId(id);

        PostCrudDto postCrudDto = new PostCrudDto();
        postCrudDto.setPostId((postEntity.getPostId()));
        postCrudDto.setCategory(postEntity.getCategory());
        postCrudDto.setComment(postEntity.getComment());
        postCrudDto.setEmoji(postEntity.getEmoji());
        postCrudDto.setEndedAt(postEntity.getEndedAt());
        postCrudDto.setGenre(postEntity.getGenre());
        postCrudDto.setImage(postEntity.getImage());
        postCrudDto.setMemo(postEntity.getMemo());
        postCrudDto.setPlatform(postEntity.getPlatform());
        postCrudDto.setStartedAt(postEntity.getStartedAt());
        postCrudDto.setTitle(postEntity.getTitle());

        return postCrudDto;
    }
}

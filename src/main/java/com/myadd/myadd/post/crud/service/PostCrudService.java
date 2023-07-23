package com.myadd.myadd.post.crud.service;

import com.myadd.myadd.post.crud.dto.PostCrudDto;
import com.myadd.myadd.post.crud.repository.PostCrudRepository;
import com.myadd.myadd.post.domain.PostEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostCrudService {

    private final PostCrudRepository postCrudRepository;

    @Transactional
    public void savePost(PostCrudDto postDto) {
        postCrudRepository.save(postDto.toPostEntity(postDto));
    }

    @Transactional
    public void deletePost(Long id) {
        postCrudRepository.deleteById(id);
    }

    @Transactional
    public PostCrudDto getPost(Long id) {
        Optional<PostEntity> postWrapper = postCrudRepository.findById(id);
        PostEntity postEntity = postWrapper.get();

        PostCrudDto postCrudDto = new PostCrudDto();
        postCrudDto.setPost_id((postEntity.getPost_id()));
        postCrudDto.setCategory(postEntity.getCategory());
        postCrudDto.setComment(postEntity.getComment());
        postCrudDto.setEmoji(postEntity.getEmoji());
        postCrudDto.setEnded_at(postEntity.getEnded_at());
        postCrudDto.setGenre(postEntity.getGenre());
        postCrudDto.setImage(postEntity.getImage());
        postCrudDto.setMemo(postEntity.getMemo());
        postCrudDto.setPlatform(postEntity.getPlatform());
        postCrudDto.setStarted_at(postEntity.getStarted_at());
        postCrudDto.setTitle(postEntity.getTitle());

        return postCrudDto;
    }
}

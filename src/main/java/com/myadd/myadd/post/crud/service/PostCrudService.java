package com.myadd.myadd.post.crud.service;

import com.myadd.myadd.fileUpload.service.FileUploadService;
import com.myadd.myadd.post.crud.dto.PostBackDto;
import com.myadd.myadd.post.crud.repository.PostCrudRepository;
import com.myadd.myadd.post.domain.PostEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PostCrudService {

    private final PostCrudRepository postCrudRepository;
    private final FileUploadService fileUploadService;
    public List<PostBackDto> getPostList() {
        List<PostEntity> postEntities = postCrudRepository.findAll();
        List<PostBackDto> postBackDtoList = new ArrayList<>();

        for (PostEntity postEntity : postEntities) {
            PostBackDto PostBackDto = new PostBackDto();
            PostBackDto.setPostId(postEntity.getPostId());
            PostBackDto.setTitle(postEntity.getTitle());

            postBackDtoList.add(PostBackDto);
        }

        return postBackDtoList;
    }

    @Transactional
    public void savePost(PostBackDto postDto, MultipartFile imageURL) throws IOException {
        String storedFileName = fileUploadService.upload(imageURL);
        postDto.setImage(storedFileName);
        postCrudRepository.save(postDto.toPostEntity(postDto));
    }


    @Transactional
    public void deletePost(Long id) {
        postCrudRepository.deleteById(id);
    }

    @Transactional
    public PostBackDto findOne(Long id) {
        PostEntity postEntity = postCrudRepository.findByPostId(id);

        PostBackDto PostBackDto = new PostBackDto();
        PostBackDto.setPostId((postEntity.getPostId()));
        PostBackDto.setCategory(postEntity.getCategory());
        PostBackDto.setComment(postEntity.getComment());
        PostBackDto.setEmoji(postEntity.getEmoji());
        PostBackDto.setEndedAt(postEntity.getEndedAt());
        PostBackDto.setGenre(postEntity.getGenre());
        PostBackDto.setImage(postEntity.getImage());
        PostBackDto.setMemo(postEntity.getMemo());
        PostBackDto.setPlatform(postEntity.getPlatform());
        PostBackDto.setStartedAt(postEntity.getStartedAt());
        PostBackDto.setTitle(postEntity.getTitle());
        PostBackDto.setViews(postEntity.getViews());

        return PostBackDto;
    }
}

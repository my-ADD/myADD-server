package com.myadd.myadd.post.crud.service;

import com.myadd.myadd.fileUpload.service.FileUploadService;
import com.myadd.myadd.post.crud.dto.PostBackDto;
import com.myadd.myadd.post.crud.repository.PostCrudRepository;
import com.myadd.myadd.post.domain.PostEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostCrudService {

    private final PostCrudRepository postCrudRepository;
    private final FileUploadService fileUploadService;

    public String savePost(PostBackDto postDto, String image, Long id) throws IOException {
        if(!image.isEmpty()) {
            postDto.setImage(image);
        }
        postDto.setUserId(id);
        postCrudRepository.save(postDto.toPostEntity(postDto));
        return "Success: save post";
    }



    public boolean deletePost(Long postId) {
        PostEntity postEntity = postCrudRepository.findByPostId(postId);

        if (postEntity == null ) {
            return false;
        }

        if(postEntity.getImage() != null) {
            String url = postEntity.getImage();
            fileUploadService.fileDelete(url.split("/")[3]);
        }
        postCrudRepository.deleteById(postId);

        return true;
    }



    public PostEntity modifyPost(Long postId, PostBackDto postDto, MultipartFile image, Long id) throws IOException {
        PostEntity postEntity = postCrudRepository.findByPostId(postId);

        if (postEntity == null) {
            return null;
        }
        if(postEntity.getImage() != null) {
            String deleteUrl = postEntity.getImage();
            fileUploadService.fileDelete(deleteUrl.split("/")[3]);
        }
        if (!image.isEmpty()) {
            String storedFileName = fileUploadService.upload(image);
            postDto.setImage(storedFileName);
        }

        postDto.setPostId(postId);
        postDto.setUserId(id);
        postDto.setCreatedAt(postEntity.getCreatedAt());
        return postCrudRepository.save(postDto.toModPostEntity(postDto));

    }
}

package com.myadd.myadd.post.crud.service;

import com.myadd.myadd.fileUpload.service.FileUploadService;
import com.myadd.myadd.post.crud.dto.PostBackDto;
import com.myadd.myadd.post.crud.repository.PostCrudRepository;
import com.myadd.myadd.post.domain.PostEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PostCrudService {

    private final PostCrudRepository postCrudRepository;
    private final FileUploadService fileUploadService;

    @Transactional
    public void savePost(PostBackDto postDto, MultipartFile imageURL) throws IOException {
        String storedFileName = fileUploadService.upload(imageURL);
        postDto.setImage(storedFileName);
        postCrudRepository.save(postDto.toPostEntity(postDto));
    }


    @Transactional
    public void deletePost(Long postId) {
        PostEntity postEntity = postCrudRepository.findByPostId(postId);
        String url = postEntity.getImage();
        fileUploadService.fileDelete(url.split("/")[3]);
        postCrudRepository.deleteById(postId);
    }


    @Transactional
    public void modifyPost(Long postId, PostBackDto postDto, MultipartFile imageURL) throws IOException {
        PostEntity postEntity = postCrudRepository.findByPostId(postId);
        String deleteUrl = postEntity.getImage();
        fileUploadService.fileDelete(deleteUrl.split("/")[3]);

        if (imageURL.isEmpty()) {
            postCrudRepository.save(postDto.toPostEntity(postDto));
        }
        else {
            String storedFileName = fileUploadService.upload(imageURL);
            postDto.setImage(storedFileName);
            postDto.setPostId(postId);
            postDto.setCreatedAt(postEntity.getCreatedAt());
            postCrudRepository.save(postDto.toModPostEntity(postDto));
        }
    }
}

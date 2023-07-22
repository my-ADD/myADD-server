package com.myadd.myadd.post.crud.service;

import com.myadd.myadd.post.crud.dto.CrudDto;
import com.myadd.myadd.post.crud.repository.CrudRepository;
import com.myadd.myadd.post.domain.PostEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CrudService {

    private final CrudRepository crudRepository;

    @Transactional
    public void savePost(CrudDto postDto) {
        crudRepository.save(postDto.toPostEntity(postDto));
    }

}

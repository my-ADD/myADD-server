package com.myadd.myadd.post.calendar.service;

import com.myadd.myadd.post.calendar.repository.CalRepostitory;
import com.myadd.myadd.post.crud.dto.PostBackDto;
import com.myadd.myadd.post.domain.PostEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CalService {
    private final CalRepostitory calRepostitory;

    public List<PostBackDto> findByCreatedAt(Long userId, String createdAt) {
        List<PostEntity> postEntities = calRepostitory.findByCreatedAt(userId, createdAt);

        if (postEntities == null) {
            return null;
        }

        List<PostBackDto> postBackDtoList = new ArrayList<>();
        for (PostEntity post : postEntities) {
            if (post.getUser().getUserId() == userId) {
                PostBackDto postBackDto = post.toPostBackDto(post);
                postBackDtoList.add(postBackDto);
            }
        }

        return postBackDtoList;
    }
}

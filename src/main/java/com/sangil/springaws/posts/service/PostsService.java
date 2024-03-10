package com.sangil.springaws.posts.service;


import com.sangil.springaws.posts.domain.Posts;
import com.sangil.springaws.posts.dto.response.PostsListResponseDto;
import com.sangil.springaws.posts.dto.response.PostsResponseDto;
import com.sangil.springaws.posts.dto.request.PostsSaveRequestDto;
import com.sangil.springaws.posts.dto.request.PostsUpdateRequestDto;
import com.sangil.springaws.posts.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    // dto 받아서 -> entity로 변환 -> DB에 저장 -> 엔티티 ID반환
    @Transactional // 트랜잭션의 일부로 실행됨(원자성) 성공->커밋
    public Long save(PostsSaveRequestDto requestDto){
        return postsRepository.save(requestDto.toEntity()).getId();
    };

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto){
        Posts posts = postsRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("해당 게시물이 없습니다. id="+id));

        // entity uodate 로직은 entity에서?
        posts.update(requestDto.getTitle(), requestDto.getContent());
        // JPA 영속성 컨텍스트 떄문!! ( 엔티티를 영구저장 -> 쿼리 안날려도 됨)

        return id; // update 성공하면 id return
    }

    @Transactional
    public PostsResponseDto findById(Long id){
        // id로 리포티토리에서 entity 찾음
        // entity -> dto 변환
        Posts entity = postsRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("해당 게시물이 없습니다. id="+id));

        return new PostsResponseDto(entity);
        // entity를 dto로 변환할 떄는 생성자 이용
    }

    @Transactional
    public Long delete(Long id){
        Posts posts = postsRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("해당 게시물이 없습니다, id=" + id));
        postsRepository.delete(posts);
        return id;
    }
    // delete는 dto를 전달할 필요도 없기에 id로 찾아서 그냥 바로 지우면 됨

    @Transactional
    public List<PostsListResponseDto> findAllDesc(){
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

}

package com.sangil.springaws.posts.dto.response;

import com.sangil.springaws.posts.domain.Posts;
import lombok.Getter;

@Getter
public class PostsResponseDto {

    private Long id;
    private String title;
    private String content;
    private String author;


    // 생성자 방식으로 entity -> dto 변환
    // response DTO이기 떄문에 Entity를 dto로 변환하는 과정 필요
    public PostsResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
    }
}

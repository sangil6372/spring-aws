package com.sangil.springaws.posts.dto.request;

import com.sangil.springaws.posts.domain.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {
    private String title;
    private String content;
    private String author;
    @Builder
    public PostsSaveRequestDto(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    // Entity는 주요 클래스 이기 때문에
    public Posts toEntity(){ // 의존성 관점에서 생성자 방식보다 빌더패턴 toEntity 방식이 적절
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }
    //

}

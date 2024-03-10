package com.sangil.springaws.posts.domain;
import com.sangil.springaws.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.NoArgsConstructor;

import lombok.Builder;
import lombok.Getter;

import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Getter
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "posts")
public class Posts extends BaseTimeEntity {

    @Id // table의 PK필드
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PK 생성 규칙
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
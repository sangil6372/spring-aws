package com.sangil.springaws.posts.controller;


import com.sangil.springaws.posts.dto.request.PostsUpdateRequestDto;
import com.sangil.springaws.posts.dto.response.PostsListResponseDto;
import com.sangil.springaws.posts.dto.response.PostsResponseDto;
import com.sangil.springaws.posts.dto.request.PostsSaveRequestDto;
import com.sangil.springaws.posts.service.PostsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "예제 API", description = "Swagger 테스트용 API")
@RequiredArgsConstructor // final이 선언된 postsService의 인자값으로 생성자 생성
@RestController
public class PostsApiController {

    private final PostsService postsService; // @Autowired사용 X

    @Operation(summary = "엔티티 POST", description = "body 정보대로 table에 삽입")
    @PostMapping("api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto){
        // @RequestBody : 클라이언트에서 받은 데이터를 dto로 변환
        // posts repository에 저장하도록 service 계층에 전달
        // 컨트롤러-서비스-리포지토리 패턴
        return postsService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) {
        return postsService.update(id, requestDto);
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable Long id) {
        postsService.delete(id);
        return id;
    }

    @Operation(summary = "엔티티 GET", description = "id를 통해 데이터 find")
    @GetMapping("api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id){
        return postsService.findById(id);
    }

    @GetMapping("api/v1/posts/list")
    public List<PostsListResponseDto> findAll(){
        return postsService.findAllDesc();
    }



}

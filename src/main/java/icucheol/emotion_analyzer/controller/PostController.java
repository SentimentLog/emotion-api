package icucheol.emotion_analyzer.controller;

import icucheol.emotion_analyzer.dto.PostRequestDto;
import icucheol.emotion_analyzer.dto.PostResponseDto;
import icucheol.emotion_analyzer.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Tag(name = "Post API", description = "게시물 작성 API")
public class PostController {

    private final PostService postService;

    /**
     * 게시물 생성
     */
    @PostMapping
    @Operation(summary = "게시물 작성" , description = "글쓰기")
    public ResponseEntity<PostResponseDto> createPost(@RequestBody PostRequestDto postRequestDto,
                                                      @AuthenticationPrincipal Long memberId) {
        PostResponseDto responseDto = postService.createPost(postRequestDto, memberId);
        return ResponseEntity.ok(responseDto);
    }

    /**
     * 특정 회원의 게시물 조회
     */
    @GetMapping
    @Operation(summary = "게시물 조회" , description = "어떤 놈이 쓴 글인지")
    public ResponseEntity<List<PostResponseDto>> getPostsByMember(@AuthenticationPrincipal Long memberId) {
        List<PostResponseDto> posts = postService.getPostsByMember(memberId);
        return ResponseEntity.ok(posts);
    }

    /**
     * 게시물 수정
     */
    @PutMapping("/{postId}")
    @Operation(summary = "수정" , description = "글 수정")
    public ResponseEntity<PostResponseDto> updatePost(@PathVariable Long postId,
                                                      @RequestBody PostRequestDto postRequestDto,
                                                      @AuthenticationPrincipal Long memberId) {
        PostResponseDto updatedPost = postService.updatePost(postId, postRequestDto, memberId);
        return ResponseEntity.ok(updatedPost);
    }

    /**
     * 게시물 삭제
     */
    @DeleteMapping("/{postId}")
    @Operation(summary = "게시물 삭제" , description = "이승기가 부릅니다 삭제")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId,
                                           @AuthenticationPrincipal Long memberId) {
        postService.deletePost(postId, memberId);
        return ResponseEntity.noContent().build();
    }
}

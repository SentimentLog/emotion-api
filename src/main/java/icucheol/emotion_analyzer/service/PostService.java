package icucheol.emotion_analyzer.service;

import icucheol.emotion_analyzer.domain.Member;
import icucheol.emotion_analyzer.domain.Post;
import icucheol.emotion_analyzer.dto.PostRequestDto;
import icucheol.emotion_analyzer.dto.PostResponseDto;
import icucheol.emotion_analyzer.repository.MemberRepository;
import icucheol.emotion_analyzer.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    // 게시물 생성
    public PostResponseDto createPost(PostRequestDto postRequestDto, Long memberId) {
        Member author = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원이 아님"));

        Post post = Post.builder()
                .title(postRequestDto.getTitle())
                .content(postRequestDto.getContent())
                .author(author)
                .build();
        Post savedPost = postRepository.save(post);
        return new PostResponseDto(savedPost);
    }

    // 게시물 조회
    public List<PostResponseDto> getPostsByMember(Long memberId) {
        List<Post> posts = postRepository.findByAuthorId(memberId);
        return posts.stream().map(PostResponseDto::new).collect(Collectors.toList());
    }

    // 게시물 수정
    public PostResponseDto updatePost(Long postId, PostRequestDto postRequestDto, Long memberId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        if (!post.getAuthor().getId().equals(memberId)) {
            throw new IllegalArgumentException("Unauthorized to update this post");
        }

        post.setTitle(postRequestDto.getTitle());
        post.setContent(postRequestDto.getContent());
        Post updatedPost = postRepository.save(post);

        return new PostResponseDto(updatedPost);
    }

    // 게시물 삭제
    public void deletePost(Long postId, Long memberId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        if (!post.getAuthor().getId().equals(memberId)) {
            throw new IllegalArgumentException("Unauthorized to delete this post");
        }

        postRepository.delete(post);
    }
}




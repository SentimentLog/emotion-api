package icucheol.emotion_analyzer.repository;

import icucheol.emotion_analyzer.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByTitle(String title); // 특정 게시물명 찾기
    List<Post> findByAuthorId(Long id); // 특정 ID와 게시물명으로 검색
    List<Post> findAllByAuthorIdAndTitle(Long id, String title); // 특정 회원 ID로 검색
    List<Post> findAllByTitleContaining(String keyword); // Like 키워드로 포함된 게시물 검색
}

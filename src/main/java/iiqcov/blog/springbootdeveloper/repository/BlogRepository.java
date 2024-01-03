package iiqcov.blog.springbootdeveloper.repository;

import iiqcov.blog.springbootdeveloper.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Article, Long> {
}

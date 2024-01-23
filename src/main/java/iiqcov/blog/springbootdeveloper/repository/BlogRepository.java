package iiqcov.blog.springbootdeveloper.repository;

import iiqcov.blog.springbootdeveloper.domain.Article;
import iiqcov.blog.springbootdeveloper.domain.Folder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogRepository extends JpaRepository<Article, Long> {
    List<Article> findByFolder(Folder folder);
    Page<Article> findByPublicStatusTrue(Pageable pageable);
    List<Article> findByFolderAndPublicStatusTrue(Folder folder);
}
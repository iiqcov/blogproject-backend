package iiqcov.blog.springbootdeveloper.api.article;

import iiqcov.blog.springbootdeveloper.domain.Article;
import iiqcov.blog.springbootdeveloper.dto.article.AddArticleRequest;
import iiqcov.blog.springbootdeveloper.dto.article.UpdateArticleRequest;
import iiqcov.blog.springbootdeveloper.service.article.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ArticleApi {
    private final BlogService blogService;

    @PostMapping("/api/article")
    public ResponseEntity<Article> createArticle(@RequestBody AddArticleRequest addArticleRequest){
        Article savedArticle = blogService.save(addArticleRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedArticle);
    }

    @DeleteMapping("/api/article/{id}")
    public ResponseEntity<Article> deleteArticle(@PathVariable Long id){
        blogService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PutMapping("/api/article/{id}")
    public ResponseEntity<Article> modifyArticle(@PathVariable Long id, @RequestBody UpdateArticleRequest request){
        Article updatedArticle=blogService.update(id, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(updatedArticle);
    }
}

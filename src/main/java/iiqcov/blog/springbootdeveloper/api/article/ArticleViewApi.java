package iiqcov.blog.springbootdeveloper.api.article;

import iiqcov.blog.springbootdeveloper.domain.Article;
import iiqcov.blog.springbootdeveloper.dto.article.ArticleListViewResponse;
import iiqcov.blog.springbootdeveloper.service.article.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ArticleViewApi {
    private final BlogService blogService;

    @GetMapping("/")
    public void home(){

    }

    @GetMapping("/articles")
    public ResponseEntity<List<ArticleListViewResponse>> getArticles(Model model){
        List<ArticleListViewResponse> articles=blogService.findAll().stream()
                .map(ArticleListViewResponse::new)
                .toList();
        return ResponseEntity.ok()
                .body(articles);
    }
    @GetMapping("/articles/{id}")
    public ResponseEntity<Article> getArticle(@PathVariable Long id, Model model){
        Article article=blogService.findById(id);
        return ResponseEntity.ok()
                .body(article);
    }
}

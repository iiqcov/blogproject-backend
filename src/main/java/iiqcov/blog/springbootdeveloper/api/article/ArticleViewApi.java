package iiqcov.blog.springbootdeveloper.api.article;

import iiqcov.blog.springbootdeveloper.domain.Article;
import iiqcov.blog.springbootdeveloper.dto.article.ArticleListViewResponse;
import iiqcov.blog.springbootdeveloper.dto.article.PagedArticleListViewResponse;
import iiqcov.blog.springbootdeveloper.service.article.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ArticleViewApi {
    private final BlogService blogService;

    @GetMapping("/")
    public void home(){

    }

    @GetMapping("/articles")
    public ResponseEntity<PagedArticleListViewResponse> getArticles(@PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        Page<ArticleListViewResponse> articles=blogService.findAll(pageable)
                .map(ArticleListViewResponse::new);
        PagedArticleListViewResponse pagedArticles=new PagedArticleListViewResponse(articles, articles.hasNext());
        return ResponseEntity.status(HttpStatus.OK)
                .body(pagedArticles);
    }

    @GetMapping("/folder/{folderName}")
    public ResponseEntity<PagedArticleListViewResponse> getArticlesByFolder(@PathVariable("folderName") String folderName, @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        Page<ArticleListViewResponse> articles=blogService.findAllArticles(folderName, pageable)
                .map(ArticleListViewResponse::new);
        PagedArticleListViewResponse pagedArticle=new PagedArticleListViewResponse(articles, articles.hasNext());
        return ResponseEntity.status(HttpStatus.OK)
                .body(pagedArticle);
    }

    @GetMapping("/article/{id}")
    public ResponseEntity<Article> getArticle(@PathVariable Long id, Model model){
        Article article=blogService.findById(id);
        return ResponseEntity.ok()
                .body(article);
    }
}

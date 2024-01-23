package iiqcov.blog.springbootdeveloper.api.article;

import iiqcov.blog.springbootdeveloper.domain.Article;
import iiqcov.blog.springbootdeveloper.dto.article.ArticleListViewResponse;
import iiqcov.blog.springbootdeveloper.dto.article.PagedArticleListViewResponse;
import iiqcov.blog.springbootdeveloper.service.article.BlogService;
import iiqcov.blog.springbootdeveloper.service.token.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ArticleViewApi {
    private final BlogService blogService;
    private final TokenService tokenService;

    @GetMapping("/")
    public void home(){

    }

    @GetMapping("/articles")
    public ResponseEntity<PagedArticleListViewResponse> getArticles(@PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, HttpServletRequest request){
        Page<ArticleListViewResponse> articles;
        if (tokenService.isMatched(request)){
            articles=blogService.findAllArticles(pageable)
                    .map(ArticleListViewResponse::new);
        } else{
            articles=blogService.findAllArticlesIfPublicStatusIsTrue(pageable)
                    .map(ArticleListViewResponse::new);
        }
        PagedArticleListViewResponse pagedArticles=new PagedArticleListViewResponse(articles, articles.hasNext());
        return ResponseEntity.status(HttpStatus.OK)
                .body(pagedArticles);
    }

    @GetMapping("/folder/{folderName}")
    public ResponseEntity<PagedArticleListViewResponse> getArticlesByFolder(@PathVariable("folderName") String folderName, @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, HttpServletRequest request){
        Page<ArticleListViewResponse> articles;
        if (tokenService.isMatched(request)){
            articles=blogService.findAllArticlesInFolder(folderName, pageable)
                    .map(ArticleListViewResponse::new);
        } else{
            articles=blogService.findAllArticlesIfPublicStatusIsTrueInFolder(folderName, pageable)
                    .map(ArticleListViewResponse::new);
        }

        PagedArticleListViewResponse pagedArticle=new PagedArticleListViewResponse(articles, articles.hasNext());
        return ResponseEntity.status(HttpStatus.OK)
                .body(pagedArticle);
    }

    @GetMapping("/article/{id}")
    public ResponseEntity<Article> getArticle(@PathVariable Long id, HttpServletRequest request){
        Article article=blogService.findById(id);
        if (article.isPublicStatus()){
            return ResponseEntity.ok()
                    .body(article);
        } else{
            if (tokenService.isMatched(request)){
                return ResponseEntity.ok()
                        .body(article);
            } else{
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(null);
            }
        }
    }
}

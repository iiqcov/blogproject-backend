package iiqcov.blog.springbootdeveloper.dto.article;

import iiqcov.blog.springbootdeveloper.domain.Article;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ArticleResponse {
    private final String title;
    private final String content;
    private final LocalDateTime created_at;
    private final LocalDateTime updated_at;

    public ArticleResponse(Article article){
        this.title=article.getTitle();
        this.content= article.getContent();
        this.created_at=article.getCreatedAt();
        this.updated_at=article.getUpdatedAt();
    }
}

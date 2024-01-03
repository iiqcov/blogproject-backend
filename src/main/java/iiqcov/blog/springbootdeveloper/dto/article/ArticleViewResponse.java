package iiqcov.blog.springbootdeveloper.dto.article;

import iiqcov.blog.springbootdeveloper.domain.Article;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class ArticleViewResponse {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

    public ArticleViewResponse(Article article){
        this.id=article.getId();
        this.content=article.getContent();
        this.title=article.getTitle();
        this.createdAt=article.getCreatedAt();
        this.updateAt=article.getUpdatedAt();
    }
}

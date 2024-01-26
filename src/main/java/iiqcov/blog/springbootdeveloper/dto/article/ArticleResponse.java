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
    private final boolean publicStatus;
    private final String folderList;
    private final String folderName;
    private final String thumbnailLink;

    public ArticleResponse(Article article, String folderList){
        this.title=article.getTitle();
        this.content= article.getContent();
        this.created_at=article.getCreatedAt();
        this.updated_at=article.getUpdatedAt();
        this.publicStatus=article.isPublicStatus();
        this.folderList=folderList;
        this.folderName=article.getFolder().getName();
        this.thumbnailLink=article.getThumbnailLink();
    }
}

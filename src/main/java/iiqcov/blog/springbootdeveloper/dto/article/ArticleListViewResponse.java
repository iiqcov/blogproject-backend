package iiqcov.blog.springbootdeveloper.dto.article;

import iiqcov.blog.springbootdeveloper.domain.Article;
import lombok.Getter;

@Getter
public class ArticleListViewResponse {
    private final Long id;
    private final String title;
    private final String folderName;
    private final String thumbnailLink;
    private final boolean publicStatus;

    public ArticleListViewResponse(Article article){
        this.id=article.getId();
        this.title= article.getTitle();
        this.folderName=article.getFolder().getName();
        this.thumbnailLink=article.getThumbnailLink();
        this.publicStatus=article.isPublicStatus();
    }
}

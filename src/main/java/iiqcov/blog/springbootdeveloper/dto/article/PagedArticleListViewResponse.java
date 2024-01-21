package iiqcov.blog.springbootdeveloper.dto.article;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
@AllArgsConstructor
public class PagedArticleListViewResponse {
    private Page<ArticleListViewResponse> page;
    private boolean hasNext;
}

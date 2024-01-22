package iiqcov.blog.springbootdeveloper.dto.article;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.Arrays;


@SpringBootTest
class AddArticleRequestTest {

    @Test
    void getFolerList() {
        AddArticleRequest request=new AddArticleRequest("title", "content", "/A Folder/B Folder/C Folder", null, true);

        assertThat(request.getFolerList()).isEqualTo(Arrays.asList("A Folder", "B Folder", "C Folder"));
    }
}
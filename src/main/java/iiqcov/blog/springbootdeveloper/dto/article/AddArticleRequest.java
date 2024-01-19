package iiqcov.blog.springbootdeveloper.dto.article;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddArticleRequest {
    private String title;
    private String content;
    private String folder;
    private String thumbnailLink;

    public List<String> getFolerList(){
        String cleanedFolder=folder;

        if (folder.startsWith("/")){
            cleanedFolder=folder.substring(1);
        }

        return Arrays.asList(cleanedFolder.split("/"));
    }
}

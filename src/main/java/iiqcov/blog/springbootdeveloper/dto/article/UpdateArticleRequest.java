package iiqcov.blog.springbootdeveloper.dto.article;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateArticleRequest {
    private String title;
    private String content;
    private String thumbnailLink;
    private boolean publicStatus;
    private String folder;

    public List<String> getFolerList(){
        String cleanedFolder=folder;

        if (folder.startsWith("/")){
            cleanedFolder=folder.substring(1);
        }

        return Arrays.asList(cleanedFolder.split("/"));
    }
}

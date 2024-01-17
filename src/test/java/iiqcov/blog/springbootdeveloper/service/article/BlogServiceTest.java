package iiqcov.blog.springbootdeveloper.service.article;

import iiqcov.blog.springbootdeveloper.common.FolderTest;
import iiqcov.blog.springbootdeveloper.dao.FolderFindDao;
import iiqcov.blog.springbootdeveloper.domain.Article;
import iiqcov.blog.springbootdeveloper.dto.article.AddArticleRequest;
import iiqcov.blog.springbootdeveloper.service.folder.FolderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BlogServiceTest extends FolderTest{
    @Autowired
    private BlogService blogService;

    @Autowired
    private FolderService folderService;

    @Autowired
    private FolderFindDao folderFindDao;

    @Test
    @DisplayName("이미 존재하는 폴더(3계층)에 새로운 게시글 생성")
    void existingFolder3rdNewArticle() {
        AddArticleRequest request=new AddArticleRequest("새로운 제목", "새로운 내용", "/A Folder/B Folder 1/C Folder");
        String savedFolder="C Folder";
        String savedArticleTitle="새로운 제목";

        Article savedArticle=blogService.save(request);

        assertThat(savedArticle).isNotNull();
        assertThat(savedArticle.getFolder().getName()).isEqualTo(savedFolder);
        assertThat(folderService.getArticleTitles(savedFolder)).contains(savedArticleTitle);
    }

    @Test
    @DisplayName("이미 존재하는 폴더(2계층)에 새로운 게시글 생성")
    void existingFolder2ndNewArticle(){
        AddArticleRequest request=new AddArticleRequest("새로운 제목", "새로운 내용", "/A Folder/B Folder 1");
        String savedFolder="B Folder 1";
        String savedArticleTitle="새로운 제목";

        Article savedArticle=blogService.save(request);

        assertThat(savedArticle).isNotNull();
        assertThat(savedArticle.getFolder().getName()).isEqualTo(savedFolder);
        assertThat(folderService.getArticleTitles(savedFolder)).contains(savedArticleTitle);

    }

    @Test
    @DisplayName("이미 존재하는 폴더(1계층)에 새로운 게시글 생성")
    void existingFolder1stNewArticle(){
        AddArticleRequest request=new AddArticleRequest("새로운 제목", "새로운 내용", "/A Folder");
        String savedFolder="A Folder";
        String savedArticleTitle="새로운 제목";

        Article savedArticle=blogService.save(request);

        assertThat(savedArticle).isNotNull();
        assertThat(savedArticle.getFolder().getName()).isEqualTo(savedFolder);
        assertThat(folderService.getArticleTitles(savedFolder)).contains(savedArticleTitle);

    }

    @Test
    @DisplayName("새로운 폴더(1계층)에 새로운 게시글 생성")
    void newFolder1stNewArticle(){
        AddArticleRequest request=new AddArticleRequest("새로운 제목", "새로운 내용", "/X Folder");
        String savedFolder="X Folder";
        String savedArticleTitle="새로운 제목";

        Article savedArticle=blogService.save(request);

        assertThat(savedArticle).isNotNull();
        assertThat(savedArticle.getFolder().getName()).isEqualTo(savedFolder);
        assertThat(folderService.getArticleTitles(savedFolder)).contains(savedArticleTitle);
        assertThat(folderService.findFolderByName(savedFolder).getParentFolders()).isEqualTo(null);

    }

    @Test
    @DisplayName("새로운 폴더(2계층)에 새로운 게시글 생성 - 1")
    void newFolder2ndNewArticle1(){
        AddArticleRequest request=new AddArticleRequest("새로운 제목", "새로운 내용", "/X Folder/Y Folder");
        String savedFolder="Y Folder";
        String parentFolder="X Folder";
        String savedArticleTitle="새로운 제목";

        Article savedArticle=blogService.save(request);

        assertThat(savedArticle).isNotNull();
        assertThat(savedArticle.getFolder().getName()).isEqualTo(savedFolder);
        assertThat(folderService.getArticleTitles(savedFolder)).contains(savedArticleTitle);
        assertThat(folderService.findFolderByName(savedFolder).getParentFolders()).isEqualTo(folderService.findFolderByName(parentFolder));
        assertThat(folderService.findFolderByName(parentFolder).getSubFolders()).contains(folderService.findFolderByName(savedFolder));
    }

    @Test
    @DisplayName("새로운 폴더(2계층)에 새로운 게시글 생성 - 2")
    void newFolder2ndNewArticle2(){
        AddArticleRequest request=new AddArticleRequest("새로운 제목", "새로운 내용", "/A Folder/Y Folder");
        String savedFolder="Y Folder";
        String parentFolder="A Folder";
        String savedArticleTitle="새로운 제목";

        Article savedArticle=blogService.save(request);

        assertThat(savedArticle).isNotNull();
        assertThat(savedArticle.getFolder().getName()).isEqualTo(savedFolder);
        assertThat(folderService.getArticleTitles(savedFolder)).contains(savedArticleTitle);
        assertThat(folderService.findFolderByName(savedFolder).getParentFolders()).isEqualTo(folderService.findFolderByName(parentFolder));
        assertThat(folderService.findFolderByName(parentFolder).getSubFolders()).contains(folderService.findFolderByName(savedFolder));
    }

    @Test
    @DisplayName("새로운 폴더(3계층)에 새로운 게시글 생성 - 1")
    void newFolder3rdNewArticle1(){
        AddArticleRequest request=new AddArticleRequest("새로운 제목", "새로운 내용", "/X Folder/Y Folder/Z Folder");
        String savedFolder="Z Folder";
        String parentFolder="Y Folder";
        String pparentFolder="X Folder";
        String savedArticleTitle="새로운 제목";

        Article savedArticle=blogService.save(request);

        assertThat(savedArticle).isNotNull();
        assertThat(savedArticle.getFolder().getName()).isEqualTo(savedFolder);
        assertThat(folderService.getArticleTitles(savedFolder)).contains(savedArticleTitle);
        assertThat(folderService.findFolderByName(savedFolder).getParentFolders()).isEqualTo(folderService.findFolderByName(parentFolder));
        assertThat(folderService.findFolderByName(parentFolder).getSubFolders()).contains(folderService.findFolderByName(savedFolder));

        assertThat(folderService.findFolderByName(pparentFolder).getSubFolders()).contains(folderService.findFolderByName(parentFolder));
        assertThat(folderService.findFolderByName(parentFolder).getParentFolders()).isEqualTo(folderService.findFolderByName(pparentFolder));
    }

    @Test
    @DisplayName("새로운 폴더(3계층)에 새로운 게시글 생성 - 2")
    void newFolder3rdNewArticle2(){
        AddArticleRequest request=new AddArticleRequest("새로운 제목", "새로운 내용", "/A Folder/Y Folder/Z Folder");
        String savedFolder="Z Folder";
        String parentFolder="Y Folder";
        String pparentFolder="A Folder";
        String savedArticleTitle="새로운 제목";

        Article savedArticle=blogService.save(request);

        assertThat(savedArticle).isNotNull();
        assertThat(savedArticle.getFolder().getName()).isEqualTo(savedFolder);
        assertThat(folderService.getArticleTitles(savedFolder)).contains(savedArticleTitle);
        assertThat(folderService.findFolderByName(savedFolder).getParentFolders()).isEqualTo(folderService.findFolderByName(parentFolder));
        assertThat(folderService.findFolderByName(parentFolder).getSubFolders()).contains(folderService.findFolderByName(savedFolder));

        assertThat(folderService.findFolderByName(pparentFolder).getSubFolders()).contains(folderService.findFolderByName(parentFolder));
        assertThat(folderService.findFolderByName(parentFolder).getParentFolders()).isEqualTo(folderService.findFolderByName(pparentFolder));

    }

    @Test
    @DisplayName("새로운 폴더(3계층)에 새로운 게시글 생성 - 3")
    void newFolder3rdNewArticle3(){
        AddArticleRequest request=new AddArticleRequest("새로운 제목", "새로운 내용", "/A Folder/B Folder 1/Z Folder");
        String savedFolder="Z Folder";
        String parentFolder="B Folder 1";
        String pparentFolder="A Folder";
        String savedArticleTitle="새로운 제목";

        Article savedArticle=blogService.save(request);

        assertThat(savedArticle).isNotNull();
        assertThat(savedArticle.getFolder().getName()).isEqualTo(savedFolder);
        assertThat(folderService.getArticleTitles(savedFolder)).contains(savedArticleTitle);
        assertThat(folderService.findFolderByName(savedFolder).getParentFolders()).isEqualTo(folderService.findFolderByName(parentFolder));
        assertThat(folderService.findFolderByName(parentFolder).getSubFolders()).contains(folderService.findFolderByName(savedFolder));
    }
}
package iiqcov.blog.springbootdeveloper.common;

import iiqcov.blog.springbootdeveloper.dao.FolderFindDao;
import iiqcov.blog.springbootdeveloper.domain.Article;
import iiqcov.blog.springbootdeveloper.domain.Folder;
import iiqcov.blog.springbootdeveloper.repository.BlogRepository;
import iiqcov.blog.springbootdeveloper.repository.FolderRepository;
import iiqcov.blog.springbootdeveloper.service.article.BlogService;
import iiqcov.blog.springbootdeveloper.service.folder.FolderService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public abstract class FolderTest {
    @Autowired
    protected BlogService blogService;

    @Autowired
    protected FolderService folderService;

    @Autowired
    protected FolderFindDao folderFindDao;

    @Autowired
    protected BlogRepository blogRepository;

    @Autowired
    protected FolderRepository folderRepository;


    @BeforeEach
    void setup(){
        // 폴더 데이터 생성
        Folder rootFolder = Folder.builder().name("A Folder").build();
        Folder childFolder1 = Folder.builder().name("B Folder 1").build();
        Folder childFolder2 = Folder.builder().name("B Folder 2").build();
        Folder grandchildFolder = Folder.builder().name("C Folder").build();

        // 폴더 데이터 저장
        rootFolder = folderRepository.save(rootFolder);
        childFolder1 = folderRepository.save(childFolder1);
        childFolder2 = folderRepository.save(childFolder2);
        grandchildFolder = folderRepository.save(grandchildFolder);

        // 폴더 트리 구조 설정
        childFolder1.setParentFolders(rootFolder);
        childFolder2.setParentFolders(rootFolder);
        grandchildFolder.setParentFolders(childFolder1);

        // 폴더 트리 구조 저장
        childFolder1 = folderRepository.save(childFolder1);
        childFolder2 = folderRepository.save(childFolder2);
        grandchildFolder = folderRepository.save(grandchildFolder);

        // 게시글 데이터 생성 및 저장
        Article article1 = Article.builder().title("제목 1").content("내용 1").folder(rootFolder).build();
        Article article2 = Article.builder().title("제목 2").content("내용 2").folder(childFolder1).build();
        Article article3 = Article.builder().title("제목 3").content("내용 3").folder(childFolder2).build();

        blogRepository.save(article1);
        blogRepository.save(article2);
        blogRepository.save(article3);

    }
}

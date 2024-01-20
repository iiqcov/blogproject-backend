package iiqcov.blog.springbootdeveloper.service.article;

import iiqcov.blog.springbootdeveloper.dao.FolderFindDao;
import iiqcov.blog.springbootdeveloper.domain.Article;
import iiqcov.blog.springbootdeveloper.domain.Folder;
import iiqcov.blog.springbootdeveloper.dto.article.AddArticleRequest;
import iiqcov.blog.springbootdeveloper.dto.article.UpdateArticleRequest;
import iiqcov.blog.springbootdeveloper.repository.BlogRepository;
import iiqcov.blog.springbootdeveloper.service.folder.FolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class BlogService {
    private final BlogRepository blogRepository;
    private final FolderFindDao folderFindDao;
    private final FolderService folderService;

    public Article save(AddArticleRequest request){
        List<String> folderList=request.getFolerList();

        Folder parentFolder=null;

        for (String folderName: folderList){
            if (parentFolder==null){
                Folder folder=folderService.findFolderByName(folderName);
                if (folder==null){
                    folderService.createFolder(folderName, null);
                    folder=folderService.findFolderByName(folderName);
                }
                parentFolder=folder;
            } else{
                List<String> subFolders=folderFindDao.findSubfolders(parentFolder.getName());
                if (!subFolders.contains(folderName)){
                    folderService.createFolder(folderName, parentFolder.getName());
                }
                parentFolder=folderService.findFolderByName(folderName);
            }
        }

        Article article=Article.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .folder(parentFolder)
                .thumbnailLink(request.getThumbnailLink())
                .build();
        parentFolder.getArticles().add(article);

        return blogRepository.save(article);
    }

    public List<Article> findAll(){
        return blogRepository.findAll();
    }

    public Article findById(Long id){
        return blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    public List<Article> findAllArticles(String folderName){
        Folder folder=folderService.findFolderByName(folderName);
        return findArticlesInSubFolders(folder);
    }

    private List<Article> findArticlesInSubFolders(Folder folder){
        List<Article> articles=new ArrayList<>(blogRepository.findByFolder(folder));
        for (Folder subFolder: folder.getSubFolders()){
            articles.addAll(findArticlesInSubFolders(subFolder));
        }
        return articles;
    }

    public void delete(Long id){
        Article article=blogRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("not found : "+id));
        authorizeArticleAuthor(article);
        blogRepository.delete(article);
    }

    @Transactional
    public Article update(Long id, UpdateArticleRequest request){
        Article article=blogRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("not found: "+id));
        authorizeArticleAuthor(article);
        article.update(request.getTitle(), request.getContent(), request.getThumbnailLink());
        return article;
    }

    private static void authorizeArticleAuthor(Article article){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();

        boolean isAdmin = authorities.contains(new SimpleGrantedAuthority("ROLE_USER"));
        if (!isAdmin){
            throw new IllegalArgumentException("not authorized");
        }
    }

}

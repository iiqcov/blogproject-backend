package iiqcov.blog.springbootdeveloper.service.folder;

import iiqcov.blog.springbootdeveloper.domain.Article;
import iiqcov.blog.springbootdeveloper.domain.Folder;
import iiqcov.blog.springbootdeveloper.repository.FolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FolderService {
    private final FolderRepository folderRepository;

    public List<Folder> getFolders(){
        return folderRepository.findAll();
    }

    public Folder findFolderByName(String name){
        return folderRepository.findByName(name);
    }

    public List<String> getArticleTitles(String folderName){
        Folder folder=folderRepository.findByName(folderName);
        return folder.getArticles().stream()
                .map(Article::getTitle)
                .collect(Collectors.toList());
    }

    public void createFolder(String folderName, String parentFolderName){
        Folder folder;
        if (parentFolderName==null){
            folder=Folder.builder()
                    .name(folderName)
                    .subFolders(new ArrayList<>())
                    .build();
        } else{
            Folder parent=findFolderByName(parentFolderName);
            if (parent==null){
                throw new RuntimeException("Parent folder not found");
            }
            folder=Folder.builder()
                    .name(folderName)
                    .parentFolders(parent)
                    .subFolders(new ArrayList<>())
                    .build();
            parent.getSubFolders().add(folder);
        }
        folderRepository.save(folder);
    }
}

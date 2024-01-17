package iiqcov.blog.springbootdeveloper.api.folder;

import iiqcov.blog.springbootdeveloper.domain.Folder;
import iiqcov.blog.springbootdeveloper.service.folder.FolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FolderViewApi {
    private final FolderService folderService;

    @GetMapping("/folders")
    public ResponseEntity<List<Folder>> getFolders(){
        List<Folder> folderList=folderService.getFolders();
        return ResponseEntity.status(HttpStatus.OK)
                .body(folderList);
    }
}

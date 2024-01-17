package iiqcov.blog.springbootdeveloper.dao;

import iiqcov.blog.springbootdeveloper.domain.Folder;
import iiqcov.blog.springbootdeveloper.repository.FolderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
class FolderFindDaoTest {
    @Autowired
    private FolderFindDao folderFindDao;

    @Autowired
    private FolderRepository folderRepository;

    @Test
    void findSubfolders() {
        Folder parentFolder = Folder.builder()
                .name("parentFolder")
                .subFolders(new ArrayList<>())
                .build();

        Folder subFolder1 = Folder.builder()
                .name("subFolder1")
                .parentFolders(parentFolder)
                .build();

        Folder subFolder2 = Folder.builder()
                .name("subFolder2")
                .parentFolders(parentFolder)
                .build();

        parentFolder.setSubFolders(Arrays.asList(subFolder1, subFolder2));

        folderRepository.save(parentFolder);
        folderRepository.save(subFolder1);
        folderRepository.save(subFolder2);

        List<String> subFolders=folderFindDao.findSubfolders(parentFolder.getName());

        assertThat(subFolders).isEqualTo(Arrays.asList(subFolder1.getName(), subFolder2.getName()));
    }
}
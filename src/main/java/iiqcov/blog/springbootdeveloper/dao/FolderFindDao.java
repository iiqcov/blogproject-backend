package iiqcov.blog.springbootdeveloper.dao;


import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static iiqcov.blog.springbootdeveloper.domain.QFolder.folder;

@Service
@RequiredArgsConstructor
public class FolderFindDao {
    private final JPAQueryFactory queryFactory;

    public List<String> findSubfolders(String parentFolderName){
        return queryFactory
                .select(folder.name).from(folder)
                .where(folder.parentFolders.name.eq(parentFolderName))
                .fetch();
    }
}

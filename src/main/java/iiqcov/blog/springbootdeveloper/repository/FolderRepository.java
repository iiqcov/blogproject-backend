package iiqcov.blog.springbootdeveloper.repository;

import iiqcov.blog.springbootdeveloper.domain.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Long> {
    Folder findByName(String name);
}

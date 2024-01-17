package iiqcov.blog.springbootdeveloper.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "folder")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Folder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "parentFolders")
    private List<Folder> subFolders=new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Folder parentFolders;

    @OneToMany(mappedBy = "folder")
    private List<Article> articles=new ArrayList<>();

    public List<Article> getArticles(){
        if (articles==null){
            articles=new ArrayList<>();
        }
        return articles;
    }

    public List<Folder> getSubFolders(){
        if (subFolders==null){
            subFolders=new ArrayList<>();
        }
        return subFolders;
    }
}

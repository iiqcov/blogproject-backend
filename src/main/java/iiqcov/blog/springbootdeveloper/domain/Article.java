package iiqcov.blog.springbootdeveloper.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "article")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false, length = 3000)
    private String content;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "folder_id")
    private Folder folder;

    @Column(name = "thumbnail")
    private String thumbnailLink;

    @Column(name = "public_status")
    private boolean publicStatus;

    @Builder
    public Article(String title, String content, Folder folder, String thumbnailLink, boolean publicStatus){
        this.title=title;
        this.content=content;
        this.folder=folder;
        this.thumbnailLink=thumbnailLink;
        this.publicStatus=publicStatus;
    }

    public void update(String title, String content, String thumbnailLink, boolean publicStatus){
        this.title=title;
        this.content=content;
        this.thumbnailLink=thumbnailLink;
        this.publicStatus=publicStatus;
    }
}

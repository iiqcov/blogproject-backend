package iiqcov.blog.springbootdeveloper.domain;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "ip", nullable = false)
    private String ipAddress;

    @Builder
    public User(String ipAddress){
        this.ipAddress=ipAddress;
    }
}

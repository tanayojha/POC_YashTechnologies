package org.yash.yashtalks.entity;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.util.Objects;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author tanay.ojha
 *
 */

@Entity
@Table(name = "posts")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private String postPhoto;
    private Integer likeCount;
    private Integer commentCount;
    private Integer shareCount;

    @Column(nullable = false)
    private Boolean isTypeShare;

    @CreationTimestamp
    private Date dateCreated;

    @UpdateTimestamp
    private Date dateLastModified;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "post")
    private List<Comment> postComments = new ArrayList<>();

//    @ManyToMany
//    @JoinTable(name = "post_likes", joinColumns = @JoinColumn(name = "post_id"),
//            inverseJoinColumns = @JoinColumn(name = "liker_id"))
//    private List<User> likeList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "shared_post_id")
    private Post sharedPost;

    @JsonIgnore
    @OneToMany(mappedBy = "sharedPost")
    private List<Post> shareList = new ArrayList<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(id, post.id) && Objects.equals(author, post.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author);
    }

}

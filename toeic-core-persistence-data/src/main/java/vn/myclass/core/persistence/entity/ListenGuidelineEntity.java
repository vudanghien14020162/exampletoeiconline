package vn.myclass.core.persistence.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "listenguideline")
public class ListenGuidelineEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long listenGuidelineId;

    @Column(name = "title")
    private String title;

    @Column(name = "image")
    private String image;

    @Column(name = "content")
    private String content;

    @Column(name = "createddate")
    private Timestamp createdDate;

    @Column(name = "modifileday")
    private Timestamp modifiedDay;

    @OneToMany(mappedBy = "listenGuidelineEntity", fetch = FetchType.LAZY)
    private List<CommentEntity> commentEntityList;

    public Long getListenGuidelineId() {
        return listenGuidelineId;
    }

    public void setListenGuidelineId(Long listenGuidelineId) {
        this.listenGuidelineId = listenGuidelineId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Timestamp getModifiedDay() {
        return modifiedDay;
    }

    public void setModifiedDay(Timestamp modifiedDay) {
        this.modifiedDay = modifiedDay;
    }

    public List<CommentEntity> getCommentEntityList() {
        return commentEntityList;
    }

    public void setCommentEntityList(List<CommentEntity> commentEntityList) {
        this.commentEntityList = commentEntityList;
    }
}

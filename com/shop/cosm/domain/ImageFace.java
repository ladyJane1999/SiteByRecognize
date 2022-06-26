package com.shop.cosm.domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class ImageFace  {


    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;


    private String filename;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;


    public ImageFace() {
    }

    public ImageFace(User user, String filename) {
        this.author = user;

        this.filename = filename;
    }



    public String getAuthorName() {
        return author != null ? author.getUsername() : "<none>";
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public List<ImageFace> DeleteByFilename(String filename) {
        return null;
    }

    public String getFilename() {
        return filename;
    }

    public String setFilename(String filename) {

        this.filename = filename;

        return filename;
    }
}

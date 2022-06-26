package com.shop.cosm.domain;

import com.machinezoo.sourceafis.FingerprintTemplate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageFinger  {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String filename;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    public ImageFinger(User user, String filename) {
        this.author = user;

        this.filename = filename;
    }

    public ImageFinger(org.springframework.security.core.userdetails.User user, String originalFilename) {
    }
}

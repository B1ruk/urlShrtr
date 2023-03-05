package io.b1ruk.start.urlShrtr.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.util.Date;

@Data
@Table("")
@NoArgsConstructor
public class UrlEntity implements Serializable {

    @PrimaryKey
    private String shortenedUrl;
    private String url;
    private Date CreatedOn;

    public UrlEntity(String shortenedUrl, String url) {
        this.shortenedUrl = shortenedUrl;
        this.url = url;
        this.CreatedOn = new Date();
    }
}
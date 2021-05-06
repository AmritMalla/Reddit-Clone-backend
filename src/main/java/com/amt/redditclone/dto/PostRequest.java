package com.amt.redditclone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Amrit Malla
 * date : 04/26/2021
 * time : 4:09 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {

    private Long id;

    private String name;

    private String subredditName;

    private String url;

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

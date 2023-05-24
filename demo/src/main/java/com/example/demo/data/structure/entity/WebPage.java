package com.example.demo.data.structure.entity;

import lombok.Data;

/**
 * @description:
 * @author: jjxu
 * @time: 2023/1/30
 * @package: com.example.demo.data.structure.entity
 */
@Data
public class WebPage {

    private int content;

    private String url;

    public WebPage(int content, String url) {
        this.content = content;
        this.url = url;
    }
}

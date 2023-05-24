package com.example.demo.data.structure.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: jjxu
 * @time: 2023/1/30
 * @package: com.example.demo.data.structure.entity
 */
@Data
public class People {

    private String name;

    private List<WebPage> webPages = new ArrayList<>();

    public People(String name) {
        this.name = name;
    }

    public void visit(WebPage webPage){
        webPages.add(webPage);
    }
}

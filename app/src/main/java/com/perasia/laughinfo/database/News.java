package com.perasia.laughinfo.database;


import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class News extends DataSupport {

    //不写入数据库，可设置 protected public default
    private int id;
    private String title;
    private String content;
    private Date publishDate;
    private int commentCount;
    private int introduceCount;

    private List<Introduction> introductions = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public List<Introduction> getIntroductions() {
        return introductions;
    }

    public void setIntroductions(List<Introduction> introductions) {
        this.introductions = introductions;
    }

    public int getIntroduceCount() {
        return introduceCount;
    }

    public void setIntroduceCount(int introduceCount) {
        this.introduceCount = introduceCount;
    }
}

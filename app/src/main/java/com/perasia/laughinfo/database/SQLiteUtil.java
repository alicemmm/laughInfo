package com.perasia.laughinfo.database;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.Date;
import java.util.List;

public class SQLiteUtil {

    public static void createSQLite() {
        SQLiteDatabase sd = Connector.getDatabase();
    }

    public static boolean insert() {
        News news = new News();
        news.setTitle("最新新闻");
        news.setContent("科比今天退役了");
        news.setPublishDate(new Date());
        return news.save();
    }

    public static boolean insertTwo() {
        Introduction introduction1 = new Introduction();
        introduction1.setDigest("你好");
        introduction1.setGuide("知道1");
        introduction1.save();
        Introduction introduction2 = new Introduction();
        introduction2.setDigest("你不好啊");
        introduction2.setGuide("知道2");
        introduction2.save();

        News news = new News();
        news.getIntroductions().add(introduction1);
        news.getIntroductions().add(introduction2);
        news.setTitle("第二个新闻");
        news.setContent("我的杯子像斑点狗");
        news.setPublishDate(new Date());
        news.setIntroduceCount(news.getIntroductions().size());
        return news.save();
    }

    public static void update() {
        ContentValues values = new ContentValues();
        values.put("title", "今天发布iPhone 7");
        DataSupport.updateAll(News.class, values, "title=?", "最新新闻");
    }

    public static void delete() {
        DataSupport.deleteAll(News.class, "title=?", "最新新闻");
    }

    public static List<News> serach() {
        List<News> newsList = DataSupport.select("title", "content").where("id > ?", "0").find(News.class);
        return newsList;
    }
}

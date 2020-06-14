package com.mattjohnson.teai7_2.dao;

import com.mattjohnson.teai7_2.model.Article;
import com.mattjohnson.teai7_2.model.News;

import java.util.List;

public interface NewsDao {

    void saveNews(News news);

    void deleteNews();

    List<Article> getArticles();

    Article getArticleById(int id);

    void updateArticle(Article updatedArticle, int id);
}


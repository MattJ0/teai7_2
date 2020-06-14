package com.mattjohnson.teai7_2.service;

import com.mattjohnson.teai7_2.model.Article;

import java.util.List;

public interface NewsService {

    void saveNews(String country, String category);

    List<Article> loadNews();

    Article loadArticle(int id);

    void deleteNews();

    void updateNews(Article updatedArticle, int id);


}

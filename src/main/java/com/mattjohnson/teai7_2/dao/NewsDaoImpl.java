package com.mattjohnson.teai7_2.dao;

import com.mattjohnson.teai7_2.model.Article;
import com.mattjohnson.teai7_2.model.News;
import com.mattjohnson.teai7_2.model.Source;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class NewsDaoImpl implements NewsDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public NewsDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

    }

    @Override
    public void saveNews(News news) {
        for (Article article : news.getArticles()) {
            String sql = "INSERT INTO articles VALUES (?, ?, ?, ?, ?, ?, ?, ?, article_id)";
            jdbcTemplate.update(sql, article.getSource().getName(), article.getAuthor(), article.getTitle(),
                    article.getDescription(), article.getUrl(), article.getUrlToImage(), article.getPublishedAt(),
                    article.getContent());
        }
    }

    @Override
    public void deleteNews() {
        String sql = "DELETE FROM articles";
        jdbcTemplate.update(sql);
    }

    @Override
    public List<Article> getArticles() {
        String sql = "SELECT * FROM articles";
        List<Article> articleList = new ArrayList<>();
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        maps.stream().forEach(element -> articleList.add(new Article(
                new Source(String.valueOf(element.get("sourceName"))),
                String.valueOf(element.get("author")),
                String.valueOf(element.get("title")),
                String.valueOf(element.get("a_description")),
                String.valueOf(element.get("url")),
                String.valueOf(element.get("urlToImage")),
                String.valueOf(element.get("publishedAt")),
                String.valueOf(element.get("content")),
                (Integer) element.get("article_id")

        )));

        return articleList;
    }

    @Override
    public Article getArticleById(int id) {
        String sql = "SELECT * FROM articles WHERE article_id=" + id;

        return jdbcTemplate.queryForObject(sql, (resultSet, rowNum) -> new Article(new Source(resultSet.getString("sourceName")),
                resultSet.getString("author"), resultSet.getString("title"), resultSet.getString("a_description"),
                resultSet.getString("url"), resultSet.getString("urlToImage"), resultSet.getString("publishedAt"),
                resultSet.getString("content"), id));
    }

    @Override
    public void updateArticle(Article updatedArticle, int id) {
        String sql = "UPDATE articles SET sourceName=?, author=?, title=?, a_description=?" +
                ", publishedAt=?, content=? WHERE article_id=?";
        jdbcTemplate.update(sql, updatedArticle.getSource().getName(), updatedArticle.getAuthor(),
                updatedArticle.getTitle(), updatedArticle.getDescription(), updatedArticle.getPublishedAt(),
                updatedArticle.getContent(), id);

    }
}

package com.mattjohnson.teai7_2.service;

import com.mattjohnson.teai7_2.dao.NewsDao;
import com.mattjohnson.teai7_2.model.Article;
import com.mattjohnson.teai7_2.model.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {


    private String baseUrl;

    private String newsApiKey;

    private List<String> categories;
    private List<String> countries;

    private NewsDao newsDao;

    @Autowired
    public NewsServiceImpl(@Value("${baseUrl}") String baseUrl, @Value("${apiKey}") String newsApiKey, NewsDao newsDao) {
        this.baseUrl = baseUrl;
        this.newsApiKey = newsApiKey;
        this.categories = new ArrayList<>();
        this.countries = new ArrayList<>();
        this.newsDao = newsDao;

        categories.add("business");
        categories.add("entertainment");
        categories.add("general");
        categories.add("health");
        categories.add("science");
        categories.add("sports");
        categories.add("technology");

        countries.add("pl");
        countries.add("us");
        countries.add("gb");
        countries.add("de");
        countries.add("fr");
        countries.add("it");

    }

    private News getTopHeadlinesNews(String country, String category) {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<News> news = restTemplate.exchange(getTopHeadlinesRequestUrl(country, category), HttpMethod.GET,
                HttpEntity.EMPTY,
                News.class);

        return news.getBody();
    }

    private String getTopHeadlinesRequestUrl(String country, String category) {

        return baseUrl + "top-headlines?country=" + country + "&category=" + category + "&apiKey=" +
                newsApiKey;
    }


    public void saveNews(String country, String category) {
        newsDao.saveNews(getTopHeadlinesNews(country, category));
    }


    public List<Article> loadNews() {
        return newsDao.getArticles();
    }

    @Override
    public Article loadArticle(int id) {
        return newsDao.getArticleById(id);
    }

    public void deleteNews() {
        newsDao.deleteNews();
    }

    @Override
    public void updateNews(Article updatedArticle, int id) {
        newsDao.updateArticle(updatedArticle, id);
    }

    public List<String> getCategories() {
        return categories;
    }

    public List<String> getCountries() {
        return countries;
    }
}

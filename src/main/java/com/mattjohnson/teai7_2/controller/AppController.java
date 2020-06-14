package com.mattjohnson.teai7_2.controller;

import com.mattjohnson.teai7_2.model.Article;
import com.mattjohnson.teai7_2.service.CountryCategoryPair;
import com.mattjohnson.teai7_2.service.NewsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AppController {

    private NewsServiceImpl newsService;

    @Autowired
    public AppController(NewsServiceImpl newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/news-app")
    public String getNewsApp(Model model) {
        model.addAttribute("categories", newsService.getCategories());
        model.addAttribute("countries", newsService.getCountries());
        model.addAttribute("ccpair", new CountryCategoryPair());

        return "news-app";
    }

    @GetMapping("/search")
    public String getArticles(Model model, @ModelAttribute(name = "ccpair") CountryCategoryPair countryCategoryPair) {
        model.addAttribute("categories", newsService.getCategories());
        model.addAttribute("countries", newsService.getCountries());
        model.addAttribute("ccpair", new CountryCategoryPair());

        newsService.deleteNews();
        newsService.saveNews(countryCategoryPair.getCountryField(), countryCategoryPair.getCategoryField());

        model.addAttribute("articles", newsService.loadNews());


        return "search-result";
    }

    @GetMapping("/search-result")
    public String getBackToResult(Model model) {
        model.addAttribute("articles", newsService.loadNews());
        model.addAttribute("categories", newsService.getCategories());
        model.addAttribute("countries", newsService.getCountries());
        model.addAttribute("ccpair", new CountryCategoryPair());
        return "search-result";
    }

    @GetMapping("/update-news/{id}")
    public String getUpdatePage(Model model, @PathVariable int id) {
        model.addAttribute("new_article", new Article());
        model.addAttribute("edit_article", newsService.loadArticle(id));
        return "update-news";
    }


    @PostMapping("/save-update/{id}")
    public String saveUpdate(Article updatedArticle, @PathVariable int id) {
        newsService.updateNews(updatedArticle, id);

        return "redirect:/search-result";
    }


}

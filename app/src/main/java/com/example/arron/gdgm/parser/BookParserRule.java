package com.example.arron.gdgm.parser;

import com.example.arron.gdgm.GdgmApplication;
import com.example.arron.gdgm.R;
import com.example.arron.gdgm.model.BookModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arron on 2017/4/21.
 */

public class BookParserRule extends BaseParserRule<List<BookModel>> {
    @Override
    public List<BookModel> parseRule(String html) {
        List<BookModel> bookModels = new ArrayList<>();
        Document document = Jsoup.parse(html);
        Elements elements = document.select("div#right_m table tr");
        for (Element element : elements) {
            Elements book = element.select("td a");
            if (book.isEmpty()) {
                continue;
            }
            if (!book.first().hasAttr("href") || book.size() != 3) {
                continue;
            }
            BookModel bookModel = new BookModel();
            for (int i = 0; i < book.size(); i++) {
                switch (i) {
                    case 0 :
                        bookModel.setUrlDetail(GdgmApplication.getContext().getString(R.string.library_home_url) +
                                "/chaxun/" + book.get(i).attr("href"));
                        bookModel.setClassification(book.get(i).text());
                        break;
                    case 1:
                        bookModel.setBookName(book.get(i).text());
                        break;
                    case 2:
                        bookModel.setAuthor(book.get(i).text());
                        break;
                }
            }
            bookModels.add(bookModel);
        }
        return bookModels;
    }
}

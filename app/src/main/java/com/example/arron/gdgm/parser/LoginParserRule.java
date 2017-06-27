package com.example.arron.gdgm.parser;

import com.example.arron.gdgm.model.LoginResultModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by Arron on 2017/4/13.
 */

public class LoginParserRule extends BaseParserRule<LoginResultModel> {
    @Override
    public LoginResultModel parseRule(String html) {
        LoginResultModel loginResultModel = new LoginResultModel();
        Document document = Jsoup.parse(html);
        Elements elements = document.select("span#xhxm");
        if (elements.isEmpty()) {
            Elements jsElements = document.select("script");
            for (Element element : jsElements) {
                String text = element.data();
                if (text.contains("alert(")) {
                    loginResultModel.setLoginSuccess(false);
                    loginResultModel.setFailedText(text.substring(text.indexOf("alert('")+"alert('".length(), text.lastIndexOf("')")));
                    return loginResultModel;
                }
            }
        } else {
            loginResultModel.setSuccessText(elements.first().text());
            loginResultModel.setLoginSuccess(true);
            return loginResultModel;
        }
        return loginResultModel;
    }
}

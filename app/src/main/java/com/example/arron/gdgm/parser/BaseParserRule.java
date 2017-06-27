package com.example.arron.gdgm.parser;

/**
 * Created by Arron on 2017/4/12.
 */

public abstract class BaseParserRule<T> {
    public abstract T parseRule(String html);
}

package wf.my.samlib.service.components;

import wf.my.samlib.entity.Author;

public interface AuthorPageParser {
    Author parsePage(String pageString);
}

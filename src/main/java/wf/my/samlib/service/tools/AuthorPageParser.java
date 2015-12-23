package wf.my.samlib.service.tools;

import wf.my.samlib.entity.Author;

public interface AuthorPageParser {
    Author parsePage(String pageString);
}

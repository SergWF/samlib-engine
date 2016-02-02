package wf.my.samlib;

import wf.my.samlib.entity.Author;
import wf.my.samlib.entity.Writing;
import wf.my.samlib.entity.WritingHistoryItem;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class TestHelper {

    public static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

    public static Writing getWriting(final String url, final String name, final int size, final String description, final Date updateDate){
        Writing writing = new Writing();
        writing.setUrl(url);
        writing.setName(name);
        writing.setSize(size);
        writing.setDescription(description);
        writing.setUpdateDate(updateDate);
        return writing;
    }

    public static Writing copyWriting(Writing writing){
        Writing copy = new Writing();
        copy.setName(writing.getName());
        copy.setUpdateDate(writing.getUpdateDate());
        copy.setAuthor(writing.getAuthor());
        copy.setDescription(writing.getDescription());
        copy.setSize(writing.getSize());
        copy.setId(writing.getId());
        copy.setUrl(writing.getUrl());
        for(WritingHistoryItem item: writing.getHistory()){
            copy.getHistory().add(copyHistoryItem(item));
        }
        return copy;
    }

    public static WritingHistoryItem getHistoryItem(String name, Integer size, String description, Date changeDate){
        WritingHistoryItem item = new WritingHistoryItem();
        item.setUpdateDate(changeDate);
        item.setName(name);
        item.setSize(size);
        item.setDescription(description);
        return item;
    }

    public static WritingHistoryItem copyHistoryItem(WritingHistoryItem item){
        WritingHistoryItem copy = new WritingHistoryItem();
        copy.setUpdateDate(item.getUpdateDate());
        copy.setName(item.getName());
        copy.setSize(item.getSize());
        copy.setDescription(item.getDescription());
        return copy;
    }

    public static Author getAuthor(final String url, final String name, final Writing... writings){
        Author author = new Author();
        author.setUrl(url);
        author.setName(name);
        author.getWritings().addAll(Arrays.asList(writings));
        return author;
    }

}

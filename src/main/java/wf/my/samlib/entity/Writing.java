package wf.my.samlib.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Writing implements UpdateDateComparable {
    private String id;
    private String url;
    private Author author;
    private String name;
    private Date updateDate;
    private Integer size = 0;
    private String description = "";
    private String section;
    private Set<WritingHistoryItem> history = new HashSet<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<WritingHistoryItem> getHistory() {
        return history;
    }

    public void setHistory(Set<WritingHistoryItem> history) {
        this.history = history;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }
}

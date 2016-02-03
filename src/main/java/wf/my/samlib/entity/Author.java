package wf.my.samlib.entity;

import wf.my.samlib.comparator.UpdateDateComparable;
import wf.my.samlib.comparator.UpdateDateComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Author implements UpdateDateComparable {
    private String url;
    private String name;
    private List<Writing> writings = new ArrayList<>();
    private Date lastCheckedDate;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public Date getUpdateDate() {
        if(null ==writings || writings.isEmpty()){
            return null;
        }
        return Collections.max(writings, new UpdateDateComparator()).getUpdateDate();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Writing> getWritings() {
        return writings;
    }

    public void setWritings(List<Writing> writings) {
        this.writings = writings;
    }

    public Date getLastCheckedDate() {
        return lastCheckedDate;
    }

    public void setLastCheckedDate(Date lastCheckedDate) {
        this.lastCheckedDate = lastCheckedDate;
    }
}

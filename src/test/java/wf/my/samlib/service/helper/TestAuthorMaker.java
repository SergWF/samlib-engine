package wf.my.samlib.service.helper;

import wf.my.samlib.entity.Author;
import wf.my.samlib.entity.Writing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class TestAuthorMaker {
    private String url;
    private Date updatedDate;
    private List<Writing> writings = new ArrayList<>();
    private Date lastChangedDate;


    public TestAuthorMaker withUrl(String url){
        this.url = url;
        return this;
    }

    public TestAuthorMaker withUpdatedDate(Date updatedDate){
        this.updatedDate = updatedDate;
        return this;
    }

    public TestAuthorMaker withLastChangedDate(Date lastChangedDate){
        this.lastChangedDate = lastChangedDate;
        return this;
    }

    public TestAuthorMaker withWritings(Writing... writings){
        this.writings.addAll(Arrays.asList(writings));
        return this;
    }

    public Author build(){
        return new Author() {
            @Override
            public String getUrl() {
                return url;
            }

            @Override
            public Date getUpdateDate() {
                return updatedDate;
            }

            @Override
            public List<Writing> getWritings() {
                return writings;
            }

            @Override
            public Date getLastChangedDate() {
                return lastChangedDate;
            }
        };
    }
}

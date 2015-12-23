package wf.my.samlib.service.helper;

import wf.my.samlib.entity.Author;
import wf.my.samlib.entity.Writing;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class TestHelper {

    public static Writing getWriting(final String url, final String name, final String size, final String description, final Date updateDate){
       return new Writing() {
            Date updDate = updateDate;
            @Override
            public String getUrl() {
                return url;
            }

            @Override
            public String getName() {
                return name;
            }

            @Override
            public Date getUpdateDate() {
                return updDate;
            }

           @Override
           public void setUpdatedDate(Date date) {
               this.updDate = date;
           }

           @Override
            public String getSize() {
                return size;
            }

            @Override
            public String getDescription() {
                return description;
            }
        };
    }

    public static Author getAuthor(final String url, final Date updatedDate, final Date lastChangedDate, final Writing... writings){
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
                return Arrays.asList(writings);
            }

            @Override
            public Date getLastChangedDate() {
                return lastChangedDate;
            }
        };
    }

}

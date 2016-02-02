package wf.my.samlib.entity;

import java.util.Date;

public class UpdateState {
    private Date startDate;
    private Date endDate;
    private int authorsTotal;
    private int authorsChecked;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getAuthorsTotal() {
        return authorsTotal;
    }

    public void setAuthorsTotal(int authorsTotal) {
        this.authorsTotal = authorsTotal;
    }

    public int getAuthorsChecked() {
        return authorsChecked;
    }

    public void setAuthorsChecked(int authorsChecked) {
        this.authorsChecked = authorsChecked;
    }

    public boolean isInProcess(){
        return null != startDate && null == endDate;
    }

    public void increaseChecked(){
        authorsChecked++;
    }
}

package wf.my.samlib.comparator;

import wf.my.samlib.entity.UpdateDateComparable;
import wf.my.samlib.entity.Writing;

import java.util.Comparator;

public class UpdateDateComparator implements Comparator<UpdateDateComparable> {

    @Override
    public int compare(UpdateDateComparable o1, UpdateDateComparable o2) {
        if(null == o1 || null ==o1.getUpdateDate()){
            if(null == o2){
                return 0;
            } else{
                return -1;
            }
        } else{
            if(null == o2 || null == o2.getUpdateDate()){
                return 1;
            } else{
                return o1.getUpdateDate().compareTo(o2.getUpdateDate());
            }
        }
    }
}

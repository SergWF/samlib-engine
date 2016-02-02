package wf.my.samlib.service;

import wf.my.samlib.entity.UpdateState;

public interface UpdatingService {
    boolean updateAuthors();
    UpdateState getUpdateState();
}

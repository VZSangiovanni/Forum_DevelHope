package co.develhope.forum.cleandatabase;

import co.develhope.forum.cleandatabase.CleanDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


// ONLY FOR TEST, CLEAN DATABASE
@Service
public class CleanService {

    @Autowired
    CleanDAO cleanDAO;

    public void CleanDB(){
        cleanDAO.cleanDB();
    }
}

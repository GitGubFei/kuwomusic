package com.example.demo.service.impl;

import com.example.demo.dao.MyMusicDao;
import com.example.demo.logic.service.MyServiceLogic;
import com.example.demo.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class myServiceImpl implements MyService {
    @Autowired
    MyMusicDao myMusicDao;
    private MyServiceLogic myServiceLogic = new MyServiceLogic();
    @Override
    public List<String> getMusicList() {
        return myServiceLogic.getMusicList(myMusicDao);
    }
}

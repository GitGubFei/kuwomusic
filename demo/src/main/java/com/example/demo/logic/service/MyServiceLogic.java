package com.example.demo.logic.service;

import com.example.demo.dao.MyMusicDao;

import java.util.List;

public class MyServiceLogic {
    public List<String> getMusicList(MyMusicDao myMusicDao){
        return  myMusicDao.getMusicList();
    }
}

package com.example.demo.dao;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MyMusicDao {
    public List<String> getMusicList();
}

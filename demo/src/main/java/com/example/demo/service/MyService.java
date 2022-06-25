package com.example.demo.service;

import com.example.demo.dao.MyMusicDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MyService {
    public List<String> getMusicList();
}

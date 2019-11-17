package com.renee.jin.board.service;

import java.util.List;

import com.renee.jin.model.BoardBean;


public interface SimsimService {
    public List<BoardBean> getbordList(BoardBean bean);
    public int boardInsert( BoardBean bean );
    public int boardUpdate( BoardBean bean );
}

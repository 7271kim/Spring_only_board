package com.renee.jin.boarddao;

import java.util.List;

import com.renee.jin.model.BoardBean;

public interface SimsimDao {
    public List<BoardBean> getbordList(BoardBean bean);
    public int boardInsert( BoardBean bean );
    public int boardUpdate( BoardBean bean );
}

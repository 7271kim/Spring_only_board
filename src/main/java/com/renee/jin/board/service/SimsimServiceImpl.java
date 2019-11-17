package com.renee.jin.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.renee.jin.boarddao.SimsimDao;
import com.renee.jin.model.BoardBean;

@Service
public class SimsimServiceImpl implements SimsimService {
    @Autowired
    SimsimDao simsimDao;
    
    /*
     * @Transactional
     * 트랜잭션 관련 옵션! 
     * 
     */
    @Override
    @Transactional(readOnly = true)
    public List<BoardBean> getbordList(BoardBean bean) {
        return simsimDao.getbordList(bean);
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public int boardInsert(BoardBean bean) {
        return simsimDao.boardInsert(bean);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public int boardUpdate(BoardBean bean) {
        return simsimDao.boardUpdate(bean);
    }

}

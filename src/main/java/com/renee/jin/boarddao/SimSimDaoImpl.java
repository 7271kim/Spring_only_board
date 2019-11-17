package com.renee.jin.boarddao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.renee.jin.model.BoardBean;


@Repository
public class SimSimDaoImpl implements SimsimDao {

    private static Logger logger = LoggerFactory.getLogger(SimSimDaoImpl.class);
    
    @Autowired
    private SqlSession sqlSession;

    @Override
    public List<BoardBean> getbordList(BoardBean bean) {
        return sqlSession.selectList("sisimboard.getbordList",bean);
    }

    @Override
    public int boardInsert(BoardBean bean) {
        sqlSession.insert("sisimboard.boardInsert",bean);
        int seq = bean.getSeq();
        return seq;
    }

    @Override
    public int boardUpdate(BoardBean bean) {
        sqlSession.insert("sisimboard.boardUpdate",bean);
        int seq = bean.getSeq();
        return seq;
    }
    
}

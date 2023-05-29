package com.fastcampus.ch3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.sql.DataSource;

@Service
public class TxService {
    @Autowired A1Dao a1dao;
    @Autowired B1Dao b1dao;

    @Autowired
    DataSource ds;


//    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
//    public void insertA1WithTx() throws Exception {
//        a1dao.insert(1, 100);   // 성공
//        insertB1WithTx();
//        a1dao.insert(1, 100);   // 실패
//    }

    /* @Transactional이 동작하지 않는 이유(REQUIRES_NEW를 줬지만 다른 트랜잭션으로 안되는 이유)
     *   같은 클래스에 속한 메서드끼리의 호출(내부 호출)이기 때문.
     *   프록시 방식(디폴트)의 AOP는 내부 호출인 경우, Advice가 적용되지 않기 떄문에 Tx가 적용되지 않는것.
     *   두 메서드를 별도의 클래스로 분리하면 Tx가 적용됨. 근본적인 해결은 프록시 방식이 아닌 다른 방식을 사용해야함.
     * */
//    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class) // REQUIRED : 하나의 트랜잭션으로 묶임
//    public void insertB1WithTx() throws Exception {
//        b1dao.insert(1, 100);   // 성공
//        b1dao.insert(2, 200);   // 성공
//    }

    public void insertA1WithTx() throws Exception {
        PlatformTransactionManager tm = new DataSourceTransactionManager(ds);
        DefaultTransactionDefinition txd = new DefaultTransactionDefinition();
        txd.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = tm.getTransaction(txd);

        try {
            a1dao.insert(1, 100);   // 성공
            insertB1WithTx();
            a1dao.insert(2, 100);   // 실패
            tm.commit(status);
        } catch (Exception e) {
            e.printStackTrace();
            tm.rollback(status);
        } finally {
        }
    }

    public void insertB1WithTx() throws Exception {
        PlatformTransactionManager tm = new DataSourceTransactionManager(ds);
        DefaultTransactionDefinition txd = new DefaultTransactionDefinition();
        txd.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus status = tm.getTransaction(txd);
        try {
            b1dao.insert(1, 100);   // 성공
            b1dao.insert(1, 200);   // 성공
            tm.commit(status);
        } catch (Exception e) {
            e.printStackTrace();
            tm.rollback(status);
        } finally {
        }
    }




    public void insertA1WithoutTx() throws Exception {
        a1dao.insert(1, 100);
        a1dao.insert(1, 200);
    }

    @Transactional(rollbackFor = Exception.class) // Exception을 rollback
//    @Transactional  // RuntimeException, Error만 rollback
    public void insertA1WithTxFail() throws Exception {
        a1dao.insert(1, 100);
//        throw new RuntimeException();
        throw new Exception();
//        a1dao.insert(1, 200);
    }

    @Transactional
    public void insertA1WithTxSuccess() throws Exception {
        a1dao.insert(1, 100);
        a1dao.insert(2, 200);
    }

}

package com.fastcampus.ch3;

import jdk.jshell.spi.ExecutionControlProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import static org.junit.Assert.*;


// 각 테스트는 다른 테스트에 영향을 주면 안된다.
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class DBConnectionTest2Test {
    @Autowired
    DataSource ds;

    @Test
    public void insertUserTest() throws Exception {
        User user = new User("asdf", "1234", "abc", "aaaa@aaa.com",  new Date(), "fb", new Date());
        deleteAll();
        int rowCnt = insertUser(user);

        assertTrue(rowCnt==1);
    }

    @Test
    public void selectUserTest() throws Exception {
        deleteAll();
        User user = new User("asdf", "1234", "abc", "aaaa@aaa.com",  new Date(), "fb", new Date());
        int rowCnt = insertUser(user);
        User user2 = selectUser("asdf");

        assertTrue(user.getId().equals( "asdf"));
    }

    @Test
    public void deleteUserTest() throws Exception {
        deleteAll();
        int rowCnt = deleteUser("asdf");

        assertTrue(rowCnt==0);

        User user = new User("asdf", "1234", "abc", "aaaa@aaa.com",  new Date(), "fb", new Date());
        rowCnt = insertUser(user);
        assertTrue(rowCnt == 1);

        rowCnt = deleteUser(user.getId());
        assertTrue(rowCnt == 1);

        assertTrue(selectUser(user.getId()) == null);
    }

    @Test
    public void updateUserTest() throws Exception {
        deleteAll();
        User user = new User("asdf", "1234", "abc", "aaaa@aaa.com",  new Date(), "fb", new Date());
        int rowCnt = insertUser(user);

        assertTrue(rowCnt == 1);

        User user2 = new User("asdf", "0922", "잔근", "bbb@bb.com",  new Date(), "sns", new Date());
        updateUser(user2);
        rowCnt += updateUser(user2);

        assertTrue(rowCnt == 2);

    }

    public int updateUser(User user) throws Exception {
        Connection conn = ds.getConnection();

        String sql = "update user_info set pwd=?, name=?, email=?, birth =? , sns=? where id=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setString(1, user.getPwd());
        pstmt.setString(2, user.getName());
        pstmt.setString(3, user.getEmail());
        pstmt.setDate(4, new java.sql.Date(user.getBirth().getTime()));
        pstmt.setString(5, user.getSns());
        pstmt.setString(6, user.getId());

        return pstmt.executeUpdate();
    }
    
    public int deleteUser(String id) throws Exception {
        Connection conn = ds.getConnection();

        String sql = "delete from user_info where id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, id);

        return pstmt.executeUpdate();
    }

    public User selectUser(String id) throws Exception {
        Connection conn = ds.getConnection();

        String sql = "select * from user_info where id = ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, id);
        ResultSet rs = pstmt.executeQuery();

        if(rs.next()) {
            User user = new User();
            user.setId(rs.getString(1));
            user.setPwd(rs.getString(2));
            user.setName(rs.getString(3));
            user.setEmail(rs.getString(4));
            user.setBirth(new Date(rs.getDate(5).getTime()));
            user.setSns(rs.getString(6));
            user.setReg_date(new Date(rs.getTimestamp(7).getTime()));

            return user;
        }
        return null;
    }

    private void deleteAll() throws Exception {
        Connection conn = ds.getConnection();

        String sql = "delete from user_info";


        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.executeUpdate();    // insert,update, delete에만 사용 가능
    }

    // 사용자 정보를 user_info테이블에 insert
    public int insertUser(User user) throws Exception {
        Connection conn = ds.getConnection();

        String sql = "insert into user_info values (?, ?, ?, ?, ?, ?, now())";

        /* PreparedStatement는 SQL Injection공격 방어,
            ? 안의 값이 달라도 전부 같은 sql문장으로 인식하기 떄문에 캐싱효과가 있어 성능이 향상됨 */
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, user.getId());
        pstmt.setString(2, user.getPwd());
        pstmt.setString(3, user.getName());
        pstmt.setString(4, user.getEmail());
        pstmt.setDate(5, new java.sql.Date(user.getBirth().getTime()));
        pstmt.setString(6, user.getSns());

        int rowCnt = pstmt.executeUpdate();    // insert,update, delete에만 사용 가능

        return rowCnt;
    }

    @Test
    public void jdbcConntectionTest() throws Exception {
//        ApplicationContext ac = new GenericXmlApplicationContext("file:src/main/webapp/WEB-INF/spring/**/root-context.xml");
//        DataSource ds = ac.getBean(DataSource.class);

        Connection conn = ds.getConnection(); // 데이터베이스의 연결을 얻는다.

        System.out.println("conn = " + conn);
        assertTrue(conn != null);
    }

}
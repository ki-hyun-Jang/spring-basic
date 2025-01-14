package com.beyond.basic.b2_board.repository;

import com.beyond.basic.b2_board.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MemberJdbcRepository {

//    DataSource는 DB와 JDBC에서 사용하는 DB연결 드라이버 객체
//    application.yml에서 설정한 DB정보가 dataSource로 주입
    @Autowired
    private DataSource dataSource;

    public List<Member> findAll(){
        List<Member> memberList = new ArrayList<>();
        try {
            Connection connection = dataSource.getConnection();
            String query = "select * from member";
            PreparedStatement ps =  connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                Member member = new Member(id, name, email, password);
                memberList.add(member);
            }
        }catch (SQLException e){
//            checked 예외는 트랙잭션 처리가 되지 않으므로, unchecked로 바꿔 service레이어 예외 전달
            throw new RuntimeException(e);
        }
        return memberList;
    }

    public void save(Member member){
        try{
            Connection connection = dataSource.getConnection();
            String sql = "insert into member(name, email, password) values(?, ?, ?)";
            PreparedStatement ps =  connection.prepareStatement(sql);
            ps.setString(1, member.getName());
            ps.setString(2, member.getEmail());
            ps.setString(3, member.getPassword());
            ps.executeUpdate(); //추가, 수정의 경우 executeUpdate. 조회의 경우 executeQuery
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Member> findById(Long inPutId){
        Member member = null;
        try {
            Connection connection = dataSource.getConnection();
            String sql = "select * from member where id = ?";
            PreparedStatement ps =  connection.prepareStatement(sql);
            ps.setLong(1, inPutId);
            ResultSet rs = ps.executeQuery();
            rs.next();
            Long id = rs.getLong("id");
            String name = rs.getString("name");
            String email = rs.getString("email");
            String password = rs.getString("password");
            member = new Member(id, name, email, password);
        }catch (SQLException e ){
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(member);
    }

    public Optional<Member> findByEmail(String inputEmail){
        Member member = null;
        try {
            Connection connection = dataSource.getConnection();
            String sql = "select * from member where email = ?";
            PreparedStatement ps =  connection.prepareStatement(sql);
            ps.setString(1, inputEmail);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                member = new Member(id, name, email, password);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(member);
    }
}

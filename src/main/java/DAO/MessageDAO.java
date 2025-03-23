package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Model.Message;
import Util.ConnectionUtil;

import java.util.List;
import java.util.ArrayList;

public class MessageDAO {
    private AccountDAO accountDAO;

    public MessageDAO() {
        accountDAO = new AccountDAO();
    }

    public List<Message> getAllMessages() {
        List<Message> messages = new ArrayList<>();
        Connection conn = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT * FROM message";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                Message message = new Message();
                message.setMessage_id(resultSet.getInt("message_id"));
                message.setPosted_by(resultSet.getInt("posted_by"));
                message.setMessage_text(resultSet.getString("message_text"));
                message.setTime_posted_epoch(resultSet.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return messages;
    }
    
    public Message insertMessage(Message message) {
        if(message.getMessage_text() == null || message.getMessage_text().length() == 0 || message.getMessage_text().length() > 255) {
            return null;
        }
        if(accountDAO.getAccountById(message.getPosted_by()) == null) {
            return null;
        }

        Connection conn = ConnectionUtil.getConnection();

        try {
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES(?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());

            preparedStatement.executeUpdate();
            ResultSet pKey = preparedStatement.getGeneratedKeys();
            if(pKey.next()) {
                return new Message((int)pKey.getLong(1), message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

}

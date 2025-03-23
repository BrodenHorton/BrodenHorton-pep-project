package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {
    private AccountDAO accountDAO;

    public MessageDAO() {
        accountDAO = new AccountDAO();
    }
    
    public Message insertMessage(Message message) {
        if(message.getMessage_text() == null || message.getMessage_text().length() <= 255) {
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

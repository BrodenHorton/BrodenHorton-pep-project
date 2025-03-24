package Service;

import DAO.MessageDAO;
import DAO.AccountDAO;
import Model.Message;

import java.util.List;

public class MessageService {
    private MessageDAO messageDAO;
    private AccountDAO accountDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
        accountDAO = new AccountDAO();
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(int message_id) {
        return messageDAO.getMessageById(message_id);
    }

    public List<Message> getMessagesByPostedBy(int posted_by) {
        return messageDAO.getMessagesByPostedBy(posted_by);
    }

    public Message addMessage(Message message) {
        if(accountDAO.getAccountById(message.getPosted_by()) == null) {
            return null;
        }
        if(message.getMessage_text() == null || message.getMessage_text().isEmpty() || message.getMessage_text().length() > 255) {
            return null;
        }
        
        return messageDAO.insertMessage(message);
    }

    public Message deleteMessage(int message_id) {
        if(getMessageById(message_id) == null) {
            return null;
        }

        return messageDAO.deleteMessageById(message_id);
    }

    public Message patchMessageTextById(int message_id, String message_text) {
        if(getMessageById(message_id) == null) {
            return null;
        }
        if(message_text == null || message_text.isEmpty() || message_text.length() > 255) {
            return null;
        }

        return messageDAO.patchMessageTextById(message_id, message_text);
    }
}

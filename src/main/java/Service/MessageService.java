package Service;

import DAO.MessageDAO;
import Model.Message;

import java.util.List;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(int message_id) {
        return messageDAO.getMessageById(message_id);
    }

    public Message addMessage(Message message) {
        return messageDAO.insertMessage(message);
    }

    public Message deleteMessage(int message_id) {
        return messageDAO.deleteMessageById(message_id);
    }

    public Message patchMessageTextById(int message_id, String message_text) {
        return messageDAO.patchMessageTextById(message_id, message_text);
    }
}

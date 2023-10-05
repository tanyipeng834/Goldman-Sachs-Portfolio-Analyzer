package com.trading.application.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trading.application.logs.entity.AccessLog;
import com.trading.application.logs.service.AccessLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import java.util.concurrent.ExecutionException;

public class LogSubsciber implements MessageListener {
    Logger logger = LoggerFactory.getLogger(AccessLogService.class);
    @Autowired
    AccessLogService accessLogService = new AccessLogService();

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String jsonMessage = message.toString();
        logger.info(jsonMessage);// Assuming message contains JSON data
        jsonMessage = extractJsonFromMessage(jsonMessage);

        // Use an ObjectMapper to deserialize the JSON into an AccessLog object
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            AccessLog accessLog = objectMapper.readValue(jsonMessage, AccessLog.class);
            logger.info("Event Processed");
            accessLogService.addLog(accessLog);

            // Now 'accessLog' contains the deserialized object
            // You can use the 'accessLog' object as needed
        } catch (JsonProcessingException e) {
            // Handle deserialization error
            logger.error("Error deserializing AccessLog from message: " + e.getMessage());
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private String extractJsonFromMessage(String messageContent) {
        // You can implement custom logic here to extract the JSON portion
        // For example, you can use regular expressions or other techniques
        // to find and isolate the JSON part from the message content.

        // For simplicity, let's assume the JSON starts with '{' and ends with '}'.
        int startIndex = messageContent.indexOf('{');
        int endIndex = messageContent.lastIndexOf('}');

        if (startIndex != -1 && endIndex != -1) {
            return messageContent.substring(startIndex, endIndex + 1);
        } else {
            return null; // No valid JSON found in the message content
        }
    }


}

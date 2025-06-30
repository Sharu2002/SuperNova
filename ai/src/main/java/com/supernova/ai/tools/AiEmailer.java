package com.supernova.ai.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.sql.SQLOutput;

@Component
public class AiEmailer {

    @Tool(name = "sendEmailToUser",description = "Send an email with the specified subject and body to the specified recipient.")
    String sendEmailToUser(@ToolParam(description = "email id of the recipient") String to, @ToolParam(description = "Subject of the email") String subject, @ToolParam(description = "Body of the email") String body) {
        // Here you would implement the logic to send an email.
        // This is a placeholder implementation.
        System.out.println("\n\nFunction Called Successfuly .......");
        System.out.println( "Email sent to " + to + " with subject: " + subject + " and body: " + body);

        return "Email sent to " + to + " with subject: " + subject + " and body: " + body;
    }

}

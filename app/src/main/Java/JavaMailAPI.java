package com.example.collegebustrack;


import android.content.Context;
import android.os.AsyncTask;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JavaMailAPI extends AsyncTask<Void, Void, Void> {

        private Context context;

        private Session session;
        private String email, subject, message;

        public JavaMailAPI(Context context, String email, String subject, String message) {
            this.context = context;
            this.email = email;
            this.subject = subject;
            this.message = message;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Properties properties = new Properties();
            properties.put("mail.smtp.auth","true");
            properties.put("mail.smtp.starttls.enable","true");
            properties.put("mail.smtp.host","smtp.gmail.com");
            properties.put("mail.smtp.port","587");


            session=Session.getInstance(properties,new javax.mail.Authenticator(){
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("collegebustracker2@gmail.com","qwerty@123");
                }
            });
            MimeMessage mimeMessage = new MimeMessage(session);
            try {
                mimeMessage.setFrom(new InternetAddress("collegebustracker2@gmail.com"));
                mimeMessage.addRecipients(Message.RecipientType.TO, String.valueOf(new InternetAddress(email)));
                mimeMessage.setSubject(subject);
                mimeMessage.setText(message);
                Transport.send(mimeMessage);
            } catch (MessagingException e) {
                e.printStackTrace();
            }

            return null;

        }
    }


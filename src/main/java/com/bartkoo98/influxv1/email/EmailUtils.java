package com.bartkoo98.influxv1.email;

public class EmailUtils {

    public static String getNotificationEmailMessage(String articleTitle, String articleId) {

        return "Hello!\n\nWe're delighted to have you with us! We wanted to inform " +
                "you that we've just published a new blog article. Don't miss out on this exciting content! " +
                "\n\nTitle: " + articleTitle + "\nArticle link: http://localhost:8080/api/articles/" + articleId +
                "\n\nThank you for staying connected, and we look forward to your visit on INFLUX!" +
                "\n\nBest regards,\nINFLUX Team.";
    }

    public static String getRegistrationEmailMessage(String name, String host, String token) {
        return "Hello " + name + ",\n\nYour  account has been created. " +
                "Please click the link below to verify your account.\n\n " +
                getVerificationUrl(host, token) + "\n\nThe INFLUX TEAM!";
    }


    private static String getVerificationUrl(String host, String token) {
        return host + "/api/users?token=" + token;
    }
}

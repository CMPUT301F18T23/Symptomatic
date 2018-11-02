package ca.ualberta.symptomaticapp;

import junit.framework.TestCase;

import java.util.Date;

public class commentTest extends TestCase {

    public void testTimeStamp() {
        User user = new User("Name", "phone", "email");
        Date date = new Date();
        String newComment = "A comment";
        Comment comment = new Comment(user, date, newComment);
        assertTrue("Author is not equal", user.equals(comment.getAuthor()));

    }


    public void testDate() {
        Date date = new Date();
        User user = new User("Name", "phone", "email");
        String newComment = "A comment";
        Comment comment = new Comment(user, date, newComment);
        assertTrue("Date is not equal", date.equals(comment.getDate()));

    }

    public void testComment(){
        Date date = new Date();
        User user = new User("Name", "phone", "email");
        String newComment = "A comment";
        Comment comment = new Comment(user, date, newComment);
        assertTrue("Comment  is not equal", newComment.equals(comment.getComment()));
    }
}


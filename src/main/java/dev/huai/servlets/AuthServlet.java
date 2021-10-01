package dev.huai.servlets;

import dev.huai.models.User;
import dev.huai.services.UserServices;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthServlet  extends HttpServlet {
    private UserServices userServices = new UserServices();
    private User user = new User();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // get username and password from request
        // because we're using .getParameter with a post request, our servlet is expecting
        // key value pairs in the request body, along with the application/x-www-formurlencoded content type
        user.setUserId(Integer.valueOf(req.getParameter("user_id")));
        user.setPassword(req.getParameter("password"));

//        String idStringParam = req.getParameter("id"); // params not present will be null
        System.out.println("Credentials received: "+user.getUserId() +" "+user.getPassword());


        // check to see if user/pass match a user in the db
        //userServices.checkUser(user);

        // sent 401 (Unauthorized) if we can't find a user with those credentials
        if(!userServices.checkUser(user)){
            resp.sendError(401, "User credentials provided did not return a valid account");
        } else {
            // send 200 (OK) if we do find a user with those credentials
            resp.setStatus(200);
            // we can also send back some token that identifies the particular user that matched
            String token = user.getUserId() + ":" + user.getFirstName();
            resp.setHeader("Authorization", token);
        }
    }

}


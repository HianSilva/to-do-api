package com.hiansil.todolist.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.hiansil.todolist.user.IUserRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;
import java.util.Objects;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var path = request.getServletPath();

        if (path.equals("/tasks")) {
            var authorization = request.getHeader("Authorization");

            var authEncoded = authorization.substring("Basic".length()).trim();
            byte[] authDecode = Base64.getDecoder().decode(authEncoded);

            var authData = new String(authDecode);

            String[] authSplit = authData.split(":");
            var username = authSplit[0];
            var password = authSplit[1];

            var user = this.userRepository.findByUsername(username);
            if (user == null) {
                response.sendError(401);
            } else {
                var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());

                if (passwordVerify.verified) {
                    filterChain.doFilter(request, response);
                } else {
                    response.sendError(401);
                }
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
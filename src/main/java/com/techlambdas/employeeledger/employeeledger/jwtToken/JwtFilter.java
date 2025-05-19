package com.techlambdas.employeeledger.employeeledger.jwtToken;

import com.techlambdas.employeeledger.employeeledger.config.ServiceConfig;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;

@Component
public class JwtFilter extends OncePerRequestFilter{
    @Autowired
    private Tokenservice tokenservice;

    @Autowired
    private ServiceConfig serviceConfig;

    @Override
    protected void doFilterInternal
            (HttpServletRequest httpServletRequest,
             HttpServletResponse httpServletResponse,
             FilterChain filterChain) throws ServletException, IOException {

        String path = httpServletRequest.getServletPath();

        if( path.equals("/login") ||
                path.equals("/register") ||
                path.startsWith("/swagger") ||
                path.startsWith("/v2/api-docs") ||
                path.startsWith("/v3/api-docs") ||
                path.startsWith("/swagger-resources") ||
                path.startsWith("/webjars/") ||
                path.equals("/swagger-ui.html") ||
                path.startsWith("/swagger-ui")){
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;

        }

        String token =null;
        String userName = null;
        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        if(authorizationHeader!=null && authorizationHeader.startsWith("Bearer ")){
            token = authorizationHeader.substring(7);
            userName=tokenservice.getUserName(token);
        }
        if(tokenservice.isTokenExpired(token)){
            throw new ServletException("Token Expired");
        }

     try {
         if(userName!=null&& SecurityContextHolder.getContext().getAuthentication()==null){
             UserDetails userDetails = serviceConfig.loadUserByUsername(userName);
             if (tokenservice.validateToken(token, userDetails)) {
             UsernamePasswordAuthenticationToken authenticationToken =
                     new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
             SecurityContextHolder.getContext().setAuthentication(authenticationToken);
             }

         }
     }catch (SignatureException e) {
         httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
         httpServletResponse.setContentType("application/json");
         httpServletResponse.getWriter().write("{\"error\": \"Invalid JWT signature.\"}");
         return;
     }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}

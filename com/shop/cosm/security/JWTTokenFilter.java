package com.shop.cosm.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JWTTokenFilter extends GenericFilterBean {

    private final JWTTokenProvider jwtTokenProvider;


    public JWTTokenFilter(JWTTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void doFilter (ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest)servletRequest );
        try{
            if(token != null && jwtTokenProvider.validateToken(token))
            {
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                if(authentication != null)
                {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }

        }catch (JwtAuthorizationException e)
        {
            SecurityContextHolder.clearContext();
            ((HttpServletResponse) servletResponse).sendError(e.getHttpStatus().value());
        throw new JwtAuthorizationException("Jwt token is expire or invalid", HttpStatus.FORBIDDEN);
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

}

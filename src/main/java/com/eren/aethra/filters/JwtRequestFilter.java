package com.eren.aethra.filters;

import java.io.IOException;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eren.aethra.daos.CartDao;
import com.eren.aethra.models.Cart;
import com.eren.aethra.models.Customer;
import com.eren.aethra.services.SessionService;
import com.eren.aethra.utils.JwtTokenUtil;
import com.eren.aethra.services.impl.JwtUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;



import io.jsonwebtoken.ExpiredJwtException;


@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Resource
    private JwtUserDetailsService jwtUserDetailsService;

    @Resource
    private JwtTokenUtil jwtTokenUtil;

    @Resource
    SessionService sessionService;

    @Resource
    CartDao cartDao;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader("Authorization");

        String username = null;
        String jwtToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
            } catch (ExpiredJwtException  e) {
                System.out.println("JWT Token has expired");
            } catch (Exception e) {
                System.out.println("Error while setting customer into session service");
            }
        }


        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);

            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                try {
                    Customer customer = jwtTokenUtil.getUserFromToken();
                    sessionService.setCurrentCustomer(customer);
                    Optional<Cart> cartByCustomer = cartDao.findCartByCustomer(customer);
                    if (cartByCustomer.isPresent()) {
                        sessionService.setCurrentCart(cartByCustomer.get());
                    }
                } catch (Exception e) {
                    System.out.println("Error while creating session");
                }
            }
        }
        chain.doFilter(request, response);
    }

}

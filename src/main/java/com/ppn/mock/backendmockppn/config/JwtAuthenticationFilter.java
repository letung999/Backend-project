package com.ppn.mock.backendmockppn.config;

import com.ppn.mock.backendmockppn.constant.RequestCorrelation;
import com.ppn.mock.backendmockppn.service.impl.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String currentCorrId = getCorrelationId(request);
            log.info("start REST request to {} with correlationId {}", request.getRequestURI(), currentCorrId);
            if (currentCorrId != null) {
                if (!request.getRequestURI().contains("/login") && !request.getRequestURI().contains("/getByEmail")) {
                    String jwt = getJwtFromRequest(request);
                    if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                        Integer userId = tokenProvider.getUserIdFromJWT(jwt);
                        UserDetails userDetails = customUserDetailsService.loadUserById(userId);
                        if (userDetails != null) {
                            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        }
                    }
                }
                log.info("completed REST request to {} with correlationId {}", request.getRequestURI(), currentCorrId);
            } else {
                currentCorrId = UUID.randomUUID().toString();
                logger.info("No correlationId found in Header. Generated : " + currentCorrId);
            }

        } catch (Exception ex) {
            log.error("failed on set user authentication", ex);
        }
        filterChain.doFilter(request, response);
    }

    //get jwt from request
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    //get correlationId from request
    private String getCorrelationId(HttpServletRequest request) {
        String currentCorrId = request.getHeader(RequestCorrelation.CORRELATION_ID_HEADER);
        return currentCorrId;
    }

}
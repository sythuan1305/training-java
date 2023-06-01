package com.beetech.trainningJava.jwt;

import com.beetech.trainningJava.exception.HttpError;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Class này dùng để lấy thông tin từ request và kiểm tra xem token có hợp lệ hay không
 */
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;

    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        String REFRESH_URI = "/api/auth/refreshToken";
        if (REFRESH_URI.equals(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }
        String jwt = getJwtFromRequest(request);
        try {
            if (StringUtils.hasText(jwt) && jwtTokenProvider.isValidToken(jwt)) {
                String username = jwtTokenProvider.getUsernameFromJwt(jwt);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (userDetails != null) {
                    // câu lệnh này dùng để tạo ra 1 đối tượng authentication
                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    // câu lệnh này dùng để set thông tin cho đối tượng authentication
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    // câu lệnh này dùng để set đối tượng authentication vào SecurityContext và để lưu trữ thông tin đăng nhập
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        } catch (HttpError e) {
            response.sendError(e.getHttpStatus().value(), e.getMessage());
            return;
        }
        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}

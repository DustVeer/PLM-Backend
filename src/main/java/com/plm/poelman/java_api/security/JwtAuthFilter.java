package com.plm.poelman.java_api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        String method = request.getMethod();
        // Skip auth for login and CORS preflight
        return ("POST".equalsIgnoreCase(method) && "/api/auth/login".equals(path)) || "OPTIONS".equalsIgnoreCase(method);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {

        String header = req.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            try {
                Jws<Claims> jws = jwtService.parse(token);
                Claims claims = jws.getPayload();

                var authorities = extractAuthorities(claims);
                var auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null, authorities);

                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception ex) {
                SecurityContextHolder.clearContext();
                System.err.println("JWT invalid: " + ex.getClass().getSimpleName() + " - " + ex.getMessage());
            }
        }
        chain.doFilter(req, res);
    }

    private List<GrantedAuthority> extractAuthorities(Claims c) {
        List<GrantedAuthority> out = new ArrayList<>();

        Object rolesClaim = c.get("roles");
        if (rolesClaim instanceof Collection<?> col) {
            for (Object o : col)
                if (o != null)
                    out.add(new SimpleGrantedAuthority(prefix(o.toString())));
        }

        Object roleClaim = c.get("role");
        if (roleClaim instanceof String s && !s.isBlank()) {
            out.add(new SimpleGrantedAuthority(prefix(s)));
        }

        return out;
    }

    private String prefix(String r) {
        return r.startsWith("ROLE_") ? r : "ROLE_" + r;
    }

}

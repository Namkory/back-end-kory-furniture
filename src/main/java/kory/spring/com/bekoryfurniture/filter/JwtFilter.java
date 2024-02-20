package kory.spring.com.bekoryfurniture.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kory.spring.com.bekoryfurniture.utils.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // logic xac thuc jwt neu hop le thi cho no den controller

        try {
            String token = this.getJwtFromRequest(request);
            if(token != null) {
                // neu co token thi validate no
                if(jwtUtil.validateJwt(token)) {
                    // neu ma token hop le thi cho no den controller de truy cap tai nguyen
                    UsernamePasswordAuthenticationToken // danh dau cho spring security biet la da xac thuc thanh cong
                            authentication = new
                            UsernamePasswordAuthenticationToken(null, null, null);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    filterChain.doFilter(request, response);
                }
            }
            else {
                System.out.println("Khong ton tai jwt!");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }

    //Hàm shouldNotFilter định nghĩa các đường dẫn không cần xác thực vẫn có thể truy cập dữ liệu
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String servletPath = request.getServletPath();
        return servletPath.startsWith("/api/v1/auth")
                || servletPath.startsWith("/api/v1/products/get-all-products");
    }
}

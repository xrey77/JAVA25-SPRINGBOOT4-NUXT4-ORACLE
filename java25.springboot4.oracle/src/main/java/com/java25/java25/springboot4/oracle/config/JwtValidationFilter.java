//package com.java25.java25.springboot4.oracle.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
////import org.springframework.http.ProblemDetail;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import com.java25.java25.springboot4.oracle.services.JwtService;
//
//import io.jsonwebtoken.JwtException; // Catch all JWT related issues
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.MalformedJwtException;
//import io.jsonwebtoken.security.SignatureException;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import tools.jackson.databind.ObjectMapper;
//import java.io.IOException; // FIXED: Use standard Java IO
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import java.util.Map;
//
//@Component
//public class JwtValidationFilter extends OncePerRequestFilter {
//
//
//    private final ObjectMapper objectMapper = new ObjectMapper();
//    private final JwtService jwtService;
//    private final UserDetailsService userDetailsService; // Added to load user details
//
//    public JwtValidationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
//        this.jwtService = jwtService;
//        this.userDetailsService = userDetailsService;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//        try {
//            String token = extractToken(request);
//            if (token != null) {
//                String username = jwtService.extractUsername(token);
//                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//                    
//                    if (jwtService.validateToken(token, userDetails)) {
//                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
//                                userDetails, null, userDetails.getAuthorities());
//                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                        SecurityContextHolder.getContext().setAuthentication(authToken);
//                    }
//                }
//            }
//            filterChain.doFilter(request, response);
//        } catch (ExpiredJwtException e) {
//            writeErrorResponse(response, HttpStatus.UNAUTHORIZED, "Token has expired");
//            return;
//        } catch (MalformedJwtException | SignatureException | IllegalArgumentException e) {
//            writeErrorResponse(response, HttpStatus.BAD_REQUEST, "Invalid or malformed Bearer token");
//            return;
//        } catch (JwtException e) {
//            writeErrorResponse(response, HttpStatus.INTERNAL_SERVER_ERROR, "General security error");
//            return;
//        }
//    }
//
//    private void writeErrorResponse(HttpServletResponse response, HttpStatus status, String message) throws IOException {
//        response.setStatus(status.value());
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        Map<String, String> errorBody = Map.of("message", message);
//        response.getWriter().write(objectMapper.writeValueAsString(errorBody));
//    }
//    
//    private String extractToken(HttpServletRequest request) {
//        String header = request.getHeader("Authorization");
//        if (header != null && header.startsWith("Bearer ")) {
//            return header.substring(7);
//        }
//        return null;
//    }
//
////    public Boolean validateToken(String token, UserDetails userDetails) {
////    	try {
////	        final String username = extractUsername(token);
////	        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
////    	} catch(Exception ex) {
////    		return null;
////    	}
////    }
//    
//    
////    private void validateToken(String token) {
////        Jwts.parser()
////            .verifyWith(getSigningKey())
////            .build()
////            .parseSignedClaims(token);
////    }
//
////    private SecretKey getSigningKey() {
////        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
////        return Keys.hmacShaKeyFor(keyBytes);
////    }
//}
//
//
////@Component
////public class JwtValidationFilter extends OncePerRequestFilter {
////
//////	@Value("${jwt.secret}")
//////	private String secretKey;
////	private final ObjectMapper objectMapper = new ObjectMapper();
////	
////	private final JwtService jwtService;
////	
////	
////	public JwtValidationFilter(JwtService jwtService) {
////        this.jwtService = jwtService;
////    }	
////	
////	
////	@Override
////    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
////            throws ServletException, IOException {
////        try {
////            String token = extractToken(request);
//////            System.out.println("TOKEN.............................." + token);
////            if (token != null) {
////            	boolean isValid = jwtService.validateToken(token, null);
////            	if (!isValid) {
//////            		System.out.println("Invalid TOKEN..............................");
////                    writeErrorResponse(response, HttpStatus.BAD_REQUEST, "Invalid or malformed Bearer token!");	
////            	} else {
////                    writeErrorResponse(response, HttpStatus.BAD_REQUEST, "valid Bearer token.....");	            		
////            	}
////            }
////            filterChain.doFilter(request, response);
//////        } catch (ExpiredJwtException e) {
//////            writeErrorResponse(response, HttpStatus.UNAUTHORIZED, {"message", "Token has expired"});
//////            writeErrorResponse(response, HttpStatus.UNAUTHORIZED, "Token has expired");            
//////        } catch (MalformedJwtException | SignatureException | IllegalArgumentException e) {
//////            writeErrorResponse(response, HttpStatus.BAD_REQUEST, "Invalid or malformed Bearer token!");
////        }
////        catch (JwtException e) {
////            writeErrorResponse(response, HttpStatus.INTERNAL_SERVER_ERROR, "General JWT Error.........");
////        }
////    }    
////    
////    private void writeErrorResponse(HttpServletResponse response, HttpStatus status, String message) throws IOException {
////      response.setStatus(status.value());
////      // Change content type to standard JSON
////      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
////
////      // Create a simple map to match your desired structure: { "message": "..." }
////      Map<String, String> errorBody = Map.of("message", message);
////
////      // Serialize the map directly
////      response.getWriter().write(objectMapper.writeValueAsString(errorBody));
////    }    
////
////    private String extractToken(HttpServletRequest request) {
////        String header = request.getHeader("Authorization");
////        if (header != null && header.startsWith("Bearer ")) {
////            return header.substring(7); // Remove "Bearer " prefix
////        }
////        return null;
////    }
////    
//////    private void validateToken(String token) {
//////        // In Spring Security 7, ensure you use high-entropy keys (at least 256-bit)
//////        Jwts.parser()
//////            .verifyWith(getSigningKey()) // Method to retrieve your secret key
//////            .build()
//////            .parseSignedClaims(token); // This throws ExpiredJwtException, etc., handled by your catch blocks
//////    }    
////    
//////    private SecretKey getSigningKey() {
//////        // Ensure the secret is at least 256 bits (32 characters)
//////        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
//////        return Keys.hmacShaKeyFor(keyBytes);
//////    }
////        
////}

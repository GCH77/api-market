package com.market.api.web.security.filter;

import com.market.api.domain.services.UserDetailService;
import com.market.api.web.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilterRequest extends OncePerRequestFilter {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UserDetailService userDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        Del request obtiene la cabecera llamada Authentication
        String authorizationHeader = request.getHeader("Authorization");
//        Luego verifica que si exista y que inicie con Bearer
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer")) {
//          Extraemos el token unicamente sin el bearer
            String jwt = authorizationHeader.substring(7);
//           Luego extraemos el username usando la clase jwutil
            String username = jwtUtil.extractUsername(jwt);

//          Valida si de la extraccion hay username y que en el Contexto de la app no este autenticado un usuario ya
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//               crea el usuario de acuerdo al username extraido
                UserDetails userDetails = userDetailService.loadUserByUsername(username);
//              Luego valida que ese username que envian sea el mismo que esta en DB
                if (jwtUtil.validateToken(jwt, userDetails)) {
//                    userDetails.getAuthorities() = es para enviar los roles de ese usuario
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                  Esto es para obtener datos extra de la peticion (navegador, hora, ubicacion, OS)
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                  Finalmente, una vez validado, se agrega al contexto con la finalidad de que no
//                  haya necesidad de que cuando haga una peticion nueva, no valide todo de nuevo
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}

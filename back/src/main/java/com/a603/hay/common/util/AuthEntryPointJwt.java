package com.a603.hay.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

  //TODO 에러처리 변경
  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException)
      throws IOException {
    log.error("Unauthorized error: {}", authException.getMessage());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

    final Map<String, Object> body = new HashMap<>();
    body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
    body.put("error", "Unauthorized");
    body.put("message", authException.getMessage());
    body.put("path", request.getServletPath());

    final ObjectMapper mapper = new ObjectMapper();
    mapper.writeValue(response.getOutputStream(),
        body);
    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");

  }

}
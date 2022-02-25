package net.dengzixu.maine.web.api.v1;

import net.dengzixu.maine.exception.BusinessException;
import net.dengzixu.maine.model.APIResponseMap;
import net.dengzixu.maine.model.APIResultMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@ControllerAdvice
public class BusinessExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Map<String, Object>> handleBusinessException(HttpServletRequest request,
                                                                       HttpServletResponse response,
                                                                       BusinessException e) {

        HttpStatus httpStatus = HttpStatus.resolve(e.getHttpStatusCode());

        return ResponseEntity.status(httpStatus != null ? httpStatus : HttpStatus.INTERNAL_SERVER_ERROR)
                .body(APIResponseMap.FAILED(-1, e.getMessage()));

    }
}

package api.giybat.uz.controller;

import api.giybat.uz.exps.AppBadException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class ExceptionHandleController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(AppBadException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}

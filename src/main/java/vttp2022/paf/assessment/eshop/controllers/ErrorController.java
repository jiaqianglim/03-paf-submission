package vttp2022.paf.assessment.eshop.controllers;

import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorController {

    @ExceptionHandler({ DataAccessException.class })
    public ResponseEntity<String> transactionfailed(DataAccessException ex) {
        return ResponseEntity.internalServerError().body("transaction failed");
    }

}

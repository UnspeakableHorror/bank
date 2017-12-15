package org.homenet.uhs.transaction;

import org.homenet.uhs.transaction.service.TransactionQueueingService;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

/**
 * @author uh on 2017/12/12.
 */
@RestController
@RequestMapping("api")
public class TransactionController {

    private final TransactionQueueingService transactionQueueingService;


    public TransactionController(TransactionQueueingService transactionQueueingService) {
        this.transactionQueueingService = transactionQueueingService;
    }

    @PostMapping("transaction")
    public ResponseEntity<String> postTransaction(@RequestBody TransactionDTO transactionDTO, BindingResult result){

        if(result.hasErrors()){
            String errors = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));

            return ResponseEntity.badRequest().body(errors);
        }

        boolean success = transactionQueueingService.post(transactionDTO);

        if(!success){
            //normally this would never happen...
            return ResponseEntity.unprocessableEntity().body("Queue full. Try again later.");
        }

        return ResponseEntity.ok().build();
    }


}

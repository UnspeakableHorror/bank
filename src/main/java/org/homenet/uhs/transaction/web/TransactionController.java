package org.homenet.uhs.transaction.web;

import org.homenet.uhs.configuration.Runners;
import org.homenet.uhs.transaction.service.queue.TransactionQueueingService;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * @author uh on 2017/12/12.
 */
@RestController
@RequestMapping("api")
public class TransactionController {
    private Logger logger = Logger.getLogger(TransactionController.class.getName());

    private final Runners runners;
    private final TransactionQueueingService transactionQueueingService;

    public TransactionController(Runners runners, TransactionQueueingService transactionQueueingService) {
        this.runners = runners;
        this.transactionQueueingService = transactionQueueingService;
    }

    @PostMapping("transaction")
    public ResponseEntity<String> postTransaction(@RequestBody TransactionDTO transactionDTO, BindingResult result){

        if(result.hasErrors()){
            String errors = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));

            //return ResponseEntity.ok().build();
            return ResponseEntity.badRequest().body(errors);
        }

        if(transactionDTO.getOriginAccount().equals(transactionDTO.getDestinationAccount())){
            logger.severe("Invalid origin and destination. Cannot transfer to itself.");
            //return ResponseEntity.ok().build();
            return ResponseEntity.badRequest().body("Invalid origin and destination. Cannot transfer to itself.");
        }

        if(transactionDTO.getAmount() <= 0){
            logger.severe("The amount must be a positive number.");
            //return ResponseEntity.ok().build();
            return ResponseEntity.badRequest().body("The amount must be a positive number.");
        }

        boolean success = transactionQueueingService.post(transactionDTO);

        if(!success){
            //normally this would never happen...
            return ResponseEntity.unprocessableEntity().body("Queue full. Try again later.");
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("start")
    public String start(){
        runners.start();
        return "started";
    }

    @GetMapping("stop")
    public String stop(){
        runners.stop();
        return "stop";
    }

}

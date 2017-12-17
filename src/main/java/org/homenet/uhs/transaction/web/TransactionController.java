package org.homenet.uhs.transaction.web;

import org.homenet.uhs.configuration.Runner;
import org.homenet.uhs.transaction.service.queue.TransactionQueueingService;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * @author uh on 2017/12/12.
 */
@RestController
@RequestMapping("api")
public class TransactionController {

    private final Runner runner;

    private final TransactionQueueingService transactionQueueingService;

    private final ExecutorService service = Executors.newSingleThreadExecutor();

    public TransactionController(Runner runner, TransactionQueueingService transactionQueueingService) {
        this.runner = runner;
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

    @GetMapping("hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("start")
    public String start(){
        //runner.run();

        service.submit(runner::run);

        return "started";
    }

    @GetMapping("stop")
    public String stop(){
        runner.stop();

        return "stop";
    }

}

//package uz.result.rmcdeluxe.controller.temporary.investment;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import uz.result.rmcdeluxe.entity.temporary.investment.PurchaseProcess;
//import uz.result.rmcdeluxe.payload.ApiResponse;
//import uz.result.rmcdeluxe.service.temporary.investment.PurchaseProcessService;
//
//@RestController
//@RequestMapping("/v1/investments")
//@RequiredArgsConstructor
//public class PurchaseProcessController {
//    private final PurchaseProcessService processService;
//
//
//    @PostMapping("/purchase_procces/add-step")
//    public ResponseEntity<ApiResponse<PurchaseProcess>> addStep(
//            @RequestParam(value = "json") String step
//    )
//    {
//        return processService.addStep(step);
//    }
//
//    @GetMapping("/purchase_procces/get")
//    public ResponseEntity<ApiResponse<?>> getProcess(
//            @RequestHeader(value = "Accept-Language",required = false) String lang
//    ) {
//        return processService.get(lang);
//    }
//
//    @PutMapping("/purchase_procces/update")
//    public ResponseEntity<ApiResponse<PurchaseProcess>> updateProcess(
//            @RequestBody PurchaseProcess process
//    ) {
//        return processService.update(process);
//    }
//
//    @DeleteMapping("/purchase_process/delete")
//    public ResponseEntity<ApiResponse<?>> deleteProcess() {
//        return processService.delete();
//    }
//
//}

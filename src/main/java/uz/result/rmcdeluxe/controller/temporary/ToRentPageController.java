//package uz.result.rmcdeluxe.controller.temporary;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import uz.result.rmcdeluxe.entity.temporary.ToRentPageHowRentForm;
//import uz.result.rmcdeluxe.entity.temporary.ToRentPageWorkProcess;
//import uz.result.rmcdeluxe.payload.ApiResponse;
//import uz.result.rmcdeluxe.service.temporary.ToRentPageHowRentFormService;
//import uz.result.rmcdeluxe.service.temporary.ToRentPageWorkProcessService;
//
//@RestController
//@RequestMapping("/to-rent-page")
//@RequiredArgsConstructor
//public class ToRentPageController {
//
//    private final ToRentPageHowRentFormService rentFormService;
//
//    private final ToRentPageWorkProcessService workProcessService;
//
//    @PostMapping("/how_rent/create")
//    public ResponseEntity<ApiResponse<ToRentPageHowRentForm>> createRentForm(
//            @RequestBody ToRentPageHowRentForm rentForm
//    ) {
//        return rentFormService.create(rentForm);
//    }
//
//    @GetMapping("/how_rent/get")
//    public ResponseEntity<ApiResponse<?>> findRentForm(
//            @RequestHeader(value = "Accept-Language", required = false) String lang
//    ) {
//        return rentFormService.find(lang);
//    }
//
//    @PutMapping("/how_rent/update")
//    public ResponseEntity<ApiResponse<ToRentPageHowRentForm>> updateRentForm(
//            @RequestBody ToRentPageHowRentForm rentForm
//    ) {
//        return rentFormService.update(rentForm);
//    }
//
//    @DeleteMapping("/how_rent/delete")
//    public ResponseEntity<ApiResponse<?>> deleteRentForm() {
//        return rentFormService.delete();
//    }
//
//    @PostMapping("/work-process/create")
//    public ResponseEntity<ApiResponse<ToRentPageWorkProcess>> createWorkProcess(
//            @RequestBody ToRentPageWorkProcess workProcess
//    ) {
//        return workProcessService.create(workProcess);
//    }
//
//    @GetMapping("/work-process/get")
//    public ResponseEntity<ApiResponse<?>> findWorkProcess(
//            @RequestHeader(value = "Accept-Language", required = false) String lang) {
//        return workProcessService.find(lang);
//    }
//
//    @PutMapping("/work-process/update")
//    public ResponseEntity<ApiResponse<ToRentPageWorkProcess>> updateWorkProcess(
//            @RequestBody ToRentPageWorkProcess workProcess
//    ) {
//        return workProcessService.update(workProcess);
//    }
//
//    @DeleteMapping("/work-process/delete")
//    public ResponseEntity<ApiResponse<?>> delete() {
//        return workProcessService.delete();
//    }
//
//}

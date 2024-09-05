//package uz.result.rmcdeluxe.controller.temporary;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartHttpServletRequest;
//import uz.result.rmcdeluxe.entity.temporary.RentPageFAQForm;
//import uz.result.rmcdeluxe.entity.temporary.RentPageHowSearchForm;
//import uz.result.rmcdeluxe.entity.temporary.RentPageSearchProperty;
//import uz.result.rmcdeluxe.entity.temporary.RentPageWorkProcess;
//import uz.result.rmcdeluxe.payload.ApiResponse;
//import uz.result.rmcdeluxe.service.temporary.RentPageFAQFormService;
//import uz.result.rmcdeluxe.service.temporary.RentPageHowSearchFormService;
//import uz.result.rmcdeluxe.service.temporary.RentPageSearchPropertyService;
//import uz.result.rmcdeluxe.service.temporary.RentPageWorkProcessService;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/v1/rent-page")
//@RequiredArgsConstructor
//public class RentPageController {
//
//    private final RentPageSearchPropertyService searchPropertyService;
//
//    private final RentPageHowSearchFormService searchFormService;
//
//    private final RentPageWorkProcessService workProcessService;
//
//    private final RentPageFAQFormService faqFormService;
//
//    @PostMapping("/search-property/create")
//    public ResponseEntity<ApiResponse<RentPageSearchProperty>> createPropertyForm(
//            @RequestParam(value = "json") String json,
//            MultipartHttpServletRequest request
//    ) {
//        return searchPropertyService.create(json, request);
//    }
//
//    @GetMapping("/search-property/get")
//    public ResponseEntity<ApiResponse<?>> findPropertyForm(
//            @RequestHeader(value = "Accept-Language", required = false) String lang
//    ) {
//        return searchPropertyService.find(lang);
//    }
//
//    @PutMapping("/search-property/update")
//    public ResponseEntity<ApiResponse<RentPageSearchProperty>> updatePropertyForm(
//            @RequestParam(value = "json") String searchProperty,
//            @RequestParam(required = false) MultipartHttpServletRequest request
//    ) {
//        return searchPropertyService.update(searchProperty, request);
//    }
//
//    @DeleteMapping("/search-property/delete")
//    public ResponseEntity<ApiResponse<?>> deletePropertyForm() {
//        return searchPropertyService.delete();
//    }
//
//    @PostMapping("/search-form/create")
//    public ResponseEntity<ApiResponse<RentPageHowSearchForm>> createSearchForm(
//            @RequestBody RentPageHowSearchForm searchForm
//    ) {
//        return searchFormService.create(searchForm);
//    }
//
//    @GetMapping("/search-form/get")
//    public ResponseEntity<ApiResponse<?>> findSearchForm(
//            @RequestHeader(value = "Accept-Language", required = false) String lang
//    ) {
//        return searchFormService.find(lang);
//    }
//
//    @PutMapping("/search-form/update")
//    public ResponseEntity<ApiResponse<RentPageHowSearchForm>> updateSearchForm(
//            @RequestBody RentPageHowSearchForm searchForm
//    ) {
//        return searchFormService.update(searchForm);
//    }
//
//    @DeleteMapping("/search-form/delete")
//    public ResponseEntity<ApiResponse<?>> deleteSearchForm() {
//        return searchFormService.delete();
//    }
//
//    @PostMapping("/work-process/create")
//    public ResponseEntity<ApiResponse<RentPageWorkProcess>> createWorkProcess(
//            @RequestBody RentPageWorkProcess workProcess
//    ) {
//        return workProcessService.create(workProcess);
//    }
//
//    @GetMapping("/work-process/get")
//    public ResponseEntity<ApiResponse<?>> findWorkProcess(
//            @RequestHeader(value = "Accept-Language", required = false) String lang
//    ) {
//        return workProcessService.find(lang);
//    }
//
//    @PutMapping("/work-process/update")
//    public ResponseEntity<ApiResponse<RentPageWorkProcess>> updateWorkProcess(
//            @RequestBody RentPageWorkProcess workProcess
//    ) {
//        return workProcessService.update(workProcess);
//    }
//
//    @DeleteMapping("/work-process/delete")
//    public ResponseEntity<ApiResponse<?>> deleteWorkProcess() {
//        return workProcessService.delete();
//    }
//
//    @PostMapping("/faq-form/create")
//    public ResponseEntity<ApiResponse<RentPageFAQForm>> createFAQForm(
//            @RequestBody RentPageFAQForm faqForm
//    ) {
//        return faqFormService.create(faqForm);
//    }
//
//    @GetMapping("/faq-form/get")
//    public ResponseEntity<ApiResponse<?>> findFAQForm(
//            @RequestHeader(value = "Accept-Language", required = false) String lang
//    ) {
//        return faqFormService.find(lang);
//    }
//
//    @PutMapping("/faq-form/update")
//    public ResponseEntity<ApiResponse<RentPageFAQForm>> updateFAQForm(
//            @RequestBody RentPageFAQForm faqForm
//    ) {
//        return faqFormService.update(faqForm);
//    }
//
//    @DeleteMapping("/faq-form/delete")
//    public ResponseEntity<ApiResponse<?>> deleteFAQForm() {
//        return faqFormService.delete();
//    }
//
//}

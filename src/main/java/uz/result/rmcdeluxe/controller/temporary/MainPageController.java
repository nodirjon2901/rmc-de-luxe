//package uz.result.rmcdeluxe.controller.temporary;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import uz.result.rmcdeluxe.entity.temporary.MainPageAboutCompany;
//import uz.result.rmcdeluxe.entity.temporary.MainPageAdvantage;
//import uz.result.rmcdeluxe.entity.temporary.MainPageInvestment;
//import uz.result.rmcdeluxe.entity.temporary.PropertyManagement;
//import uz.result.rmcdeluxe.payload.ApiResponse;
//import uz.result.rmcdeluxe.service.temporary.MainPageAboutCompanyService;
//import uz.result.rmcdeluxe.service.temporary.MainPageAdvantageService;
//import uz.result.rmcdeluxe.service.temporary.MainPageInvestmentService;
//import uz.result.rmcdeluxe.service.temporary.PropertyManagementService;
//
//@RestController
//@RequestMapping("/v1/main-page")
//@RequiredArgsConstructor
//public class MainPageController {
//
//    private final MainPageInvestmentService investmentService;
//
//    private final MainPageAdvantageService advantageService;
//
//    private final PropertyManagementService managementService;
//
//    private final MainPageAboutCompanyService companyService;
//
//    @PostMapping("/investment/create")
//    public ResponseEntity<ApiResponse<MainPageInvestment>> createInvestment(
//            @RequestParam(value = "json") String investment,
//            @RequestPart(value = "main-photo") MultipartFile mainPhoto,
//            @RequestPart(value = "photo") MultipartFile photo
//    ) {
//        return investmentService.create(investment, mainPhoto, photo);
//    }
//
//    @GetMapping("/investment/get")
//    public ResponseEntity<ApiResponse<?>> getInvestment(
//            @RequestHeader(value = "Accept-Language", required = false) String lang
//    ) {
//        return investmentService.get(lang);
//    }
//
//    @PutMapping("/investment/update")
//    public ResponseEntity<ApiResponse<MainPageInvestment>> updateInvestment(
//            @RequestBody MainPageInvestment investment
//    ) {
//        return investmentService.update(investment);
//    }
//
//    @DeleteMapping("/investment/delete")
//    public ResponseEntity<ApiResponse<?>> deleteInvestment() {
//        return investmentService.delete();
//    }
//
//    @PostMapping("/advantage/create")
//    public ResponseEntity<ApiResponse<MainPageAdvantage>> createAdvantage(
//            @RequestParam(value = "json") String advantage,
//            @RequestPart(value = "photo") MultipartFile photo
//    ) {
//        return advantageService.create(advantage, photo);
//    }
//
//    @GetMapping("/advantage/get/{id}")
//    public ResponseEntity<ApiResponse<?>> findAdvantageById(
//            @PathVariable Long id,
//            @RequestHeader(value = "Accept-Language", required = false) String lang
//    ) {
//        return advantageService.findById(id, lang);
//    }
//
//    @GetMapping("/advantage/get-all")
//    public ResponseEntity<ApiResponse<?>> findAllAdvantage(
//            @RequestHeader(value = "Accept-Language", required = false) String lang
//    ) {
//        return advantageService.findAll(lang);
//    }
//
//    @PutMapping("/advantage/update")
//    public ResponseEntity<ApiResponse<MainPageAdvantage>> updateAdvantage(
//            @RequestBody MainPageAdvantage advantage
//    ) {
//        return advantageService.update(advantage);
//    }
//
//    @DeleteMapping("/advantage/delete/{id}")
//    public ResponseEntity<ApiResponse<?>> delete(
//            @PathVariable Long id
//    ) {
//        return advantageService.delete(id);
//    }
//
//    @PostMapping("/service/create")
//    public ResponseEntity<ApiResponse<PropertyManagement>> addService(
//            @RequestBody PropertyManagement management
//    ) {
//        return managementService.create(management);
//    }
//
//    @GetMapping("/service/get")
//    public ResponseEntity<ApiResponse<?>> findService(
//            @RequestHeader(value = "Accept-Language", required = false) String lang
//    ) {
//        return managementService.find(lang);
//    }
//
//    @PutMapping("/service/update")
//    public ResponseEntity<ApiResponse<PropertyManagement>> updateService(
//            @RequestBody PropertyManagement management
//    ) {
//        return managementService.update(management);
//    }
//
//    @DeleteMapping("/service/delete")
//    public ResponseEntity<ApiResponse<?>> deleteService() {
//        return managementService.delete();
//    }
//
//    @PostMapping("/about-company/create")
//    public ResponseEntity<ApiResponse<MainPageAboutCompany>> createAboutCompany(
//            @RequestParam(value = "json") String json,
//            @RequestPart(value = "photo") MultipartFile photo
//    ) {
//        return companyService.create(json, photo);
//    }
//
//    @GetMapping("/about-company/get")
//    public ResponseEntity<ApiResponse<?>> find(
//            @RequestHeader(value = "Accept-Language",required = false) String lang
//    ) {
//        return companyService.find(lang);
//    }
//
//    @PutMapping("/about-company/update")
//    public ResponseEntity<ApiResponse<MainPageAboutCompany>> update(
//            @RequestBody MainPageAboutCompany company
//    ) {
//        return companyService.update(company);
//    }
//
//    @DeleteMapping("/about-company/delete")
//    public ResponseEntity<ApiResponse<?>> delete() {
//        return companyService.delete();
//    }
//
//}

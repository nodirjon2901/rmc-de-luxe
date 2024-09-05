//package uz.result.rmcdeluxe.controller.temporary;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import uz.result.rmcdeluxe.entity.temporary.BuildingPageHeader;
//import uz.result.rmcdeluxe.entity.temporary.BuildingPageOverview;
//import uz.result.rmcdeluxe.payload.ApiResponse;
//import uz.result.rmcdeluxe.service.temporary.BuildingPageHeaderService;
//import uz.result.rmcdeluxe.service.temporary.BuildingPageOverviewService;
//
//@RestController
//@RequestMapping("/v1/building-page")
//@RequiredArgsConstructor
//public class BuildingPageController {
//
//    private final BuildingPageHeaderService headerService;
//
//    private final BuildingPageOverviewService overviewService;
//
//    @PostMapping("/header/create")
//    public ResponseEntity<ApiResponse<BuildingPageHeader>> createHeader(
//            @RequestParam(value = "json") String header,
//            @RequestPart(value = "photo") MultipartFile photo
//    ) {
//        return headerService.create(header, photo);
//    }
//
//    @GetMapping("/header/get")
//    public ResponseEntity<ApiResponse<?>> findHeader(
//            @RequestHeader(value = "Accept-Language", required = false) String lang
//    ) {
//        return headerService.find(lang);
//    }
//
//    @PutMapping("/header/update")
//    public ResponseEntity<ApiResponse<BuildingPageHeader>> updateHeader(
//            @RequestBody BuildingPageHeader header
//    ) {
//        return headerService.update(header);
//    }
//
//    @DeleteMapping("/header/delete")
//    public ResponseEntity<ApiResponse<?>> deleteHeader() {
//        return headerService.delete();
//    }
//
//    @PostMapping("/overview/create")
//    public ResponseEntity<ApiResponse<BuildingPageOverview>> createOverview(
//            @RequestParam(value = "json") String overview,
//            @RequestPart(value = "photo") MultipartFile photo
//    ) {
//        return overviewService.create(overview, photo);
//    }
//
//    @GetMapping("/overview/get")
//    public ResponseEntity<ApiResponse<?>> findOverview(
//            @RequestHeader(value = "Accept-Language",required = false) String lang
//    ) {
//        return overviewService.find(lang);
//    }
//
//    @PutMapping("/overview/update")
//    public ResponseEntity<ApiResponse<BuildingPageOverview>> updateOverview(
//            @RequestBody BuildingPageOverview overview
//    ) {
//        return overviewService.update(overview);
//    }
//
//    @DeleteMapping("/overview/delete")
//    public ResponseEntity<ApiResponse<?>> delete() {
//        return overviewService.delete();
//    }
//
//}

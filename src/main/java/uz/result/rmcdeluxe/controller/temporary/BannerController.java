//package uz.result.rmcdeluxe.controller.temporary;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import uz.result.rmcdeluxe.entity.temporary.Banner;
//import uz.result.rmcdeluxe.payload.ApiResponse;
//import uz.result.rmcdeluxe.service.temporary.BannerService;
//
//@RestController
//@RequestMapping("/v1/banner")
//@RequiredArgsConstructor
//public class BannerController {
//
//    private final BannerService bannerService;
//
//    @PostMapping("/add")
//    public ResponseEntity<ApiResponse<Banner>> addBannerSlider(
//            @RequestParam(value = "json") String slider,
//            @RequestPart(value = "photo") MultipartFile photo
//    ) {
//        return bannerService.addSlider(slider, photo);
//    }
//
//    @GetMapping("/get")
//    public ResponseEntity<ApiResponse<?>> find(
//            @RequestHeader(value = "Accept-Language", required = false) String lang
//    ) {
//        return bannerService.get(lang);
//    }
//
//    @PutMapping("/update")
//    public ResponseEntity<ApiResponse<Banner>> update(
//            @RequestBody Banner banner
//    ) {
//        return bannerService.update(banner);
//    }
//
//    @DeleteMapping("/delete")
//    public ResponseEntity<ApiResponse<?>> delete() {
//        return bannerService.delete();
//    }
//
//}

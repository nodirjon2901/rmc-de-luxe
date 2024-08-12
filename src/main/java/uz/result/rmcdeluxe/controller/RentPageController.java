package uz.result.rmcdeluxe.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.result.rmcdeluxe.entity.RentPageHowSearchForm;
import uz.result.rmcdeluxe.entity.RentPageSearchProperty;
import uz.result.rmcdeluxe.payload.ApiResponse;
import uz.result.rmcdeluxe.service.RentPageHowSearchFormService;
import uz.result.rmcdeluxe.service.RentPageSearchPropertyService;

import java.util.List;

@RestController
@RequestMapping("/v1/rent-page")
@RequiredArgsConstructor
public class RentPageController {

    private final RentPageSearchPropertyService searchPropertyService;

    private final RentPageHowSearchFormService searchFormService;

    @PostMapping("/search-property/create")
    public ResponseEntity<ApiResponse<RentPageSearchProperty>> createPropertyForm(
            @RequestParam(value = "json") String json,
            MultipartHttpServletRequest request
    ) {
        return searchPropertyService.create(json, request);
    }

    @GetMapping("/search-property/get")
    public ResponseEntity<ApiResponse<?>> findPropertyForm(
            @RequestHeader(value = "Accept-Language", required = false) String lang
    ) {
        return searchPropertyService.find(lang);
    }

    @PutMapping("/search-property/update")
    public ResponseEntity<ApiResponse<RentPageSearchProperty>> updatePropertyForm(
            @RequestParam(value = "json") String searchProperty,
            @RequestParam(required = false) MultipartHttpServletRequest request
    ) {
        return searchPropertyService.update(searchProperty, request);
    }

    @DeleteMapping("/search-property/delete")
    public ResponseEntity<ApiResponse<?>> deletePropertyForm() {
        return searchPropertyService.delete();
    }

    @PostMapping("/search-form/create")
    public ResponseEntity<ApiResponse<RentPageHowSearchForm>> createSearchForm(
            @RequestBody RentPageHowSearchForm searchForm
    ) {
        return searchFormService.create(searchForm);
    }

    @GetMapping("/search-form/get")
    public ResponseEntity<ApiResponse<?>> findSearchForm(
            @RequestHeader(value = "Accept-Language", required = false) String lang
    ) {
        return searchFormService.find(lang);
    }

    @PutMapping("/search-form/update")
    public ResponseEntity<ApiResponse<RentPageHowSearchForm>> updateSearchForm(
            @RequestBody RentPageHowSearchForm searchForm
    ) {
        return searchFormService.update(searchForm);
    }

    @DeleteMapping("/search-form/delete")
    public ResponseEntity<ApiResponse<?>> deleteSearchForm() {
        return searchFormService.delete();
    }


}

package uz.result.rmcdeluxe.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.result.rmcdeluxe.documentation.SwaggerConstants;
import uz.result.rmcdeluxe.payload.ApiResponse;
import uz.result.rmcdeluxe.payload.banner.*;
import uz.result.rmcdeluxe.payload.blog.BlogResponseDTO;
import uz.result.rmcdeluxe.payload.blog.BlogUpdateDTO;

@RestController
@RequestMapping("/api/banner")
@RequiredArgsConstructor
@Tag(name = "Banner - Баннер")
public class BannerController {

    @PostMapping(value = "/add", consumes = "multipart/form-data")
    @Operation(summary = "This API used for create banner")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "If successfully created you get status '201'",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BannerResponseDTO.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "If get 400 status Please read response 'message'. DON'T BE MAZGI",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
            )
    })
    public ResponseEntity<ApiResponse<BannerResponseDTO>> create(
            @Parameter(
                    name = "json",
                    description = "Banner details in JSON format (excluding photo). Insert text data as JSON format",
                    required = true,
                    schema = @Schema(implementation = BannerCreateDTO.class, format = "json", type = "string")
            )
            @RequestPart(value = "json") String json,
            @Parameter(
                    name = "photo",
                    description = "Photo file to be uploaded for the banner. Select picture of 'Banner' .jpg or .png or .svg file format",
                    required = true,
                    content = @Content(mediaType = "multipart/form-data",
                            schema = @Schema(type = "string", format = "binary"))
            )
            @RequestPart(value = "photo") MultipartFile photo
    ) {
        return ResponseEntity.ok(new ApiResponse<>());
    }

    @GetMapping("/get")
    @Operation(summary = "This API used to retrieve a banner")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200-a",
                    description = "a) Example schema for all language",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BannerResponseDTO.class)
                    )),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200-b",
                    description = "b) Example schema for one language",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BannerMapper.class)
                    )),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "If get 400 Please READ RESPONSE MESSAGE!!! DON'T BE MAZGI",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class)
                    ))
    })
    @Parameter(
            name = "Accept-Language",
            description = "Select language code one of : 'uz','ru','en' ",
            examples = {
                    @ExampleObject(
                            name = "uz",
                            description = "Data in uzbek",
                            summary = "uz",
                            value = "uz"
                    ),
                    @ExampleObject(
                            name = "ru",
                            description = "Data in russian",
                            summary = "ru",
                            value = "ru"
                    ),
                    @ExampleObject(
                            name = "en",
                            description = "Data in english",
                            summary = "en",
                            value = "en"
                    ),
                    @ExampleObject(
                            name = "-",
                            description = "Data in all language",
                            summary = "-",
                            value = "-"
                    )
            }
    )
    public ResponseEntity<ApiResponse<?>> findBanner(
            @RequestHeader(value = "Accept-Language", required = false) String lang
    ) {
        return ResponseEntity.ok(new ApiResponse<>());
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "This API used to retrieve a bannerSlider by its ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200-a",
                    description = "a) Example schema for all language",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BannerSliderResponseDTO.class)
                    )),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200-b",
                    description = "b) Example schema for one language",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BannerSliderMapper.class)
                    )),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "If get 400 Please READ RESPONSE MESSAGE!!! DON'T BE MAZGI",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class)
                    ))
    })
    @Parameter(
            name = "Accept-Language",
            description = "Select language code one of : 'uz','ru','en' ",
            examples = {
                    @ExampleObject(
                            name = "uz",
                            description = "Data in uzbek",
                            summary = "uz",
                            value = "uz"
                    ),
                    @ExampleObject(
                            name = "ru",
                            description = "Data in russian",
                            summary = "ru",
                            value = "ru"
                    ),
                    @ExampleObject(
                            name = "en",
                            description = "Data in english",
                            summary = "en",
                            value = "en"
                    ),
                    @ExampleObject(
                            name = "-",
                            description = "Data in all language",
                            summary = "-",
                            value = "-"
                    )
            }
    )
    @Parameter(
            name = "id",
            description = "ID of the bannerSlider to be retrieved",
            required = true)
    public ResponseEntity<ApiResponse<?>> findBannerSliderById(
            @PathVariable Long id,
            @RequestHeader(value = "Accept-Language", required = false) String lang
    ) {
        return ResponseEntity.ok(new ApiResponse<>());
    }

    @PutMapping(value = "/update", consumes = {"application/json"})
    @Operation(summary = "This API used to update an existing banner")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "'Banner' after edited",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BannerResponseDTO.class)
                    )),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "If get 400 Please READ RESPONSE MESSAGE!!! DON'T BE MAZGI",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class)
                    ))
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            description = SwaggerConstants.BANNER_UPDATE_DESCRIPTION,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = BannerResponseDTO.class),
                    examples = {

                            @ExampleObject(
                                    name = "Edit All fields",
                                    description = "Edit All fields at the same time",
                                    value = SwaggerConstants.BANNER_FULL_FORM
                            ),
                            @ExampleObject(
                                    name = "Edit custom field",
                                    description = "Send field which you want",
                                    value = SwaggerConstants.BANNER_CUSTOM_FIELD
                            ),
                            @ExampleObject(
                                    name = "Add slider",
                                    description = SwaggerConstants.ADD_SLIDER_DESC,
                                    value = SwaggerConstants.ADD_SLIDER_JSON
                            ),
                            @ExampleObject(
                                    name = "Delete slider",
                                    description = SwaggerConstants.DELETE_SLIDER_DESC,
                                    value = SwaggerConstants.DELETE_SLIDER_JSON
                            )
                    }
            )
    )
    public ResponseEntity<ApiResponse<BlogResponseDTO>> update(
            @RequestBody BlogUpdateDTO updateDTO
    ){
        return ResponseEntity.ok(new ApiResponse<>());
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "This API used to delete a slider by its ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "You get 200 status code when successfully deleted",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class)
                    )),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "If get 400 Please READ RESPONSE MESSAGE!!! DON'T BE MAZGI",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class)
                    ))
    })
    @Parameter(
            name = "id",
            description = "ID of the slider to be deleted",
            required = true)
    public ResponseEntity<ApiResponse<?>> delete(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(new ApiResponse<>());
    }


}

package uz.result.rmcdeluxe.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
import uz.result.rmcdeluxe.payload.blog.BlogTypeCreateDTO;
import uz.result.rmcdeluxe.payload.building.BuildingCreateDTO;
import uz.result.rmcdeluxe.payload.building.BuildingMapper;
import uz.result.rmcdeluxe.payload.building.BuildingResponseDTO;
import uz.result.rmcdeluxe.payload.building.BuildingUpdateDTO;
import uz.result.rmcdeluxe.payload.catalog.CatalogCreateDTO;
import uz.result.rmcdeluxe.payload.catalog.CatalogMapper;
import uz.result.rmcdeluxe.payload.catalog.CatalogResponseDTO;
import uz.result.rmcdeluxe.service.BuildingService;

import java.util.List;

@RestController
@RequestMapping("/api/build")
@RequiredArgsConstructor
@Tag(name = "Building Page")
public class BuildingController {

    private final BuildingService buildingService;

    @PostMapping(value = "/create", consumes = "multipart/form-data")
    @Operation(summary = "This API is used to create a new building entry with its gallery and videos.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "If successfully created, you get status '201'.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BuildingResponseDTO.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "If you get a 400 status, please read the response 'message'. DON'T BE MAZGI.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
            ),
    })
    public ResponseEntity<ApiResponse<BuildingResponseDTO>> create(
            @Parameter(
                    name = "json",
                    description = "Building details in JSON format. This field contains all the text information needed to create a building.",
                    required = true,
                    schema = @Schema(implementation = BuildingCreateDTO.class, format = "json", type = "string")
            )
            @RequestPart(value = "json") String json,
            @Parameter(
                    name = "gallery",
                    description = "A list of image files to be uploaded as the building's gallery. Accepted formats: .jpg, .png, .svg.",
                    required = false,
                    content = @Content(mediaType = "multipart/form-data",
                            array = @ArraySchema(schema = @Schema(type = "string", format = "binary")))
            )
            @RequestPart(value = "gallery", required = false) List<MultipartFile> gallery,
            @Parameter(
                    name = "video_list",
                    description = "A list of video files to be uploaded for the building. Accepted formats: .mp4 ",
                    required = false,
                    content = @Content(mediaType = "multipart/form-data",
                            array = @ArraySchema(schema = @Schema(type = "string", format = "binary")))
            )
            @RequestPart(value = "video_list", required = false) List<MultipartFile> videoList
    ) {
        return buildingService.create(json, gallery, videoList);
    }


    @GetMapping("/get/{id}")
    @Operation(summary = "This API used to retrieve a build by its ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200-a",
                    description = "a) Example schema for all language",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BuildingResponseDTO.class)
                    )),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200-b",
                    description = "b) Example schema for one language",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BuildingMapper.class)
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
            description = "ID of the build to be retrieved",
            required = true)
    public ResponseEntity<ApiResponse<?>> findById(
            @PathVariable Long id,
            @RequestHeader(value = "Accept-Language", required = false) String lang
    ) {
        return buildingService.findById(id, lang);
    }

    @GetMapping("/get-by-slug/{slug}")
    @Operation(summary = "This API used to retrieve a build by its SLUG")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200-a",
                    description = "a) Example schema for all language",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BuildingResponseDTO.class)
                    )),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200-b",
                    description = "b) Example schema for one language",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BuildingMapper.class)
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
            name = "slug",
            description = "SLUG of the build to be retrieved",
            required = true)
    public ResponseEntity<ApiResponse<?>> findBySlug(
            @PathVariable String slug,
            @RequestHeader(value = "Accept-Language", required = false) String lang
    ) {
        return buildingService.findBySlug(slug, lang);
    }

    @GetMapping("/get-all")
    @Operation(summary = "This API is used to retrieve all builds")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200-a",
                    description = "a) Example schema for all languages. List of builds",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = BuildingResponseDTO.class))
                    )),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200-b",
                    description = "b) Example schema for one language. List of builds",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = BuildingMapper.class))
                    )
            ),
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
            description = "Select language code one of : 'uz','ru','en'",
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
                            description = "Data in all languages",
                            summary = "-",
                            value = "-"
                    )
            }
    )
    public ResponseEntity<ApiResponse<?>> findAll(
            @RequestHeader(value = "Accept-Language", required = false) String lang
    ) {
        return buildingService.findAll(lang);
    }

    @PutMapping("/update")
    @Operation(summary = "This API used to update an existing build")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "'Build' after edited",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BuildingResponseDTO.class)
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
            description = SwaggerConstants.BUILD_UPDATE_DESCRIPTION,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = BuildingResponseDTO.class),
                    examples = {
                            @ExampleObject(
                                    name = "Edit All fields",
                                    description = "Edit All fields at the same time",
                                    value = SwaggerConstants.BUILD_FULL_FORM
                            ),
                            @ExampleObject(
                                    name = "Edit custom field",
                                    description = "Send field which you want",
                                    value = SwaggerConstants.BUILD_CUSTOM_FIELD
                            ),
                            @ExampleObject(
                                    name = "Add photo/video",
                                    description = "Send 'photo/video' without 'id' and it added",
                                    value = SwaggerConstants.ADD_PHOTO_OR_VIDEO_IN_BUILDING
                            ),

                    }
            )
    )
    public ResponseEntity<ApiResponse<BuildingResponseDTO>> update(
            @RequestBody BuildingUpdateDTO updateDTO
    ) {
        return buildingService.update(updateDTO);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "This API used to delete a build by its ID")
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
            description = "ID of the catalog to be deleted",
            required = true)
    public ResponseEntity<ApiResponse<?>> delete(
            @PathVariable Long id
    ) {
        return buildingService.delete(id);
    }


}

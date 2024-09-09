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
import uz.result.rmcdeluxe.payload.blog.*;

import java.util.List;

@RestController
@RequestMapping("/api/blog")
@RequiredArgsConstructor
@Tag(name = "Blog - Блог")
public class BlogController {

//    @PostMapping(value = "/create", consumes = {"multipart/form-data"})
//    @Operation(summary = "This API used for create blog")
//    @ApiResponses(value = {
//            @io.swagger.v3.oas.annotations.responses.ApiResponse(
//                    responseCode = "201",
//                    description = "If successfully created you get  status '201'",
//                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BlogResponseDTO.class))
//            ),
//            @io.swagger.v3.oas.annotations.responses.ApiResponse(
//                    responseCode = "400",
//                    description = "If get 400 status Please read response 'message'. DON'T BE MAZGI",
//                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))),
//    })
//    public ResponseEntity<ApiResponse<BlogResponseDTO>> create(
//            @Parameter(
//                    name = "json",
//                    description = "Blog details in JSON format (excluding photo). Insert text data as JSON format",
//                    required = true,
//                    schema = @Schema(implementation = BlogCreateDTO.class, format = "json", type = "string")
//            )
//            @RequestPart(value = "json") String json,
//            MultipartHttpServletRequest request
//    ) {
//        return ResponseEntity.ok(new ApiResponse<>());
//    }

    @PostMapping(value = "/create", consumes = {"multipart/form-data"})
    @Operation(summary = "This API is used for creating a blog with optional images")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "If successfully created, you get status '201'",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BlogResponseDTO.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "If you get status 400, please read the response 'message'. DON'T BE MAZGI",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
            ),
    })
    public ResponseEntity<ApiResponse<BlogResponseDTO>> create(
            @Parameter(
                    name = "json",
                    description = "Blog details in JSON format (excluding photo). Insert text data as JSON format.",
                    required = true,
                    schema = @Schema(implementation = BlogCreateDTO.class, format = "json", type = "string")
            )
            @RequestPart(value = "json") String json,

            @Parameter(
                    name = "imageKeys",
                    description = "JSON array with keys to indicate which image corresponds to which block. Each image key will match its uploaded image.",
                    required = false
            )
            @RequestPart(value = "imageKeys", required = false) String imageKeys,

            @Parameter(
                    name = "files",
                    description = "Multiple blog images in multipart format. You can upload multiple images.",
                    required = false
            )
            @RequestPart(value = "files", required = false) List<MultipartFile> files
    ) {
        return ResponseEntity.ok(new ApiResponse<>());
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "This API used to retrieve a blog by its ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200-a",
                    description = "a) Example schema for all language",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BlogResponseDTO.class)
                    )),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200-b",
                    description = "b) Example schema for one language",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BlogMapper.class)
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
            description = "ID of the blog to be retrieved",
            required = true)
    public ResponseEntity<ApiResponse<?>> findById(
            @PathVariable Long id,
            @RequestHeader(value = "Accept-Language", required = false) String lang
    ) {
        return ResponseEntity.ok(new ApiResponse<>());
    }

    @GetMapping("/get-by-slug/{slug}")
    @Operation(summary = "This API used to retrieve a blog by its SLUG")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200-a",
                    description = "a) Example schema for all language",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BlogResponseDTO.class)
                    )),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200-b",
                    description = "b) Example schema for one language",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BlogMapper.class)
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
            description = "SLUG of the blog to be retrieved",
            required = true)
    public ResponseEntity<ApiResponse<?>> findBySlug(
            @PathVariable String slug,
            @RequestHeader(value = "Accept-Language", required = false) String lang
    ) {
        return ResponseEntity.ok(new ApiResponse<>());
    }

    @GetMapping("/get-all")
    @Operation(summary = "This API is used to retrieve all blogs or blogs based on various filters")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200-a",
                    description = "a) Example schema for all languages. List of blogs",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = BlogResponseDTO.class))
                    )),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200-b",
                    description = "b) Example schema for one language. List of blogs",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = BlogMapper.class))
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "If you get status 400, please read the response message.",
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
    @Parameter(
            name = "main",
            description = "To get a list of blogs that should be displayed on the main page, set 'main' to true",
            required = false,
            schema = @Schema(type = "boolean")
    )
    @Parameter(
            name = "popular",
            description = "If you set 'popular' to true, it will return you a list of blogs sorted by the number of views",
            required = false,
            schema = @Schema(type = "boolean")
    )
    @Parameter(
            name = "new",
            description = "If you set 'New' to true, it will return a list of blogs sorted by creation time from newest to oldest",
            required = false,
            schema = @Schema(type = "boolean")
    )
    @Parameter(
            name = "old",
            description = "If you set 'Old' to true, it will return a list of blogs sorted by creation time from oldest to newest",
            required = false,
            schema = @Schema(type = "boolean")
    )
    @Parameter(
            name = "typeID",
            description = "Filter by blog type. If only the typeId is sent, the Backend will find the type by id and then filter",
            required = false,
            schema = @Schema(type = "integer")
    )
    @Parameter(
            name = "page",
            description = "Page number to retrieve. Default is 1. Number of blogs to be retrieved per page default is 10",
            required = false,
            schema = @Schema(type = "integer", defaultValue = "1")
    )
    public ResponseEntity<ApiResponse<?>> findAllWithOptionalFilters(
            @RequestParam(value = "main", required = false) boolean main,
            @RequestParam(value = "popular", required = false) boolean popular,
            @RequestParam(value = "new", required = false) boolean aNew,
            @RequestParam(value = "old", required = false) boolean old,
            @RequestParam(value = "typeID", required = false) Long typeId,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestHeader(value = "Accept-Language", required = false) String lang,
            @Parameter(hidden = true)
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size
    ) {
        return ResponseEntity.ok(new ApiResponse<>());
    }

    @PutMapping(value = "/update", consumes = {"application/json"})
    @Operation(summary = "This API used to update an existing blog")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "'Blog' after edited",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BlogResponseDTO.class)
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
            description = SwaggerConstants.BlOG_UPDATE_DESCRIPTION,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = BlogResponseDTO.class),
                    examples = {

                            @ExampleObject(
                                    name = "Edit All fields",
                                    description = "Edit All fields at the same time",
                                    value = SwaggerConstants.BLOG_FULL_FORM
                            ),
                            @ExampleObject(
                                    name = "Edit custom field",
                                    description = "Send field which you want",
                                    value = SwaggerConstants.BLOG_CUSTOM_FIELD
                            ),
                            @ExampleObject(
                                    name = "Add BlogOption",
                                    description = SwaggerConstants.ADD_BLOG_OPTION_DESC,
                                    value = SwaggerConstants.ADD_BLOG_OPTION_JSON
                            ),
                            @ExampleObject(
                                    name = "Add photo in BlogOption",
                                    description = SwaggerConstants.ADD_PHOTO_IN_OPTION_DESC,
                                    value = SwaggerConstants.ADD_PHOTO_IN_OPTION_JSON
                            ),
                            @ExampleObject(
                                    name = "Delete BlogOption",
                                    description = SwaggerConstants.DELETE_BLOG_OPTION_DESC,
                                    value = SwaggerConstants.DELETE_BLOG_OPTION_JSON
                            )
                    }
            )
    )
    public ResponseEntity<ApiResponse<BlogResponseDTO>> update(
            @RequestBody BlogUpdateDTO updateDTO
    ) {
        return ResponseEntity.ok(new ApiResponse<>());
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "This API used to delete a blog by its ID")
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
            description = "ID of the blog to be deleted",
            required = true)
    public ResponseEntity<ApiResponse<?>> delete(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(new ApiResponse<>());
    }


}

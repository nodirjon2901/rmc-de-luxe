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
import uz.result.rmcdeluxe.payload.catalog.CatalogCreateDTO;
import uz.result.rmcdeluxe.payload.catalog.CatalogMapper;
import uz.result.rmcdeluxe.payload.catalog.CatalogResponseDTO;
import uz.result.rmcdeluxe.payload.catalog.CatalogUpdateDTO;

@RestController
@RequestMapping("/api/catalog")
@RequiredArgsConstructor
@Tag(name = "Catalog - Каталог")
public class CatalogController {

    @PostMapping(value = "/create", consumes = {"multipart/from-data"})
    @Operation(summary = "This API used for create catalog")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "If successfully created you get  status '201'",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CatalogResponseDTO.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "If get 400 status Please read response 'message'. DON'T BE MAZGI",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))),
    })
    public ResponseEntity<ApiResponse<CatalogResponseDTO>> create(
            @Parameter(
                    name = "json",
                    description = "Catalog details in JSON format (excluding photo). Insert text data as JSON format",
                    required = true,
                    schema = @Schema(implementation = CatalogCreateDTO.class, format = "json", type = "string")
            )
            @RequestPart(value = "json") String json,
            @Parameter(
                    name = "photo",
                    description = "Photo file to be uploaded for the catalog. Select picture of 'Catalog' .jpg or .png or .svg file format",
                    required = true,
                    content = @Content(mediaType = "multipart/form-data",
                            schema = @Schema(type = "string", format = "binary")))
            @RequestPart(value = "photo") MultipartFile photo
    ) {
        return ResponseEntity.ok(new ApiResponse<>());
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "This API used to retrieve a catalog by its ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200-a",
                    description = "a) Example schema for all language",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CatalogResponseDTO.class)
                    )),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200-b",
                    description = "b) Example schema for one language",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CatalogMapper.class)
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
            description = "ID of the catalog to be retrieved",
            required = true)
    public ResponseEntity<ApiResponse<?>> findById(
            @PathVariable Long id,
            @RequestHeader(value = "Accept-Language", required = false) String lang
    ) {
        return ResponseEntity.ok(new ApiResponse<>());
    }

    @GetMapping("/get-by-slug/{slug}")
    @Operation(summary = "This API used to retrieve a catalog by its SLUG")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200-a",
                    description = "a) Example schema for all language",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CatalogResponseDTO.class)
                    )),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200-b",
                    description = "b) Example schema for one language",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CatalogMapper.class)
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
            description = "SLUG of the catalog to be retrieved",
            required = true)
    public ResponseEntity<ApiResponse<?>> findBySlug(
            @PathVariable String slug,
            @RequestHeader(value = "Accept-Language", required = false) String lang
    ) {
        return ResponseEntity.ok(new ApiResponse<>());
    }

    @GetMapping("/get-all")
    @Operation(summary = "This API used to retrieve all catalogs")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200-a",
                    description = "a) Example schema for all language. List of catalogs",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = CatalogResponseDTO.class))
                    )),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200-b",
                    description = "b) Example scheme for one language. List of catalogs",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = CatalogMapper.class))
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
            required = true,
            examples = {
                    @ExampleObject(
                            name = "uz",
                            description = "List of catalogs in uzbek",
                            summary = "uz",
                            value = "uz"
                    ),
                    @ExampleObject(
                            name = "ru",
                            description = "List of catalogs in russian",
                            summary = "ru",
                            value = "ru"
                    ),
                    @ExampleObject(
                            name = "en",
                            description = "List of catalogs in english",
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
    public ResponseEntity<ApiResponse<?>> findAll(
            @RequestHeader(value = "Accept-Language", required = false) String lang
    ) {
        return ResponseEntity.ok(new ApiResponse<>());
    }

    @GetMapping("/get-all-by-filter")
    @Operation(summary = "This API is used to retrieve catalogs based on various filters")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200-a",
                    description = "a) Example schema for all language. List of catalogs",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = CatalogResponseDTO.class))
                    )),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200-b",
                    description = "b) Example scheme for one language. List of catalogs",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = CatalogMapper.class))
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
                    )
            }
    )
    @Parameter(
            name = "districtID",
            description = "Filter by district. If only the districtId is sent, the Backend will find the district by id and then filter",
            required = false,
            schema = @Schema(type = "integer")
    )
    @Parameter(
            name = "from",
            description = "Filter by minimum price",
            required = false,
            schema = @Schema(type = "number", format = "double")
    )
    @Parameter(
            name = "to",
            description = "Filter by maximum price",
            required = false,
            schema = @Schema(type = "number", format = "double")
    )
    @Parameter(
            name = "typeID",
            description = "Filter by catalog type. If only the typeId is sent, the Backend will find the type by id and then filter",
            required = false,
            schema = @Schema(type = "integer")
    )
    @Parameter(
            name = "room-number",
            description = "Filter by number of rooms",
            required = false,
            schema = @Schema(type = "string")
    )
    @Parameter(
            name = "deadline",
            description = "Filter by deadline",
            required = false,
            schema = @Schema(type = "string", format = "date")
    )
    public ResponseEntity<ApiResponse<?>> findAllWithFilters(
            @RequestParam(value = "districtID", required = false) Long districtId,
            @RequestParam(value = "from", required = false) Double fromPrice,
            @RequestParam(value = "to", required = false) Double toPrice,
            @RequestParam(value = "typeID", required = false) Long typeId,
            @RequestParam(value = "room-number", required = false) String roomNumber,
            @RequestParam(value = "deadline", required = false) String deadline,
            @RequestHeader(value = "Accept-Language", required = false) String lang
    ) {
        return ResponseEntity.ok(new ApiResponse<>());
    }

    @PutMapping(value = "/update", consumes = {"application/json"})
    @Operation(summary = "This API used to update an existing catalog")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "'Catalog' after edited",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CatalogResponseDTO.class)
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
            description = SwaggerConstants.CATALOG_UPDATE_DESCRIPTION,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CatalogResponseDTO.class),
                    examples = {
                            @ExampleObject(
                                    name = "Edit All fields",
                                    description = "Edit All fields at the same time",
                                    value = SwaggerConstants.CATALOG_FULL_FORM
                            ),
                            @ExampleObject(
                                    name = "Edit custom field",
                                    description = "Send field which you want",
                                    value = SwaggerConstants.CATALOG_CUSTOM_FIELD
                            )
                    }
            )
    )
    public ResponseEntity<ApiResponse<CatalogResponseDTO>> update(
            @RequestBody CatalogUpdateDTO updateDTO
    ) {
        return ResponseEntity.ok(new ApiResponse<>());
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "This API used to delete a catalog by its ID")
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
        return ResponseEntity.ok(new ApiResponse<>());
    }


}

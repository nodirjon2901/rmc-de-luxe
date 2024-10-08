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
import uz.result.rmcdeluxe.payload.building.PlanApartmentCreateDTO;
import uz.result.rmcdeluxe.payload.building.PlanApartmentMapper;
import uz.result.rmcdeluxe.payload.building.PlanApartmentResponseDTO;
import uz.result.rmcdeluxe.payload.building.PlanApartmentUpdateDTO;
import uz.result.rmcdeluxe.payload.catalog.CatalogResponseDTO;
import uz.result.rmcdeluxe.payload.catalog.CatalogUpdateDTO;
import uz.result.rmcdeluxe.service.PlanApartmentService;

@RestController
@RequestMapping("/api/plan-apartment")
@RequiredArgsConstructor
@Tag(name = "Plan of apartment - Планировки и доступные квартиры")
public class PlanApartmentController {

    private final PlanApartmentService apartmentService;

    @PostMapping(value = "/create", consumes = {"multipart/form-data"})
    @Operation(summary = "This API used for create plan of apartment")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "If successfully created you get  status '201'",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PlanApartmentResponseDTO.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "If get 400 status Please read response 'message'. DON'T BE MAZGI",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))),
    })
    public ResponseEntity<ApiResponse<PlanApartmentResponseDTO>> create(
            @Parameter(
                    name = "json",
                    description = "PlanOfApartment details in JSON format (excluding photo). Insert text data as JSON format",
                    required = true,
                    schema = @Schema(implementation = PlanApartmentCreateDTO.class, format = "json", type = "string")
            )
            @RequestPart(value = "json") String json,
            @Parameter(
                    name = "photo",
                    description = "Photo file to be uploaded for the PlanOfApartment. Select picture of 'PlanOfApartment' .jpg or .png or .svg file format",
                    required = true,
                    content = @Content(mediaType = "multipart/form-data",
                            schema = @Schema(type = "string", format = "binary")))
            @RequestPart(value = "photo") MultipartFile photo
    ) {
        return apartmentService.create(json, photo);
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "This API used to retrieve a PlanOfApartment by its ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200-a",
                    description = "a) Example schema for all language",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PlanApartmentResponseDTO.class)
                    )),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200-b",
                    description = "b) Example schema for one language",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PlanApartmentMapper.class)
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
            description = "ID of the PlanOfApartment to be retrieved",
            required = true)
    public ResponseEntity<ApiResponse<?>> findById(
            @PathVariable Long id,
            @RequestHeader(value = "Accept-Language", required = false) String lang
    ) {
        return apartmentService.findById(lang, id);
    }

    @GetMapping("/get-all")
    @Operation(summary = "This API is used to retrieve all PlanOfApartment or PlanOfApartment based on various filters")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200-a",
                    description = "a) Example schema for all languages. List of PlanOfApartment",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PlanApartmentResponseDTO.class))
                    )),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200-b",
                    description = "b) Example schema for one language. List of PlanOfApartment",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PlanApartmentMapper.class))
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
            name = "floor",
            description = "Filter by floorCount.",
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
            name = "page",
            description = "Page number to retrieve. Default is 1. Number of blogs to be retrieved per page default is 12",
            required = false,
            schema = @Schema(type = "integer", defaultValue = "1")
    )
    public ResponseEntity<ApiResponse<?>> findAllWithOptionalFilters(
            @RequestParam(value = "from", required = false) Double fromPrice,
            @RequestParam(value = "to", required = false) Double toPrice,
            @RequestParam(value = "room-number", required = false) String roomNumber,
            @RequestParam(value = "floor", required = false) Integer floorCount,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestHeader(value = "Accept-Language", required = false) String lang,
            @Parameter(hidden = true)
            @RequestParam(value = "size", required = false, defaultValue = "12") Integer size
    ) {
        return apartmentService.findAll(lang, page, size, fromPrice, toPrice, roomNumber,floorCount);
    }


    @PutMapping(value = "/update", consumes = {"application/json"})
    @Operation(summary = "This API used to update an existing PlanOfApartment")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "'PlanOfApartment' after edited",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PlanApartmentResponseDTO.class)
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
            description = SwaggerConstants.APARTMENT_UPDATE_DESCRIPTION,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PlanApartmentUpdateDTO.class),
                    examples = {
                            @ExampleObject(
                                    name = "Edit All fields",
                                    description = "Edit All fields at the same time",
                                    value = SwaggerConstants.APARTMENT_FULL_FORM
                            ),
                            @ExampleObject(
                                    name = "Edit custom field",
                                    description = "Send field which you want",
                                    value = SwaggerConstants.APARTMENT_CUSTOM_FIELD
                            )
                    }
            )
    )
    public ResponseEntity<ApiResponse<PlanApartmentResponseDTO>> update(
            @RequestBody PlanApartmentUpdateDTO updateDTO
    ) {
        return apartmentService.update(updateDTO);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "This API used to delete a PlanOfApartment by its ID")
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
            description = "ID of the PlanOfApartment to be deleted",
            required = true)
    public ResponseEntity<ApiResponse<?>> delete(
            @PathVariable Long id
    ) {
        return apartmentService.delete(id);
    }

}

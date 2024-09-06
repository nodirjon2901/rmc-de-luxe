package uz.result.rmcdeluxe.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.result.rmcdeluxe.documentation.SwaggerConstants;
import uz.result.rmcdeluxe.payload.ApiResponse;
import uz.result.rmcdeluxe.payload.district.DistrictCreateDTO;
import uz.result.rmcdeluxe.payload.district.DistrictMapper;
import uz.result.rmcdeluxe.payload.district.DistrictResponseDTO;
import uz.result.rmcdeluxe.payload.houseType.HouseTypeCreateDTO;
import uz.result.rmcdeluxe.payload.houseType.HouseTypeMapper;
import uz.result.rmcdeluxe.payload.houseType.HouseTypeResponseDTO;

@RestController
@RequestMapping("/api/house-type")
@RequiredArgsConstructor
@Tag(name = "House Type - Тип жилья")
public class HouseTypeController {

    @PostMapping(value = "/create",consumes = {"application/json"})
    @Operation(summary = "This API used for create type")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "If successfully created you get  status '201'",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = HouseTypeResponseDTO.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "If get 400 status Please read response 'message'. DON'T BE MAZGI",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))),
    })
    public ResponseEntity<ApiResponse<HouseTypeResponseDTO>> create(
            @Parameter(
                    name = "createDTO",
                    description = "DTO containing details of district",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = HouseTypeCreateDTO.class))
            )
            @RequestBody HouseTypeCreateDTO createDTO
    ){
        return ResponseEntity.ok(new ApiResponse<>());
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "This API used to retrieve a type by its ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200-a",
                    description = "a) Example schema for all language",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HouseTypeResponseDTO.class)
                    )),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200-b",
                    description = "b) Example schema for one language",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HouseTypeMapper.class)
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
            description = "ID of the type to be retrieved",
            required = true)
    public ResponseEntity<ApiResponse<?>> findById(
            @PathVariable Long id,
            @RequestHeader(value = "Accept-Language", required = false) String lang
    ){
        return ResponseEntity.ok(new ApiResponse<>());
    }

    @GetMapping("/get-all")
    @Operation(summary = "This API used to retrieve all type")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200-a",
                    description = "a) Example schema for all language. List of types",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = HouseTypeResponseDTO.class))
                    )),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200-b",
                    description = "b) Example scheme for one language. List of types",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = HouseTypeMapper.class))
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
                            description = "List of types in uzbek",
                            summary = "uz",
                            value = "uz"
                    ),
                    @ExampleObject(
                            name = "ru",
                            description = "List of types in russian",
                            summary = "ru",
                            value = "ru"
                    ),
                    @ExampleObject(
                            name = "en",
                            description = "List of types in english",
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
    ){
        return ResponseEntity.ok(new ApiResponse<>());
    }

    @PutMapping("/update")
    @Operation(summary = "This API used to update an existing type")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "'HouseType' after edited",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HouseTypeResponseDTO.class)
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
            description = SwaggerConstants.HOUSE_TYPE_UPDATE_DESCRIPTION,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = HouseTypeResponseDTO.class),
                    examples = {
                            @ExampleObject(
                                    name = "Edit All fields",
                                    description = "Edit All fields at the same time",
                                    value = SwaggerConstants.DISTRICT_FULL_FORM
                            ),
                            @ExampleObject(
                                    name = "Edit custom field",
                                    description = "Send field which you want",
                                    value = SwaggerConstants.DISTRICT_CUSTOM_FIELD
                            )
                    }
            )
    )
    public ResponseEntity<ApiResponse<HouseTypeResponseDTO>> update(
            @RequestBody HouseTypeCreateDTO createDTO
    ){
        return ResponseEntity.ok(new ApiResponse<>());
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "This API used to delete a type by its ID")
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
            description = "ID of the type to be deleted",
            required = true)
    public ResponseEntity<ApiResponse<?>> delete(
            @PathVariable Long id
    ){
        return ResponseEntity.ok(new ApiResponse<>());
    }

}

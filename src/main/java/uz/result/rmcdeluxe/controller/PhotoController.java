package uz.result.rmcdeluxe.controller;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.result.rmcdeluxe.entity.Photo;
import uz.result.rmcdeluxe.payload.ApiResponse;
import uz.result.rmcdeluxe.payload.PhotoDTO;
import uz.result.rmcdeluxe.service.PhotoService;

import java.util.List;

@RestController
@RequestMapping("/photo")
@RequiredArgsConstructor
@Tag(name = "Photo - Фото")
public class PhotoController
{

    private final PhotoService photoService;

    @PostMapping(value = "/create",consumes = {"multipart/form-data"})
    @Operation(summary = "Upload photo to server")
    public ResponseEntity<ApiResponse<List<Photo>>> upload(
            @RequestParam(value = "photo") List<MultipartFile> photo)
    {
        return photoService.upload(photo);
    }

    @GetMapping("/get/{name}")
    @Operation(summary = "Show a photo")
    @Hidden
    public ResponseEntity<byte[]> getPhoto(@PathVariable(name = "name") String name)
    {
        return photoService.findByName(name);
    }

    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Photo updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PhotoDTO.class)))})
    @Operation(summary = "Replace photo to another")
    @Parameters({
            @Parameter(name = "id", description = "Show 'id' in url path for which photo is updated", required = true)
    })
    @PutMapping(value = "/update/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<ApiResponse<PhotoDTO>> updatePhoto(
            @PathVariable(name = "id")
            Long id,

            @RequestPart(value = "new-photo")
            MultipartFile newPhoto)
    {
        return photoService.update(id, newPhoto);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete photo from table and also file system")
    public ResponseEntity<ApiResponse<?>> deletePhoto(
            @PathVariable
            @Parameter(description = "Show 'id' which photo must deleted")
            Long id)
    {
        return photoService.delete(id);
    }
}

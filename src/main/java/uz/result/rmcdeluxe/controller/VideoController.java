package uz.result.rmcdeluxe.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.result.rmcdeluxe.entity.VideoFile;
import uz.result.rmcdeluxe.payload.ApiResponse;
import uz.result.rmcdeluxe.payload.VideoDTO;
import uz.result.rmcdeluxe.service.VideoFileService;

import java.io.IOException;

@RestController
@RequestMapping("/api/video")
@RequiredArgsConstructor
@Tag(name = "Video - Видео")
public class VideoController {

    private final VideoFileService service;

    @PostMapping(value = "/save", consumes = {"multipart/form-data"})
    @Operation(summary = "Upload photo to server")
    public ResponseEntity<ApiResponse<VideoFile>> save(
            @RequestPart(value = "video") MultipartFile video
    ) {
        return service.save(video);
    }

    @GetMapping("/get/{file-name}")
    @Operation(summary = "Show a photo")
    public ResponseEntity<Resource> getVideo(
            @RequestHeader HttpHeaders headers,
            @PathVariable("file-name") String fileName
    ) throws IOException {
        return service.getVideoByName(headers, fileName);
    }

    @PutMapping(value = "/update/{id}", consumes = {"multipart/form-data"})
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Photo updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = VideoFile.class))
            )
    })
    @Operation(summary = "Replace video to another")
    @Parameters({
            @Parameter(name = "id", description = "Show 'id' in url path for which video is updated", required = true)
    })
    public ResponseEntity<ApiResponse<VideoDTO>> updateVideo(
            @PathVariable Long id,
            @RequestPart(value = "video") MultipartFile video
    ){
        return service.update(id, video);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete video from table and also file system")
    public ResponseEntity<ApiResponse<?>> deleteVideo(
            @PathVariable
            @Parameter(description = "Show 'id' which video must deleted") Long id
    ){
        return service.delete(id);
    }

}

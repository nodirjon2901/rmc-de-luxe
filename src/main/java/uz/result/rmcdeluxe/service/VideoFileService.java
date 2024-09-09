package uz.result.rmcdeluxe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRange;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import uz.result.rmcdeluxe.entity.VideoFile;
import uz.result.rmcdeluxe.payload.ApiResponse;
import uz.result.rmcdeluxe.repository.VideoFileRepository;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class VideoFileService {

    @Value("${photo.upload.path}")
    private String uploadPath;

    @Value("${server.base-url}")
    private String baseUrl;

    private final VideoFileRepository videoFileRepository;

    private final ResourceLoader resourceLoader;

    public ResponseEntity<ApiResponse<VideoFile>> save(MultipartFile video) {
        ApiResponse<VideoFile> response = new ApiResponse<>();
        if (video.isEmpty()) {
            response.setMessage("Please select a file to upload");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        if (!Objects.equals(video.getContentType(), "video/mp4")) {
            response.setMessage("Only MP4 videos are allowed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        try {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(video.getOriginalFilename()));
            Path targetLocation = Paths.get(uploadPath).resolve(fileName);

            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            Files.copy(video.getInputStream(), targetLocation);
            VideoFile videoFile = VideoFile
                    .builder()
                    .videoUrl(baseUrl + "/video/" + fileName)
                    .build();
            response.setData(videoFileRepository.save(videoFile));
            response.setMessage("Video successfully saved!");
            return ResponseEntity.ok(response);

        } catch (IOException e) {
            response.setMessage("Could not upload the file: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Resource> getVideoByName(HttpHeaders headers, String fileName) throws IOException {
        Path videoPath = Paths.get(uploadPath).resolve(fileName);  // Faylni to'g'ridan-to'g'ri diskdan olish
        File videoFile = videoPath.toFile();

        if (!videoFile.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        long fileSize = Files.size(videoPath);
        List<HttpRange> ranges = headers.getRange();

        if (ranges.isEmpty()) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, "video/mp4")
                    .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(fileSize))
                    .body(new ByteArrayResource(Files.readAllBytes(videoPath)));
        }

        HttpRange range = ranges.get(0);
        long start = range.getRangeStart(fileSize);
        long end = range.getRangeEnd(fileSize);

        byte[] data = new byte[(int) (end - start + 1)];
        try (RandomAccessFile file = new RandomAccessFile(videoFile, "r")) {
            file.seek(start);
            file.readFully(data);
        }

        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .header(HttpHeaders.CONTENT_TYPE, "video/mp4")
                .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(data.length))
                .header(HttpHeaders.CONTENT_RANGE, "bytes " + start + "-" + end + "/" + fileSize)
                .body(new ByteArrayResource(data));
    }



}

package uz.result.rmcdeluxe.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import uz.result.rmcdeluxe.entity.Photo;
import uz.result.rmcdeluxe.entity.VideoFile;
import uz.result.rmcdeluxe.exception.IllegalPhotoTypeException;
import uz.result.rmcdeluxe.exception.NotFoundException;
import uz.result.rmcdeluxe.payload.ApiResponse;
import uz.result.rmcdeluxe.payload.VideoDTO;
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

    private final Logger logger = LoggerFactory.getLogger(VideoFileService.class);

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
            VideoFile videoFile = videoFileRepository.save(new VideoFile());//--
            saveToFile(video, videoFile);//--
//            Files.copy(video.getInputStream(), targetLocation);
//            VideoFile videoFile = VideoFile
//                    .builder()
//                    .videoUrl(baseUrl + "/video/" + fileName)
//                    .build();
            response.setData(videoFileRepository.save(videoFile));
            response.setMessage("Video successfully saved!");
            return ResponseEntity.ok(response);

        } catch (IOException e) {
            response.setMessage("Could not upload the file: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void saveToFile(MultipartFile file, VideoFile video) throws IOException {
        String originalFileName = video.getId() + "-" + Objects.requireNonNull(file.getOriginalFilename()).replaceAll(" ", "%20");

        Path filePath = Paths.get(uploadPath + File.separator + originalFileName);

        file.transferTo(filePath);

        video.setName(originalFileName);
        video.setFilepath(filePath.toFile().getAbsolutePath());
        video.setVideoUrl(baseUrl + "/video/" + video.getName());
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

    public ResponseEntity<ApiResponse<VideoDTO>> update(Long id, MultipartFile videoFile) {
        ApiResponse<VideoDTO> response = new ApiResponse<>();
        VideoFile fromDB = videoFileRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Video not found by id: " + id));

        if (videoFile.getContentType() != null && !(videoFile.getContentType().equals("video/mp4"))) {
            throw new IllegalPhotoTypeException("Unsupported video type: " + videoFile.getContentType() + ", Support only video/mp4");
        }

        try {
            if (fromDB.getFilepath() != null && !fromDB.getFilepath().isEmpty()) {
                deleteFromFile(fromDB.getFilepath());
            }
            saveToFile(videoFile, fromDB);

            response.setMessage("Updated");
            response.setData(new VideoDTO(videoFileRepository.save(fromDB)));
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteFromFile(String filePath) throws IOException {
        try {
            Files.delete(Paths.get(filePath));
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new IOException(e);
        }
    }

//    public ResponseEntity<ApiResponse<Photo>> upload(MultipartFile photo)
//    {
//        if (photo == null || photo.isEmpty())
//            throw new NotFoundException("Photo is null or empty");
//        ApiResponse<Photo> response = new ApiResponse<>();
//
//        response.setMessage("Uploaded");
//        response.setData(save(photo));
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

    public ResponseEntity<ApiResponse<?>> delete(Long id) {
        ApiResponse<?> response=new ApiResponse<>();
        VideoFile video = videoFileRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Video not found by id: " + id));
        try {
            videoFileRepository.deleteByCustom(video.getId());
        }catch (Exception e){
            VideoFile video1=new VideoFile();
            video1.setId(id);
            videoFileRepository.save(video1);
        }

        try {
            deleteFromFile(video.getFilepath());
        }catch (IOException e){
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
        response.setMessage("Deleted");
        return ResponseEntity.ok(response);
    }

}

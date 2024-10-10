package uz.result.rmcdeluxe.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.result.rmcdeluxe.entity.Photo;
import uz.result.rmcdeluxe.exception.IllegalPhotoTypeException;
import uz.result.rmcdeluxe.exception.NotFoundException;
import uz.result.rmcdeluxe.payload.ApiResponse;
import uz.result.rmcdeluxe.payload.PhotoDTO;
import uz.result.rmcdeluxe.repository.PhotoRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PhotoService {

    @Value("${photo.upload.path}")
    private String photoUploadPath;

    @Value("${server.base-url}")
    private String baseUrl;

    private final PhotoRepository photoRepository;

    private static final Logger logger = LoggerFactory.getLogger(PhotoService.class);

    public Photo save(MultipartFile file) {
        if (file.getContentType() != null && !(file.getContentType().equals("image/png") ||
                file.getContentType().equals("image/svg+xml") ||
                file.getContentType().equals("image/jpeg"))) {
            throw new IllegalPhotoTypeException("Unsupported image type: " + file.getContentType());
        }

        try {
            Photo photo = photoRepository.save(new Photo());
            saveToFile(file, photo);

            return photoRepository.save(photo);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Photo getEmpty() {
        return photoRepository.save(new Photo());
    }

    public ResponseEntity<byte[]> findByName(String name) {
        try {
            Photo photo = photoRepository.findByName(name).orElseThrow(() -> new NotFoundException("Photo not found: " + name));

            Path imagePath = Paths.get(photo.getFilepath());
            byte[] imageBytes = Files.readAllBytes(imagePath);

            switch (photo.getType()) {
                case "image/png" -> {
                    return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(imageBytes);
                }
                case "image/jpeg" -> {
                    return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
                }
                case "image/svg+xml" -> {
                    HttpHeaders headers = new HttpHeaders();
                    headers.add(HttpHeaders.CONTENT_TYPE, "image/svg+xml");

                    return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
                }
            }

        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new NotFoundException(e.getMessage());
        }
        return null;
    }

    public ResponseEntity<ApiResponse<PhotoDTO>> update(Long id, MultipartFile file) {
        ApiResponse<PhotoDTO> response = new ApiResponse<>();
        Photo fromDb = photoRepository.findById(id).orElseThrow(() -> new NotFoundException("Photo not found by id: " + id));

        if (file.getContentType() != null && !(file.getContentType().equals("image/png") ||
                file.getContentType().equals("image/svg+xml") ||
                file.getContentType().equals("image/jpeg"))) {
            throw new IllegalPhotoTypeException("Unsupported image type: " + file.getContentType() + " , Support only image/png or image/svg+xml or image/jpeg");
        }

        try {
            if (fromDb.getFilepath() != null && !fromDb.getFilepath().isEmpty())
                deleteFromFile(fromDb.getFilepath());

            saveToFile(file, fromDb);

            response.setMessage("Updated");
            response.setData(new PhotoDTO(photoRepository.save(fromDb)));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void saveToFile(MultipartFile file, Photo photo) throws IOException {
        String originalFileName = photo.getId() + "-" + Objects.requireNonNull(file.getOriginalFilename()).replaceAll(" ", "%20");

        Path filePath = Paths.get(photoUploadPath + File.separator + originalFileName);

        file.transferTo(filePath);

        photo.setName(originalFileName);
        photo.setFilepath(filePath.toFile().getAbsolutePath());
        photo.setType(file.getContentType());
        photo.setHttpUrl(baseUrl + "/photo/" + photo.getName());
    }

    public void deleteFromFile(String filePath) throws IOException {
        try {
            if (filePath != null)
                Files.delete(Paths.get(filePath));
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new IOException(e);
        }
    }


    public ResponseEntity<ApiResponse<List<Photo>>> upload(List<MultipartFile> photo) {
        if (photo == null || photo.isEmpty())
            throw new NotFoundException("Photo is null or empty");
        ApiResponse<List<Photo>> response = new ApiResponse<>();

        response.setData(new ArrayList<>());
        photo.forEach(i -> response.getData().add(save(i)));

        response.setMessage("Uploaded");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<?>> delete(Long id) {
        ApiResponse<?> response = new ApiResponse<>();
        Photo photo = photoRepository.findById(id).orElseThrow(() -> new NotFoundException("Photo not found by id: " + id));

        try {
            photoRepository.deleteByCustom(photo.getId());
        } catch (Exception e) {
            Photo photo1 = new Photo();
            photo1.setId(id);
            photoRepository.save(photo1);
        }


        try {
            deleteFromFile(photo.getFilepath());
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
        response.setMessage("Deleted");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}

package uz.result.rmcdeluxe.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uz.result.rmcdeluxe.payload.PhotoDTO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "photo")
public class Photo {

    private static final Logger LOGGER = LoggerFactory.getLogger(Photo.class);
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @JsonIgnore
    String name;

    @JsonIgnore
    String filepath;

    @JsonProperty(value = "url")
    @Column(unique = true)
    String httpUrl;

    @JsonIgnore
    String type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    Building building;

    public Photo(PhotoDTO dto)
    {
        if (dto == null)
            return;

        this.id = dto.getId();
        this.httpUrl = dto.getUrl();
    }

    @PreRemove
    private void deleteFile()
    {
        if (this.filepath != null)
        {
            try
            {
                Files.delete(Paths.get(filepath));
            } catch (IOException e)
            {
                LOGGER.error("Error deleting file: ", e);
            }
        }
    }
}

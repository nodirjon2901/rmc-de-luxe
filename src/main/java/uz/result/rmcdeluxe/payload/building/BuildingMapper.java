package uz.result.rmcdeluxe.payload.building;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.Building;
import uz.result.rmcdeluxe.entity.Photo;
import uz.result.rmcdeluxe.entity.VideoFile;
import uz.result.rmcdeluxe.exception.LanguageNotSupported;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BuildingMapper {

    Long id;

    String title;

    String description;

    List<Photo> gallery;

    List<VideoFile> videoList;

    boolean active;

    public BuildingMapper(Building build, String lang){
        this.id= build.getId();
        this.gallery=build.getGallery();
        this.videoList=build.getVideoList();
        this.active= build.isActive();
        switch (lang.toLowerCase()){

            case "uz":{
                this.title= build.getTitleUz();
                this.description= build.getDescriptionUz();
                break;
            }

            case "ru":{
                this.title= build.getTitleRu();
                this.description= build.getDescriptionRu();
                break;
            }

            case "en":{
                this.title= build.getTitleEn();
                this.description= build.getDescriptionEn();
                break;
            }
            default:
                throw new LanguageNotSupported("Language not supported: "+lang);
        }
    }

}

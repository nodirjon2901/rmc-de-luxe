package uz.result.rmcdeluxe.service.temporary;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.result.rmcdeluxe.entity.temporary.ToRentPageWorkProcess;
import uz.result.rmcdeluxe.entity.temporary.ToRentWorkProcessOption;
import uz.result.rmcdeluxe.exception.AlreadyExistsException;
import uz.result.rmcdeluxe.exception.NotFoundException;
import uz.result.rmcdeluxe.payload.ApiResponse;
import uz.result.rmcdeluxe.payload.temporary.ToRentPageWorkProcessDTO;
import uz.result.rmcdeluxe.repository.temporary.ToRentPageWorkProcessRepository;
import uz.result.rmcdeluxe.repository.temporary.ToRentWorkProcessOptionRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ToRentPageWorkProcessService {

    private final ToRentPageWorkProcessRepository workProcessRepository;

    private final ToRentWorkProcessOptionRepository optionRepository;

    public ResponseEntity<ApiResponse<ToRentPageWorkProcess>> create(ToRentPageWorkProcess workProcess) {
        ApiResponse<ToRentPageWorkProcess> response = new ApiResponse<>();
        Optional<ToRentPageWorkProcess> processOptional = workProcessRepository.findAll().stream().findFirst();
        if (processOptional.isPresent()) {
            throw new AlreadyExistsException("WorkProcess form is already created");
        }
        ToRentPageWorkProcess save = workProcessRepository.save(workProcess);
        response.setData(save);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<?>> find(String lang) {
        ToRentPageWorkProcess workProcess = workProcessRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new NotFoundException("WorkProcess form is not found"));
        if (lang != null) {
            ApiResponse<ToRentPageWorkProcessDTO> response = new ApiResponse<>();
            response.setData(new ToRentPageWorkProcessDTO(workProcess, lang));
            return ResponseEntity.ok(response);
        }
        ApiResponse<ToRentPageWorkProcess> response = new ApiResponse<>();
        response.setData(workProcess);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<ToRentPageWorkProcess>> update(ToRentPageWorkProcess workProcess) {
        ApiResponse<ToRentPageWorkProcess> response = new ApiResponse<>();
        ToRentPageWorkProcess fromDb = workProcessRepository.findById(workProcess.getId())
                .orElseThrow(() -> new NotFoundException("WorkProcess form is not found by id: " + workProcess.getId()));

        if (workProcess.getOptions() != null) {
            List<ToRentWorkProcessOption> processOptions = workProcess.getOptions();
            List<ToRentWorkProcessOption> dbOptions = fromDb.getOptions();
            List<ToRentWorkProcessOption> optionsToRemove = new ArrayList<>();
            for (ToRentWorkProcessOption processOption : processOptions) {
                if (processOption.getId() != null) {
                    for (ToRentWorkProcessOption dbOption : dbOptions) {
                        if (dbOption.getId().equals(processOption.getId())) {

                            if (processOption.getNameUz() != null) {
                                dbOption.setNameUz(processOption.getNameUz());
                            }
                            if (processOption.getNameRu() != null) {
                                dbOption.setNameRu(processOption.getNameRu());
                            }
                            if (processOption.getNameEn() != null) {
                                dbOption.setNameEn(processOption.getNameEn());
                            }

                            if (processOption.getDescriptionUz() != null) {
                                dbOption.setDescriptionUz(processOption.getDescriptionUz());
                            }
                            if (processOption.getDescriptionRu() != null) {
                                dbOption.setDescriptionRu(processOption.getDescriptionRu());
                            }
                            if (processOption.getDescriptionEn() != null) {
                                dbOption.setDescriptionEn(processOption.getDescriptionEn());
                            }
                            if (processOption.getOrderNum() != null) {
                                dbOption.setOrderNum(processOption.getOrderNum());
                            }

                            if (processOption.getNameUz() == null && processOption.getNameRu() == null && processOption.getNameEn() == null && processOption.getOrderNum() == null
                                    && processOption.getDescriptionUz() == null && processOption.getDescriptionEn() == null && processOption.getDescriptionRu() == null) {
                                optionsToRemove.add(dbOption);
                            }

                        }
                    }
                } else {
                    processOption.setWorkProcess(fromDb);
                    dbOptions.add(processOption);
                }
            }
            for (ToRentWorkProcessOption removeOption : optionsToRemove) {
                dbOptions.remove(removeOption);
                optionRepository.deleteByCustom(removeOption.getId());
            }
            dbOptions.sort(Comparator.comparing(ToRentWorkProcessOption::getOrderNum));
        }
        response.setData(workProcessRepository.save(fromDb));
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<?>> delete() {
        ApiResponse<?> response = new ApiResponse<>();
        ToRentPageWorkProcess workProcess = workProcessRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new NotFoundException("WorkProcess form is not found"));
        workProcessRepository.delete(workProcess);
        response.setMessage("Successfully deleted");
        return ResponseEntity.ok(response);
    }

}

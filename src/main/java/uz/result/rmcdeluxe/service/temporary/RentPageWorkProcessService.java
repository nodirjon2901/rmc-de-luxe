package uz.result.rmcdeluxe.service.temporary;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.result.rmcdeluxe.entity.temporary.RentPageWorkProcess;
import uz.result.rmcdeluxe.entity.temporary.RentPageWorkProcessOption;
import uz.result.rmcdeluxe.exception.AlreadyExistsException;
import uz.result.rmcdeluxe.exception.NotFoundException;
import uz.result.rmcdeluxe.payload.ApiResponse;
import uz.result.rmcdeluxe.payload.temporary.RentPageWorkProcessDTO;
import uz.result.rmcdeluxe.repository.temporary.RentPageWorkProcessOptionRepository;
import uz.result.rmcdeluxe.repository.temporary.RentPageWorkProcessRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RentPageWorkProcessService {

    private final RentPageWorkProcessRepository workProcessRepository;

    private final RentPageWorkProcessOptionRepository optionRepository;

    public ResponseEntity<ApiResponse<RentPageWorkProcess>> create(RentPageWorkProcess workProcess) {
        ApiResponse<RentPageWorkProcess> response = new ApiResponse<>();
        Optional<RentPageWorkProcess> workProcessOptional = workProcessRepository.findAll().stream().findFirst();
        if (workProcessOptional.isPresent()) {
            throw new AlreadyExistsException("Work process form is already created");
        }
        RentPageWorkProcess save = workProcessRepository.save(workProcess);
        response.setData(save);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<?>> find(String lang) {
        RentPageWorkProcess workProcess = workProcessRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new NotFoundException("Work process form is not found"));
        if (lang != null) {
            ApiResponse<RentPageWorkProcessDTO> response = new ApiResponse<>();
            response.setData(new RentPageWorkProcessDTO(workProcess, lang));
            return ResponseEntity.ok(response);
        }
        ApiResponse<RentPageWorkProcess> response = new ApiResponse<>();
        response.setData(workProcess);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<RentPageWorkProcess>> update(RentPageWorkProcess workProcess) {
        ApiResponse<RentPageWorkProcess> response = new ApiResponse<>();
        RentPageWorkProcess fromDb = workProcessRepository.findById(workProcess.getId())
                .orElseThrow(() -> new NotFoundException("Work process form is not found with id: " + workProcess.getId()));

        if (workProcess.getOptions() != null) {
            List<RentPageWorkProcessOption> processOptions = workProcess.getOptions();
            List<RentPageWorkProcessOption> dbOptions = fromDb.getOptions();
            List<RentPageWorkProcessOption> optionsToRemove = new ArrayList<>();

            for (RentPageWorkProcessOption processOption : processOptions) {
                if (processOption.getId() != null) {
                    for (RentPageWorkProcessOption dbOption : dbOptions) {
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

                            if (processOption.getNameUz() == null && processOption.getNameRu() == null && processOption.getNameEn() == null && processOption.getOrderNum() == null &&
                                    processOption.getDescriptionUz() == null && processOption.getDescriptionRu() == null && processOption.getDescriptionEn() == null) {
                                optionsToRemove.add(dbOption);
                            }

                        }
                    }
                } else {
                    processOption.setWorkProcess(fromDb);
                    dbOptions.add(processOption);
                }
            }
            for (RentPageWorkProcessOption removeOption : optionsToRemove) {
                dbOptions.remove(removeOption);
                optionRepository.deleteByCustom(removeOption.getId());
            }
            dbOptions.sort(Comparator.comparing(RentPageWorkProcessOption::getOrderNum));
        }
        response.setData(workProcessRepository.save(fromDb));
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<?>> delete() {
        ApiResponse<?> response = new ApiResponse<>();
        RentPageWorkProcess workProcess = workProcessRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new NotFoundException("Work process form is not found"));
        workProcessRepository.delete(workProcess);
        response.setMessage("Successfully deleted");
        return ResponseEntity.ok(response);
    }

}

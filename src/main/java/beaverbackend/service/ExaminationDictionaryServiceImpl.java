package beaverbackend.service;

import beaverbackend.controllers.common.BadRequestException;
import beaverbackend.controllers.doctor.ExaminationDictSearchReq;
import beaverbackend.enums.BadRequestDictEnum;
import beaverbackend.jpa.model.ExaminationDictionary;
import beaverbackend.jpa.repository.ExaminationDictionaryRepository;
import beaverbackend.jpa.specification.ExaminationDictionarySpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExaminationDictionaryServiceImpl implements ExaminationDictionaryService {

    private final ExaminationDictionaryRepository examinationDictionaryRepository;

    @Override
    public List<ExaminationDictionary> searchExaminationDictionary(ExaminationDictSearchReq req) {
        return examinationDictionaryRepository.findAll(ExaminationDictionarySpecification.searchSpecification(req));
    }

    @Override
    public ExaminationDictionary findByExaminationCode(String code) throws Exception {
        return examinationDictionaryRepository.findByCode(code)
                .orElseThrow(() -> new BadRequestException(BadRequestDictEnum.BAD_EXAMINATION_CODE, code));
    }
}

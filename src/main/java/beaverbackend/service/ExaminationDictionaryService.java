package beaverbackend.service;

import beaverbackend.controllers.doctor.ExaminationDictSearchReq;
import beaverbackend.jpa.model.ExaminationDictionary;

import java.util.List;

public interface ExaminationDictionaryService {

    public List<ExaminationDictionary> searchExaminationDictionary(ExaminationDictSearchReq req);

    public ExaminationDictionary findByExaminationCode(String code) throws Exception;

}

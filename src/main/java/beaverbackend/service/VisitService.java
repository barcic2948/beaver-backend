package beaverbackend.service;

import beaverbackend.controllers.common.BadRequestException;
import beaverbackend.controllers.common.VisitSearchReq;
import beaverbackend.controllers.doctor.SetVisitStatusReq;
import beaverbackend.controllers.receptionist.VisitCreateReq;
import beaverbackend.jpa.model.Visit;

import java.util.List;

public interface VisitService {

    Visit createNewVisit(VisitCreateReq req) throws BadRequestException;

    List<Visit> searchVisits(VisitSearchReq req);

    Visit cancelVisit(Long id) throws BadRequestException;

    Visit setVisitStatus(SetVisitStatusReq req) throws BadRequestException;
}

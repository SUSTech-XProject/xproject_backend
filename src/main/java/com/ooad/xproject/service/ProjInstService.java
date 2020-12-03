package com.ooad.xproject.service;

import com.ooad.xproject.dto.StudentDTO;
import com.ooad.xproject.entity.ProjectInst;
import com.ooad.xproject.entity.RecordInst;
import com.ooad.xproject.entity.SubmissionInst;

import java.util.List;

public interface ProjInstService {

    ProjectInst getProjectInst(Integer piid);

    List<ProjectInst> getProInstList(Integer projId);

    List<StudentDTO> getStudentDTOByProjInstId(Integer projInstId);

    // select by record, return recordinst of the record
    List<RecordInst> getRecordInstList(Integer rid);

    // select by Project id and student id, return the student's RecordInsts under the project
    List<RecordInst> getRecordInstList(Integer pid, Integer sid);

    // select by submission, return submissionInst of the submission
    List<SubmissionInst> getSubmissionInstList(Integer sbmid);

    // select by Project id and student id, return the student's submissionInsts under the submission
   List<SubmissionInst> getSubmissionInstList(Integer pid, Integer sid);
}
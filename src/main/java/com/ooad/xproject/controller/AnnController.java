package com.ooad.xproject.controller;

import com.ooad.xproject.constant.RespStatus;
import com.ooad.xproject.dto.AnnDTO;
import com.ooad.xproject.dto.StudentProjDTO;
import com.ooad.xproject.entity.Announcement;
import com.ooad.xproject.entity.Role;
import com.ooad.xproject.service.*;
import com.ooad.xproject.utils.RoleUtils;
import com.ooad.xproject.vo.Result;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AnnController {

    private final RoleService roleService;
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final HomeService homeService;
    private final AnnService annService;
    private final MailService mailService;
    private final Logger logger = LogManager.getLogger(this.getClass().getName());
    private final ProjectService projService;

    public AnnController(RoleService testService, StudentService studentService, TeacherService teacherService, HomeService homeService, AnnService annService, MailService mailService, ProjectService projService) {
        this.roleService = testService;
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.homeService = homeService;
        this.annService = annService;
        this.mailService = mailService;
        this.projService = projService;
    }

    @ResponseBody
    @GetMapping("api/all/project/ann")
    public Result<?> getAnnouncementList(@RequestParam("projId") int projId) {
        List<AnnDTO> annList = annService.getAnnList(projId);
        return new Result<>(annList);
    }

    @ResponseBody
    @PostMapping("api/teacher/project/ann/modify")
    public Result<?> postModifyAnnouncement(@RequestBody Announcement announcement) {
        boolean success = annService.updateAnn(announcement);
        if (success) {
            return new Result<>(true);
        } else {
            return new Result<>(RespStatus.FAIL, "Update failed", false);
        }
    }

    @ResponseBody
    @PostMapping("api/teacher/project/ann/add")
    public Result<?> postAddAnnouncement(@RequestBody Announcement announcement) {
        String username = RoleUtils.getUsername();
        Role role = roleService.getByUsername(username);

        // check project accessible
        if (!projService.isAccessible(role.getRoleId(), announcement.getProjId())) {
            return new Result<>(RespStatus.FAIL, "Project is not accessible");
        }

        announcement.setCreatorId(role.getRoleId());

        boolean success = annService.addAnn(announcement);
        if (success) {
            List<StudentProjDTO> stdList = projService.getStdProjList(announcement.getProjId());
            List<String> mailList = stdList.stream().map(StudentProjDTO::getEmail).collect(Collectors.toList());
//            mailService.sendMailToStudent(mailList, )

            return new Result<>(true);
        } else {
            return new Result<>(RespStatus.FAIL, "Add failed", false);
        }
    }

    @ResponseBody
    @GetMapping("api/teacher/project/ann/delete")
    public Result<?> getDeleteAnnouncement(@RequestParam("annId") int annId) {
        boolean success = annService.deleteAnn(annId);
        if (success) {
            return new Result<>(true);
        } else {
            return new Result<>(RespStatus.FAIL, "Delete failed", false);
        }
    }
}

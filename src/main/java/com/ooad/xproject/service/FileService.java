package com.ooad.xproject.service;

import com.ooad.xproject.entity.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

public interface FileService {
    ResponseEntity<byte[]> download(HttpServletRequest request, String realPath, String userAgent, String filename, boolean inline);

    String upload(MultipartFile[] files, String uploadPath);

    String upload(MultipartFile file, String uploadPath, String fileName);

    void deleteFolder(File folderPath);

    File getOrCreateStudentDir(SubmissionInst submissionInst);
}

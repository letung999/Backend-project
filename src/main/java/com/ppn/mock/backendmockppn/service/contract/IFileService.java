package com.ppn.mock.backendmockppn.service.contract;

import com.ppn.mock.backendmockppn.exception.FileDownloadException;
import com.ppn.mock.backendmockppn.exception.FileUploadException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IFileService {
    String uploadFile(MultipartFile multipartFile) throws FileUploadException, IOException;

    Object downloadFile(String fileName) throws FileDownloadException, IOException;

    boolean delete(String fileName);
}


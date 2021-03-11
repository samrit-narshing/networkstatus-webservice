package com.project.core.services.util;

import com.project.core.models.entities.test.FileInfo;
import com.project.core.models.entities.util.UploadedFileContent;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Samrit
 */
public interface FileHandlerService {

    public FileInfo uploadFile_AsMultpartFile(MultipartFile inputFile) throws Exception;

    public FileInfo uploadProfileImageOfUser_AsObject(UploadedFileContent uploadedFileContent) throws Exception;

    public boolean deleteProfilePictureOfUser(String filename) throws Exception;

    public FileInfo uploadProfileImageOfLocation_AsObject(UploadedFileContent uploadedFileContent) throws Exception;

    public boolean deleteProfilePictureOfLocation(String filename) throws Exception;

    public FileInfo uploadImageOfNews_AsObject(UploadedFileContent uploadedFileContent) throws Exception;

    public boolean deletePictureOfNews(String filename) throws Exception;

    public FileInfo uploadLogoImageOfCompanyInfo_AsObject(UploadedFileContent uploadedFileContent) throws Exception;

    public boolean deleteLogoImageOfCompanyInfo(String filename) throws Exception;

    public FileInfo uploadProfileImageOfCompanyInfo_AsObject(UploadedFileContent uploadedFileContent) throws Exception;

    public boolean deleteProfileImageOfCompanyInfo(String filename) throws Exception;

    public FileInfo uploadCSVFileToAddNodesOfUser_AsObject(UploadedFileContent uploadedFileContent) throws Exception;

}

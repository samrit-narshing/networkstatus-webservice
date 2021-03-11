/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.core.services.impl.util;

import com.project.core.models.entities.test.FileInfo;
import com.project.core.models.entities.util.UploadedFileContent;
import com.project.core.security.PropertiesConfig;
import com.project.core.services.util.FileHandlerService;
import com.project.test.ImageResizer;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Samrit
 */
@Service
public class FileHandlerImpl implements FileHandlerService {

    @Autowired
    @Resource(name = "propertiesConfig")
    private PropertiesConfig propertiesConfig;

    @Override
    public FileInfo uploadFile_AsMultpartFile(MultipartFile inputFile) throws Exception {
        FileInfo fileInfo = new FileInfo();
        if (!inputFile.isEmpty()) {

            String originalFilename = inputFile.getOriginalFilename();
//                File destinationFile = new File(context.getRealPath("/WEB-INF/uploaded") + File.separator + originalFilename);
            File destinationFile = new File(propertiesConfig.getProperty("upload_profile_image_path") + File.separator + originalFilename);

            inputFile.transferTo(destinationFile);
            fileInfo.setFileName(destinationFile.getPath());
            fileInfo.setFileSize(inputFile.getSize());
            return fileInfo;

        } else {
            return null;
        }
    }

    @Override
    public FileInfo uploadProfileImageOfUser_AsObject(UploadedFileContent uploadedFileContent) throws Exception {
        try {
            //Save the uploaded file to this folder
            String UPLOADED_FOLDER = propertiesConfig.getProperty("upload_profile_image_path");
            // Get the file and save it somewhere
            byte[] bytes = uploadedFileContent.getBytes();

            String filename = (uploadedFileContent.getNewFilename() == null || uploadedFileContent.getNewFilename().trim().equalsIgnoreCase("")) ? uploadedFileContent.getFilename() : uploadedFileContent.getNewFilename();
//            Path path = Paths.get(UPLOADED_FOLDER + uploadedFileContent.getFilename());
            Path path = Paths.get(UPLOADED_FOLDER + filename);
            Files.write(path, bytes);

            saveProfileImageOfUserInOtherSizes(filename);
            FileInfo fileInfo = new FileInfo();
            fileInfo.setFileName(uploadedFileContent.getFilename());
            fileInfo.setFileSize(uploadedFileContent.getFileSize());
            return fileInfo;
        } catch (IOException e) {
            return null;
        }
    }

    public void saveProfileImageOfUserInOtherSizes(String fileName) throws IOException {

        String uploadedSourcePath = propertiesConfig.getProperty("upload_profile_image_path");
        String uploadedSourcePathWithFileToRead = uploadedSourcePath + fileName;
        // For Large 640x480
        //Save the uploaded file to this folder
        String uploadedSourcePathForLarge = propertiesConfig.getProperty("upload_profile_large_image_path");
        String uploadedSourcePathWithFileForLargeToWrite = uploadedSourcePathForLarge + fileName;
        int aspectRatioMaintainedWidthForLarge = 480;
        int aspectRatioMaintainedHeightForLarge = 640;
        new ImageResizer().resizeByAscpectRatio(uploadedSourcePathWithFileToRead, uploadedSourcePathWithFileForLargeToWrite, aspectRatioMaintainedWidthForLarge, aspectRatioMaintainedHeightForLarge);

        // For Medium 320x240
        //Save the uploaded file to this folder
        String uploadedSourcePathForMedium = propertiesConfig.getProperty("upload_profile_medium_image_path");
        String uploadedSourcePathWithFileForMediumToWrite = uploadedSourcePathForMedium + fileName;
        int aspectRatioMaintainedWidthForMedium = 240;
        int aspectRatioMaintainedHeightForMedium = 320;
        new ImageResizer().resizeByAscpectRatio(uploadedSourcePathWithFileToRead, uploadedSourcePathWithFileForMediumToWrite, aspectRatioMaintainedWidthForMedium, aspectRatioMaintainedHeightForMedium);

        // For Small 128x96
        //Save the uploaded file to this folder
        String uploadedSourcePathForSmall = propertiesConfig.getProperty("upload_profile_small_image_path");
        String uploadedSourcePathWithFileForSmallToWrite = uploadedSourcePathForSmall + fileName;
        int aspectRatioMaintainedWidthForSmall = 96;
        int aspectRatioMaintainedHeightForSmall = 128;
        new ImageResizer().resizeByAscpectRatio(uploadedSourcePathWithFileToRead, uploadedSourcePathWithFileForSmallToWrite, aspectRatioMaintainedWidthForSmall, aspectRatioMaintainedHeightForSmall);

    }

    @Override
    public boolean deleteProfilePictureOfUser(String filename) throws Exception {
        try {
            String UPLOADED_FOLDER = propertiesConfig.getProperty("upload_profile_image_path");
            String path = UPLOADED_FOLDER + filename;
            File file = new File(path);
            if (file.exists()) {
                if (file.delete()) {
                    System.out.println(file.getName() + " is deleted!");
                    deleteProfileImageInOtherSizes(filename);
                    return true;
                } else {
                    System.out.println("Delete operation is failed.");
                    return false;
                }
            } else {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void deleteProfileImageInOtherSizes(String fileName) throws IOException {

        //For Large
        String uploadedSourcePathForLarge = propertiesConfig.getProperty("upload_profile_large_image_path");
        String path = uploadedSourcePathForLarge + fileName;
        File largeFile = new File(path);
        if (largeFile.exists()) {
            if (largeFile.delete()) {
                System.out.println(largeFile.getName() + " is deleted!");
            } else {
                System.out.println("Delete operation is failed.");
            }
        }

        //For Medium
        String uploadedSourcePathForMedium = propertiesConfig.getProperty("upload_profile_medium_image_path");
        String uploadedSourcePathWithFileForMediumToDelete = uploadedSourcePathForMedium + fileName;
        File mediumFile = new File(uploadedSourcePathWithFileForMediumToDelete);
        if (mediumFile.exists()) {
            if (mediumFile.delete()) {
                System.out.println(mediumFile.getName() + " is deleted!");

            } else {
                System.out.println("Delete operation is failed.");

            }
        }

        //For Small
        String uploadedSourcePathForSmall = propertiesConfig.getProperty("upload_profile_small_image_path");
        String uploadedSourcePathWithFileForSmallToDelete = uploadedSourcePathForSmall + fileName;
        File SmallFile = new File(uploadedSourcePathWithFileForSmallToDelete);
        if (SmallFile.exists()) {
            if (SmallFile.delete()) {
                System.out.println(SmallFile.getName() + " is deleted!");

            } else {
                System.out.println("Delete operation is failed.");

            }
        }

    }

    @Override
    public FileInfo uploadProfileImageOfLocation_AsObject(UploadedFileContent uploadedFileContent) throws Exception {
        try {
            //Save the uploaded file to this folder
            String UPLOADED_FOLDER = propertiesConfig.getProperty("upload_location_image_path");
            // Get the file and save it somewhere
            byte[] bytes = uploadedFileContent.getBytes();

            String filename = (uploadedFileContent.getNewFilename() == null || uploadedFileContent.getNewFilename().trim().equalsIgnoreCase("")) ? uploadedFileContent.getFilename() : uploadedFileContent.getNewFilename();
//            Path path = Paths.get(UPLOADED_FOLDER + uploadedFileContent.getFilename());
            Path path = Paths.get(UPLOADED_FOLDER + filename);
            Files.write(path, bytes);

            saveProfileImageOfLocationInOtherSizes(filename);
            FileInfo fileInfo = new FileInfo();
            fileInfo.setFileName(uploadedFileContent.getFilename());
            fileInfo.setFileSize(uploadedFileContent.getFileSize());
            return fileInfo;
        } catch (IOException e) {
            return null;
        }
    }

    public void saveProfileImageOfLocationInOtherSizes(String fileName) throws IOException {

        String uploadedSourcePath = propertiesConfig.getProperty("upload_location_image_path");
        String uploadedSourcePathWithFileToRead = uploadedSourcePath + fileName;
        // For Large 640x480
        //Save the uploaded file to this folder
        String uploadedSourcePathForLarge = propertiesConfig.getProperty("upload_location_large_image_path");
        String uploadedSourcePathWithFileForLargeToWrite = uploadedSourcePathForLarge + fileName;
        int aspectRatioMaintainedWidthForLarge = 480;
        int aspectRatioMaintainedHeightForLarge = 640;
        new ImageResizer().resizeByAscpectRatio(uploadedSourcePathWithFileToRead, uploadedSourcePathWithFileForLargeToWrite, aspectRatioMaintainedWidthForLarge, aspectRatioMaintainedHeightForLarge);

        // For Medium 320x240
        //Save the uploaded file to this folder
        String uploadedSourcePathForMedium = propertiesConfig.getProperty("upload_location_medium_image_path");
        String uploadedSourcePathWithFileForMediumToWrite = uploadedSourcePathForMedium + fileName;
        int aspectRatioMaintainedWidthForMedium = 240;
        int aspectRatioMaintainedHeightForMedium = 320;
        new ImageResizer().resizeByAscpectRatio(uploadedSourcePathWithFileToRead, uploadedSourcePathWithFileForMediumToWrite, aspectRatioMaintainedWidthForMedium, aspectRatioMaintainedHeightForMedium);

        // For Small 128x96
        //Save the uploaded file to this folder
        String uploadedSourcePathForSmall = propertiesConfig.getProperty("upload_location_small_image_path");
        String uploadedSourcePathWithFileForSmallToWrite = uploadedSourcePathForSmall + fileName;
        int aspectRatioMaintainedWidthForSmall = 96;
        int aspectRatioMaintainedHeightForSmall = 128;
        new ImageResizer().resizeByAscpectRatio(uploadedSourcePathWithFileToRead, uploadedSourcePathWithFileForSmallToWrite, aspectRatioMaintainedWidthForSmall, aspectRatioMaintainedHeightForSmall);

    }

    @Override
    public boolean deleteProfilePictureOfLocation(String filename) throws Exception {
        try {
            String UPLOADED_FOLDER = propertiesConfig.getProperty("upload_location_image_path");
            String path = UPLOADED_FOLDER + filename;
            File file = new File(path);
            if (file.exists()) {
                if (file.delete()) {
                    System.out.println(file.getName() + " is deleted!");
                    deleteProfileImageInOtherSizes(filename);
                    return true;
                } else {
                    System.out.println("Delete operation is failed.");
                    return false;
                }
            } else {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public FileInfo uploadImageOfNews_AsObject(UploadedFileContent uploadedFileContent) throws Exception {
        try {
            //Save the uploaded file to this folder
            String UPLOADED_FOLDER = propertiesConfig.getProperty("upload_news_image_path");
            // Get the file and save it somewhere
            byte[] bytes = uploadedFileContent.getBytes();

            String filename = (uploadedFileContent.getNewFilename() == null || uploadedFileContent.getNewFilename().trim().equalsIgnoreCase("")) ? uploadedFileContent.getFilename() : uploadedFileContent.getNewFilename();
//            Path path = Paths.get(UPLOADED_FOLDER + uploadedFileContent.getFilename());
            Path path = Paths.get(UPLOADED_FOLDER + filename);
            Files.write(path, bytes);

            saveHeadingImageOfNewsInOtherSizes(filename);
            FileInfo fileInfo = new FileInfo();
            fileInfo.setFileName(uploadedFileContent.getFilename());
            fileInfo.setFileSize(uploadedFileContent.getFileSize());
            return fileInfo;
        } catch (IOException e) {
            return null;
        }
    }

    public void saveHeadingImageOfNewsInOtherSizes(String fileName) throws IOException {

        String uploadedSourcePath = propertiesConfig.getProperty("upload_news_image_path");
        String uploadedSourcePathWithFileToRead = uploadedSourcePath + fileName;
        // For Large 640x480
        //Save the uploaded file to this folder
        String uploadedSourcePathForLarge = propertiesConfig.getProperty("upload_news_large_image_path");
        String uploadedSourcePathWithFileForLargeToWrite = uploadedSourcePathForLarge + fileName;
        int aspectRatioMaintainedWidthForLarge = 480;
        int aspectRatioMaintainedHeightForLarge = 640;
        new ImageResizer().resizeByAscpectRatio(uploadedSourcePathWithFileToRead, uploadedSourcePathWithFileForLargeToWrite, aspectRatioMaintainedWidthForLarge, aspectRatioMaintainedHeightForLarge);

        // For Medium 320x240
        //Save the uploaded file to this folder
        String uploadedSourcePathForMedium = propertiesConfig.getProperty("upload_news_medium_image_path");
        String uploadedSourcePathWithFileForMediumToWrite = uploadedSourcePathForMedium + fileName;
        int aspectRatioMaintainedWidthForMedium = 240;
        int aspectRatioMaintainedHeightForMedium = 320;
        new ImageResizer().resizeByAscpectRatio(uploadedSourcePathWithFileToRead, uploadedSourcePathWithFileForMediumToWrite, aspectRatioMaintainedWidthForMedium, aspectRatioMaintainedHeightForMedium);

        // For Small 128x96
        //Save the uploaded file to this folder
        String uploadedSourcePathForSmall = propertiesConfig.getProperty("upload_news_small_image_path");
        String uploadedSourcePathWithFileForSmallToWrite = uploadedSourcePathForSmall + fileName;
        int aspectRatioMaintainedWidthForSmall = 96;
        int aspectRatioMaintainedHeightForSmall = 128;
        new ImageResizer().resizeByAscpectRatio(uploadedSourcePathWithFileToRead, uploadedSourcePathWithFileForSmallToWrite, aspectRatioMaintainedWidthForSmall, aspectRatioMaintainedHeightForSmall);

    }

    @Override
    public boolean deletePictureOfNews(String filename) throws Exception {
        try {
            String UPLOADED_FOLDER = propertiesConfig.getProperty("upload_news_image_path");
            String path = UPLOADED_FOLDER + filename;
            File file = new File(path);
            if (file.exists()) {
                if (file.delete()) {
                    System.out.println(file.getName() + " is deleted!");
                    deletePicureOfNewsImageInOtherSizes(filename);
                    return true;
                } else {
                    System.out.println("Delete operation is failed.");
                    return false;
                }
            } else {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void deletePicureOfNewsImageInOtherSizes(String fileName) throws IOException {

        //For Large
        String uploadedSourcePathForLarge = propertiesConfig.getProperty("upload_news_large_image_path");
        String path = uploadedSourcePathForLarge + fileName;
        File largeFile = new File(path);
        if (largeFile.exists()) {
            if (largeFile.delete()) {
                System.out.println(largeFile.getName() + " is deleted!");
            } else {
                System.out.println("Delete operation is failed.");
            }
        }

        //For Medium
        String uploadedSourcePathForMedium = propertiesConfig.getProperty("upload_news_medium_image_path");
        String uploadedSourcePathWithFileForMediumToDelete = uploadedSourcePathForMedium + fileName;
        File mediumFile = new File(uploadedSourcePathWithFileForMediumToDelete);
        if (mediumFile.exists()) {
            if (mediumFile.delete()) {
                System.out.println(mediumFile.getName() + " is deleted!");

            } else {
                System.out.println("Delete operation is failed.");

            }
        }

        //For Small
        String uploadedSourcePathForSmall = propertiesConfig.getProperty("upload_news_small_image_path");
        String uploadedSourcePathWithFileForSmallToDelete = uploadedSourcePathForSmall + fileName;
        File SmallFile = new File(uploadedSourcePathWithFileForSmallToDelete);
        if (SmallFile.exists()) {
            if (SmallFile.delete()) {
                System.out.println(SmallFile.getName() + " is deleted!");

            } else {
                System.out.println("Delete operation is failed.");

            }
        }

    }

    @Override
    public FileInfo uploadLogoImageOfCompanyInfo_AsObject(UploadedFileContent uploadedFileContent) throws Exception {
        try {
            //Save the uploaded file to this folder
            String UPLOADED_FOLDER = propertiesConfig.getProperty("upload_company_logo_image_path");
            // Get the file and save it somewhere
            byte[] bytes = uploadedFileContent.getBytes();

            String filename = (uploadedFileContent.getNewFilename() == null || uploadedFileContent.getNewFilename().trim().equalsIgnoreCase("")) ? uploadedFileContent.getFilename() : uploadedFileContent.getNewFilename();
//            Path path = Paths.get(UPLOADED_FOLDER + uploadedFileContent.getFilename());
            Path path = Paths.get(UPLOADED_FOLDER + filename);
            Files.write(path, bytes);

            saveLogoImageOfCompanyInfoInOtherSizes(filename);
            FileInfo fileInfo = new FileInfo();
            fileInfo.setFileName(uploadedFileContent.getFilename());
            fileInfo.setFileSize(uploadedFileContent.getFileSize());
            return fileInfo;
        } catch (IOException e) {
            return null;
        }
    }

    public void saveLogoImageOfCompanyInfoInOtherSizes(String fileName) throws IOException {

        String uploadedSourcePath = propertiesConfig.getProperty("upload_company_logo_image_path");
        String uploadedSourcePathWithFileToRead = uploadedSourcePath + fileName;
        // For Large 640x480
        //Save the uploaded file to this folder
        String uploadedSourcePathForLarge = propertiesConfig.getProperty("upload_company_logo_large_image_path");
        String uploadedSourcePathWithFileForLargeToWrite = uploadedSourcePathForLarge + fileName;
        int aspectRatioMaintainedWidthForLarge = 480;
        int aspectRatioMaintainedHeightForLarge = 640;
        new ImageResizer().resizeByAscpectRatio(uploadedSourcePathWithFileToRead, uploadedSourcePathWithFileForLargeToWrite, aspectRatioMaintainedWidthForLarge, aspectRatioMaintainedHeightForLarge);

        // For Medium 320x240
        //Save the uploaded file to this folder
        String uploadedSourcePathForMedium = propertiesConfig.getProperty("upload_company_logo_medium_image_path");
        String uploadedSourcePathWithFileForMediumToWrite = uploadedSourcePathForMedium + fileName;
        int aspectRatioMaintainedWidthForMedium = 240;
        int aspectRatioMaintainedHeightForMedium = 320;
        new ImageResizer().resizeByAscpectRatio(uploadedSourcePathWithFileToRead, uploadedSourcePathWithFileForMediumToWrite, aspectRatioMaintainedWidthForMedium, aspectRatioMaintainedHeightForMedium);

        // For Small 128x96
        //Save the uploaded file to this folder
        String uploadedSourcePathForSmall = propertiesConfig.getProperty("upload_company_logo_small_image_path");
        String uploadedSourcePathWithFileForSmallToWrite = uploadedSourcePathForSmall + fileName;
        int aspectRatioMaintainedWidthForSmall = 96;
        int aspectRatioMaintainedHeightForSmall = 128;
        new ImageResizer().resizeByAscpectRatio(uploadedSourcePathWithFileToRead, uploadedSourcePathWithFileForSmallToWrite, aspectRatioMaintainedWidthForSmall, aspectRatioMaintainedHeightForSmall);

    }

    @Override
    public boolean deleteLogoImageOfCompanyInfo(String filename) throws Exception {
        try {
            String UPLOADED_FOLDER = propertiesConfig.getProperty("upload_company_logo_image_path");
            String path = UPLOADED_FOLDER + filename;
            File file = new File(path);
            if (file.exists()) {
                if (file.delete()) {
                    System.out.println(file.getName() + " is deleted!");
                    deleteLogoImageOfCompanyInfoInOtherSizes(filename);
                    return true;
                } else {
                    System.out.println("Delete operation is failed.");
                    return false;
                }
            } else {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void deleteLogoImageOfCompanyInfoInOtherSizes(String fileName) throws IOException {

        //For Large
        String uploadedSourcePathForLarge = propertiesConfig.getProperty("upload_company_logo_large_image_path");
        String path = uploadedSourcePathForLarge + fileName;
        File largeFile = new File(path);
        if (largeFile.exists()) {
            if (largeFile.delete()) {
                System.out.println(largeFile.getName() + " is deleted!");
            } else {
                System.out.println("Delete operation is failed.");
            }
        }

        //For Medium
        String uploadedSourcePathForMedium = propertiesConfig.getProperty("upload_company_logo_medium_image_path");
        String uploadedSourcePathWithFileForMediumToDelete = uploadedSourcePathForMedium + fileName;
        File mediumFile = new File(uploadedSourcePathWithFileForMediumToDelete);
        if (mediumFile.exists()) {
            if (mediumFile.delete()) {
                System.out.println(mediumFile.getName() + " is deleted!");

            } else {
                System.out.println("Delete operation is failed.");

            }
        }

        //For Small
        String uploadedSourcePathForSmall = propertiesConfig.getProperty("upload_company_logo_small_image_path");
        String uploadedSourcePathWithFileForSmallToDelete = uploadedSourcePathForSmall + fileName;
        File SmallFile = new File(uploadedSourcePathWithFileForSmallToDelete);
        if (SmallFile.exists()) {
            if (SmallFile.delete()) {
                System.out.println(SmallFile.getName() + " is deleted!");

            } else {
                System.out.println("Delete operation is failed.");

            }
        }

    }

    @Override
    public FileInfo uploadProfileImageOfCompanyInfo_AsObject(UploadedFileContent uploadedFileContent) throws Exception {
        try {
            //Save the uploaded file to this folder
            String UPLOADED_FOLDER = propertiesConfig.getProperty("upload_company_profile_image_path");
            // Get the file and save it somewhere
            byte[] bytes = uploadedFileContent.getBytes();

            String filename = (uploadedFileContent.getNewFilename() == null || uploadedFileContent.getNewFilename().trim().equalsIgnoreCase("")) ? uploadedFileContent.getFilename() : uploadedFileContent.getNewFilename();
//            Path path = Paths.get(UPLOADED_FOLDER + uploadedFileContent.getFilename());
            Path path = Paths.get(UPLOADED_FOLDER + filename);
            Files.write(path, bytes);

            saveProfileImageOfCompanyInfoInOtherSizes(filename);
            FileInfo fileInfo = new FileInfo();
            fileInfo.setFileName(uploadedFileContent.getFilename());
            fileInfo.setFileSize(uploadedFileContent.getFileSize());
            return fileInfo;
        } catch (IOException e) {
            return null;
        }
    }

    public void saveProfileImageOfCompanyInfoInOtherSizes(String fileName) throws IOException {

        String uploadedSourcePath = propertiesConfig.getProperty("upload_company_profile_image_path");
        String uploadedSourcePathWithFileToRead = uploadedSourcePath + fileName;
        // For Large 640x480
        //Save the uploaded file to this folder
        String uploadedSourcePathForLarge = propertiesConfig.getProperty("upload_company_profile_large_image_path");
        String uploadedSourcePathWithFileForLargeToWrite = uploadedSourcePathForLarge + fileName;
        int aspectRatioMaintainedWidthForLarge = 480;
        int aspectRatioMaintainedHeightForLarge = 640;
        new ImageResizer().resizeByAscpectRatio(uploadedSourcePathWithFileToRead, uploadedSourcePathWithFileForLargeToWrite, aspectRatioMaintainedWidthForLarge, aspectRatioMaintainedHeightForLarge);

        // For Medium 320x240
        //Save the uploaded file to this folder
        String uploadedSourcePathForMedium = propertiesConfig.getProperty("upload_company_profile_medium_image_path");
        String uploadedSourcePathWithFileForMediumToWrite = uploadedSourcePathForMedium + fileName;
        int aspectRatioMaintainedWidthForMedium = 240;
        int aspectRatioMaintainedHeightForMedium = 320;
        new ImageResizer().resizeByAscpectRatio(uploadedSourcePathWithFileToRead, uploadedSourcePathWithFileForMediumToWrite, aspectRatioMaintainedWidthForMedium, aspectRatioMaintainedHeightForMedium);

        // For Small 128x96
        //Save the uploaded file to this folder
        String uploadedSourcePathForSmall = propertiesConfig.getProperty("upload_company_profile_small_image_path");
        String uploadedSourcePathWithFileForSmallToWrite = uploadedSourcePathForSmall + fileName;
        int aspectRatioMaintainedWidthForSmall = 96;
        int aspectRatioMaintainedHeightForSmall = 128;
        new ImageResizer().resizeByAscpectRatio(uploadedSourcePathWithFileToRead, uploadedSourcePathWithFileForSmallToWrite, aspectRatioMaintainedWidthForSmall, aspectRatioMaintainedHeightForSmall);

    }

    @Override
    public boolean deleteProfileImageOfCompanyInfo(String filename) throws Exception {
        try {
            String UPLOADED_FOLDER = propertiesConfig.getProperty("upload_company_profile_image_path");
            String path = UPLOADED_FOLDER + filename;
            File file = new File(path);
            if (file.exists()) {
                if (file.delete()) {
                    System.out.println(file.getName() + " is deleted!");
                    deleteProfileImageOfCompanyInfoInOtherSizes(filename);
                    return true;
                } else {
                    System.out.println("Delete operation is failed.");
                    return false;
                }
            } else {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void deleteProfileImageOfCompanyInfoInOtherSizes(String fileName) throws IOException {

        //For Large
        String uploadedSourcePathForLarge = propertiesConfig.getProperty("upload_company_profile_large_image_path");
        String path = uploadedSourcePathForLarge + fileName;
        File largeFile = new File(path);
        if (largeFile.exists()) {
            if (largeFile.delete()) {
                System.out.println(largeFile.getName() + " is deleted!");
            } else {
                System.out.println("Delete operation is failed.");
            }
        }

        //For Medium
        String uploadedSourcePathForMedium = propertiesConfig.getProperty("upload_company_profile_medium_image_path");
        String uploadedSourcePathWithFileForMediumToDelete = uploadedSourcePathForMedium + fileName;
        File mediumFile = new File(uploadedSourcePathWithFileForMediumToDelete);
        if (mediumFile.exists()) {
            if (mediumFile.delete()) {
                System.out.println(mediumFile.getName() + " is deleted!");

            } else {
                System.out.println("Delete operation is failed.");

            }
        }

        //For Small
        String uploadedSourcePathForSmall = propertiesConfig.getProperty("upload_company_profile_small_image_path");
        String uploadedSourcePathWithFileForSmallToDelete = uploadedSourcePathForSmall + fileName;
        File SmallFile = new File(uploadedSourcePathWithFileForSmallToDelete);
        if (SmallFile.exists()) {
            if (SmallFile.delete()) {
                System.out.println(SmallFile.getName() + " is deleted!");

            } else {
                System.out.println("Delete operation is failed.");

            }
        }

    }

    @Override
    public FileInfo uploadCSVFileToAddNodesOfUser_AsObject(UploadedFileContent uploadedFileContent) throws Exception {
        try {
            //Save the uploaded file to this folder
            String UPLOADED_FOLDER = propertiesConfig.getProperty("upload_csv_toadd_node_path");
            // Get the file and save it somewhere
            byte[] bytes = uploadedFileContent.getBytes();

            String filename = (uploadedFileContent.getNewFilename() == null || uploadedFileContent.getNewFilename().trim().equalsIgnoreCase("")) ? uploadedFileContent.getFilename() : uploadedFileContent.getNewFilename();
//            Path path = Paths.get(UPLOADED_FOLDER + uploadedFileContent.getFilename());
            Path path = Paths.get(UPLOADED_FOLDER + filename);
            Files.write(path, bytes);
            FileInfo fileInfo = new FileInfo();
            fileInfo.setFileName(uploadedFileContent.getFilename());
            fileInfo.setFileSize(uploadedFileContent.getFileSize());
            return fileInfo;
        } catch (IOException e) {
            return null;
        }
    }
}

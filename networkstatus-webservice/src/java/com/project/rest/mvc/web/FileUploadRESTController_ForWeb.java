/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.rest.mvc.web;

/**
 *
 * @author samri
 */
import com.project.core.models.entities.common.ExceptionInfo;
import com.project.core.models.entities.test.FileInfo;
import com.project.core.models.entities.user.User;
import com.project.core.models.entities.util.UploadedFileContent;
import com.project.core.security.PropertiesConfig;
import com.project.core.services.user.UserService;
import com.project.core.services.util.FileHandlerService;
import com.project.core.util.Log4jUtil;
import com.project.rest.exceptions.BadRequestException;
import com.project.rest.exceptions.ConflictException;
import com.project.rest.exceptions.InternalServertException;
import com.project.rest.exceptions.NotFoundException;
import com.project.rest.mvc.common.BaseController;
import com.project.rest.util.GlobalExceptionMessage;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/rest/web/filehandler")
public class FileUploadRESTController_ForWeb extends BaseController {
    
    @Autowired
    ServletContext context;
    
    @Autowired
    FileHandlerService fileHandlerService;
    
    @Autowired
    @Resource(name = "propertiesConfig")
    private PropertiesConfig propertiesConfig;
    
    @Autowired
    private UserService userService;
    
    @RequestMapping(value = "/upload/file", headers = ("content-type=multipart/*"), method = RequestMethod.POST)
    public ResponseEntity<?> uploadFileByFormVIA_Rest(@RequestParam("file") MultipartFile inputFile) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            HttpHeaders headers = new HttpHeaders();
            try {
                if (!inputFile.isEmpty()) {
                    FileInfo fileInfo = fileHandlerService.uploadFile_AsMultpartFile(inputFile);
                    headers.add("File Uploaded Successfully - ", inputFile.getOriginalFilename());
                    return new ResponseEntity<>(fileInfo, headers, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            } catch (NotFoundException exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.NOT_FOUND.value(), GlobalExceptionMessage.NOTFOUND_APPLICATION_CODE, GlobalExceptionMessage.NOTFOUND_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.NOT_FOUND);
//                throw new NotFoundException(exception);
            } catch (BadRequestException exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.BAD_REQUEST.value(), GlobalExceptionMessage.BADREQUEST_APPLICATION_CODE, GlobalExceptionMessage.BADREQUST_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.BAD_REQUEST);
//                throw new BadRequestException(exception);
            } catch (ConflictException exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.CONFLICT.value(), GlobalExceptionMessage.CONFLICT_APPLICATION_CODE, GlobalExceptionMessage.CONFLICT_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.CONFLICT);
//                throw new ConflictException(exception);
            } catch (Exception exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.INTERNAL_SERVER_ERROR.value(), GlobalExceptionMessage.INTERNALSERVERERROR_APPLICATION_CODE, GlobalExceptionMessage.INTERNALSERVERERROR_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.INTERNAL_SERVER_ERROR);
//                throw new InternalServertException(e);
            }
        } else {
            ExceptionInfo info = new ExceptionInfo(HttpStatus.FORBIDDEN.value(), GlobalExceptionMessage.FORBIDDEN_APPLICATION_CODE, GlobalExceptionMessage.FORBIDDEN_MESSAGE, false, "N/A");
            writeLogMessage(info.getMessage());
            return new ResponseEntity<>(info, HttpStatus.FORBIDDEN);
//            throw new ForbiddenException();
        }
    }

//    @RequestMapping(value = "/upload/file", headers = ("content-type=multipart/*"), method = RequestMethod.POST)
//    public ResponseEntity<FileInfo> uploadFileByFormVIA_Rest(@RequestParam("file") MultipartFile inputFile) {
//
//        FileInfo fileInfo = new FileInfo();
//        HttpHeaders headers = new HttpHeaders();
//        if (!inputFile.isEmpty()) {
//            try {
//                String originalFilename = inputFile.getOriginalFilename();
//                File destinationFile = new File(context.getRealPath("/WEB-INF/uploaded") + File.separator + originalFilename);
//                inputFile.transferTo(destinationFile);
//                fileInfo.setFileName(destinationFile.getPath());
//                fileInfo.setFileSize(inputFile.getSize());
//                headers.add("File Uploaded Successfully - ", originalFilename);
//            }
//        } else {
//                return new ResponseEntity<>(fileInfo, headers, HttpStatus.OK);
//            } catch (IOException | IllegalStateException e) {
//                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }
    @RequestMapping(value = "/upload/filebyobject",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> uploadFileByRequestBodyObject(@RequestBody(required = false) UploadedFileContent uploadedFileContent) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {
                FileInfo fileInfo = fileHandlerService.uploadProfileImageOfUser_AsObject(uploadedFileContent);
                return new ResponseEntity<>(fileInfo, HttpStatus.OK);
            } catch (NotFoundException exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.NOT_FOUND.value(), GlobalExceptionMessage.NOTFOUND_APPLICATION_CODE, GlobalExceptionMessage.NOTFOUND_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.NOT_FOUND);
//                throw new NotFoundException(exception);
            } catch (BadRequestException exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.BAD_REQUEST.value(), GlobalExceptionMessage.BADREQUEST_APPLICATION_CODE, GlobalExceptionMessage.BADREQUST_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.BAD_REQUEST);
//                throw new BadRequestException(exception);
            } catch (ConflictException exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.CONFLICT.value(), GlobalExceptionMessage.CONFLICT_APPLICATION_CODE, GlobalExceptionMessage.CONFLICT_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.CONFLICT);
//                throw new ConflictException(exception);
            } catch (Exception exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.INTERNAL_SERVER_ERROR.value(), GlobalExceptionMessage.INTERNALSERVERERROR_APPLICATION_CODE, GlobalExceptionMessage.INTERNALSERVERERROR_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.INTERNAL_SERVER_ERROR);
//                throw new InternalServertException(e);
            }
        } else {
            ExceptionInfo info = new ExceptionInfo(HttpStatus.FORBIDDEN.value(), GlobalExceptionMessage.FORBIDDEN_APPLICATION_CODE, GlobalExceptionMessage.FORBIDDEN_MESSAGE, false, "N/A");
            writeLogMessage(info.getMessage());
            return new ResponseEntity<>(info, HttpStatus.FORBIDDEN);
//            throw new ForbiddenException();
        }
    }
    
    @RequestMapping(value = "/upload/image/user/profile",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> uploadUserProfileImageByRequestBodyObject(@RequestBody(required = false) UploadedFileContent uploadedFileContent) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {
                User user = userService.findUser(uploadedFileContent.getSourceId());
                fileHandlerService.deleteProfilePictureOfUser(user.getProfileImage());
                FileInfo fileInfo = fileHandlerService.uploadProfileImageOfUser_AsObject(uploadedFileContent);
                String filename = (uploadedFileContent.getNewFilename() == null || uploadedFileContent.getNewFilename().trim().equalsIgnoreCase("")) ? uploadedFileContent.getFilename() : uploadedFileContent.getNewFilename();
                user.setProfileImage(filename);
                userService.updateUserProfileImage(user.getId(), user);
                return new ResponseEntity<>(fileInfo, HttpStatus.OK);
            } catch (NotFoundException exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.NOT_FOUND.value(), GlobalExceptionMessage.NOTFOUND_APPLICATION_CODE, GlobalExceptionMessage.NOTFOUND_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.NOT_FOUND);
//                throw new NotFoundException(exception);
            } catch (BadRequestException exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.BAD_REQUEST.value(), GlobalExceptionMessage.BADREQUEST_APPLICATION_CODE, GlobalExceptionMessage.BADREQUST_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.BAD_REQUEST);
//                throw new BadRequestException(exception);
            } catch (ConflictException exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.CONFLICT.value(), GlobalExceptionMessage.CONFLICT_APPLICATION_CODE, GlobalExceptionMessage.CONFLICT_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.CONFLICT);
//                throw new ConflictException(exception);
            } catch (Exception exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.INTERNAL_SERVER_ERROR.value(), GlobalExceptionMessage.INTERNALSERVERERROR_APPLICATION_CODE, GlobalExceptionMessage.INTERNALSERVERERROR_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.INTERNAL_SERVER_ERROR);
//                throw new InternalServertException(e);
            }
        } else {
            ExceptionInfo info = new ExceptionInfo(HttpStatus.FORBIDDEN.value(), GlobalExceptionMessage.FORBIDDEN_APPLICATION_CODE, GlobalExceptionMessage.FORBIDDEN_MESSAGE, false, "N/A");
            writeLogMessage(info.getMessage());
            return new ResponseEntity<>(info, HttpStatus.FORBIDDEN);
//            throw new ForbiddenException();
        }
    }
    
    @RequestMapping(value = "/download/image/profile/{fileName}", method = RequestMethod.GET)
    public void showProfileImage(HttpServletResponse response, @PathVariable String fileName) throws Exception {
        
        try {
            ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
            User user = userService.findUserByUsername(fileName);
            try {
                String UPLOADED_FOLDER = propertiesConfig.getProperty("upload_profile_image_path");
//            String UPLOADED_FOLDER = "D://tempfile//server//";
//                File file = new File(UPLOADED_FOLDER + fileName + ".jpg");
                File file;
                if (user != null) {
                    file = new File(UPLOADED_FOLDER + user.getProfileImage());
                    
                    if (!file.exists() || !file.isFile()) {
                        file = new File(UPLOADED_FOLDER + "blank_profile.jpg");
                    }
                } else {
                    file = new File(UPLOADED_FOLDER + "blank_profile.jpg");
                }
                BufferedImage image = ImageIO.read(file); //CALL_OR_CREATE_YOUR_IMAGE_OBJECT;
                ImageIO.write(image, "jpeg", jpegOutputStream);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
            
            byte[] imgByte = jpegOutputStream.toByteArray();
            
            response.setHeader("Cache-Control", "no-store");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType("image/jpeg");
            ServletOutputStream responseOutputStream = response.getOutputStream();
            responseOutputStream.write(imgByte);
            responseOutputStream.flush();
            responseOutputStream.close();
        } catch (NotFoundException exception) {
            writeLogMessage(exception);
            Log4jUtil.error(exception.toString());
            Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
            throw new NotFoundException(exception);
        } catch (BadRequestException exception) {
            writeLogMessage(exception);
            Log4jUtil.error(exception.toString());
            Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
            throw new BadRequestException(exception);
        } catch (ConflictException exception) {
            writeLogMessage(exception);
            Log4jUtil.error(exception.toString());
            Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
            throw new ConflictException(exception);
        } catch (Exception e) {
            writeLogMessage(e);
            Log4jUtil.error(e.toString());
            Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(e));
            throw new InternalServertException(e);
        }
        
    }
    
    @RequestMapping(value = "/download/apk", method = RequestMethod.GET)
    public void showLocationImage(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        try {
            String filePath = propertiesConfig.getProperty("download_apk_path");
            int BUFFER_SIZE = 4096;

            // get absolute path of the application
            ServletContext context = request.getServletContext();
            String appPath = context.getRealPath("");
            System.out.println("appPath = " + appPath);

            // construct the complete absolute path of the file
            String fullPath = filePath;
            File downloadFile = new File(fullPath);
            FileInputStream inputStream = new FileInputStream(downloadFile);

            // get MIME type of the file
            String mimeType = context.getMimeType(fullPath);
            if (mimeType == null) {
                // set to binary type if MIME mapping not found
                mimeType = "application/octet-stream";
            }
            System.out.println("MIME type: " + mimeType);

            // set content attributes for the response
            response.setContentType(mimeType);
            response.setContentLength((int) downloadFile.length());

            // set headers for the response
            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"",
                    downloadFile.getName());
            response.setHeader(headerKey, headerValue);

            // get output stream of the response
            OutputStream outStream = response.getOutputStream();
            
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead = -1;

            // write bytes read from the input stream into the output stream
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
            
            inputStream.close();
            outStream.close();
            
        } catch (NotFoundException exception) {
            writeLogMessage(exception);
            Log4jUtil.error(exception.toString());
            Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
            throw new NotFoundException(exception);
        } catch (BadRequestException exception) {
            writeLogMessage(exception);
            Log4jUtil.error(exception.toString());
            Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
            throw new BadRequestException(exception);
        } catch (ConflictException exception) {
            writeLogMessage(exception);
            Log4jUtil.error(exception.toString());
            Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
            throw new ConflictException(exception);
        } catch (Exception e) {
            writeLogMessage(e);
            Log4jUtil.error(e.toString());
            Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(e));
            throw new InternalServertException(e);
        }
        
    }
    
    @RequestMapping(value = "/upload/csv/node/user",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> uploadCSVFileToImportNodesByRequestBodyObject(@RequestBody(required = false) UploadedFileContent uploadedFileContent) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {
                // Validation Finish
                UserDetails details = (UserDetails) principal;
                User loggedUser = userService.findUserByUsername(details.getUsername());

                uploadedFileContent.setNewFilename(loggedUser.getUsername() + ".csv");
                               
//                fileHandlerService.deleteProfilePictureOfUser(user.getProfileImage());
                FileInfo fileInfo = fileHandlerService.uploadCSVFileToAddNodesOfUser_AsObject(uploadedFileContent);
//                String filename = (uploadedFileContent.getNewFilename() == null || uploadedFileContent.getNewFilename().trim().equalsIgnoreCase("")) ? uploadedFileContent.getFilename() : uploadedFileContent.getNewFilename();
//                user.setProfileImage(filename);
//                userService.updateUserProfileImage(user.getId(), user);
                return new ResponseEntity<>(fileInfo, HttpStatus.OK);
            } catch (NotFoundException exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.NOT_FOUND.value(), GlobalExceptionMessage.NOTFOUND_APPLICATION_CODE, GlobalExceptionMessage.NOTFOUND_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.NOT_FOUND);
//                throw new NotFoundException(exception);
            } catch (BadRequestException exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.BAD_REQUEST.value(), GlobalExceptionMessage.BADREQUEST_APPLICATION_CODE, GlobalExceptionMessage.BADREQUST_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.BAD_REQUEST);
//                throw new BadRequestException(exception);
            } catch (ConflictException exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.CONFLICT.value(), GlobalExceptionMessage.CONFLICT_APPLICATION_CODE, GlobalExceptionMessage.CONFLICT_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.CONFLICT);
//                throw new ConflictException(exception);
            } catch (Exception exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.INTERNAL_SERVER_ERROR.value(), GlobalExceptionMessage.INTERNALSERVERERROR_APPLICATION_CODE, GlobalExceptionMessage.INTERNALSERVERERROR_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.INTERNAL_SERVER_ERROR);
//                throw new InternalServertException(e);
            }
        } else {
            ExceptionInfo info = new ExceptionInfo(HttpStatus.FORBIDDEN.value(), GlobalExceptionMessage.FORBIDDEN_APPLICATION_CODE, GlobalExceptionMessage.FORBIDDEN_MESSAGE, false, "N/A");
            writeLogMessage(info.getMessage());
            return new ResponseEntity<>(info, HttpStatus.FORBIDDEN);
//            throw new ForbiddenException();
        }
    }
    
}

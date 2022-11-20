package org.yash.yashtalks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import org.yash.yashtalks.entity.File;
import org.yash.yashtalks.exception.FileStorageException;
import org.yash.yashtalks.exception.MyFileNotFoundException;
import org.yash.yashtalks.property.FileStorageProperties;


public interface FileStorageService {

    public File store(MultipartFile file) throws IOException;

    public File getFile(String id);

    public Stream<File> getAllFiles();

}

package com.loggerproject.coreservice.service.util;

import com.loggerproject.coreservice.data.model.image.ImageModel;
import com.loggerproject.coreservice.data.model.image.extra.ImageSource;
import com.loggerproject.coreservice.data.model.image.extra.ImageSourceType;
import com.loggerproject.coreservice.service.image.create.ImageModelCreateService;
import com.loggerproject.coreservice.service.image.delete.ImageModelDeleteService;
import com.loggerproject.coreservice.service.image.update.ImageModelUpdateService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;

@Service
public class ImageUploadService {

    @Autowired
    ImageModelCreateService imageModelCreateService;

    @Autowired
    ImageModelUpdateService imageModelUpdateService;

    @Autowired
    ImageModelDeleteService imageDeleteService;

    @Autowired
    AmazonS3BucketService amazonS3BucketService;

    @Value("${image.upload.tmpdir}")
    private String tmpDir;

    private File tmpDirectory;

    @PostConstruct
    public void init() {
        tmpDirectory = new File(tmpDir);
    }

    private String getDirectoryPath() {
        DateTime now = DateTime.now(DateTimeZone.UTC);
        return "" + now.getYear() + "/" + now.getMonthOfYear() + "/" + now.getDayOfMonth() + "/";
    }

    /**
     * @param url - must be of type image
     * @return File with .extension
     * @throws IOException
     */
    private File generateImageFile(URL url) throws IOException {
        File file = File.createTempFile("image-upload-", "", tmpDirectory);
        FileUtils.copyURLToFile(url, file);

        String extension = getImageFileFormatName(file);

        // TODO make more efficient
        // .extension is needed for amazonS3 upload/putObject so resulting link would display image
        // otherwise link would cause download to pop-up
        File newFile = new File(file.getAbsolutePath() + "." + extension.toLowerCase());
        FileUtils.moveFile(file, newFile);

        return newFile;
    }

    private String getImageFileFormatName(File file) throws IOException {
        try (ImageInputStream iis = ImageIO.createImageInputStream(new FileInputStream(file))) {
            Iterator<ImageReader> imageReaders = ImageIO.getImageReaders(iis);
            ImageReader reader = imageReaders.next();
            return reader.getFormatName();
        }
    }

    public ImageModel uploadImage(File imageFile) throws Exception {
        String extension = FilenameUtils.getExtension(imageFile.getName());

        ImageModel model = new ImageModel();
        model = imageModelCreateService.create(model);

        String key = getDirectoryPath() + model.getId() + "." + extension;

        String imageURL;
        try {
            imageURL = amazonS3BucketService.uploadFile(key, imageFile);
        } catch (Exception e) {
            imageDeleteService.delete(model.getId());
            throw new Exception("Error uploading image to AmazonS3BucketService");
        }

        BufferedImage bufferedImage = ImageIO.read(imageFile);
        Integer width = bufferedImage.getWidth();
        Integer height = bufferedImage.getHeight();

        model.setExtension(extension);
        model.setSource(new ImageSource());
        model.setExtension(extension);
        model.setWidth(width);
        model.setHeight(height);
        model.setHeightDividedByWidth((double) height / (double) width);
        model.setImageURL(imageURL);
        model = imageModelUpdateService.update(model);

        return model;
    }

    public ImageModel uploadImage(URL url) throws Exception {
        File file = generateImageFile(url);
        ImageModel model = uploadImage(file);

        model.getSource().setType(ImageSourceType.URL);
        model.getSource().setUrl(url.toString());
        imageModelUpdateService.update(model);

        file.delete();
        return model;
    }

    public ImageModel uploadImage(String urlString) throws Exception {
        URL url = new URL(urlString);
        return uploadImage(url);
    }
}

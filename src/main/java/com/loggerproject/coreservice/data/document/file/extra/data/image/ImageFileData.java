package com.loggerproject.coreservice.data.document.file.extra.data.image;

import com.loggerproject.coreservice.data.document.file.extra.data.image.extra.ImageSource;
import lombok.Data;

@Data
public class ImageFileData {
    String imageURL;
    String extension;
    Integer width;
    Integer height;
    Double heightDividedByWidth;
    ImageSource source;
}

package com.loggerproject.coreservice.server.data.document.file.extra.data.logdirectory;

import com.loggerproject.coreservice.server.data.document.file.extra.data.image.extra.ImageSource;
import lombok.Data;

@Data
public class LogDirectoryFileData {
    String imageURL;
    String extension;
    Integer width;
    Integer height;
    Double heightDividedByWidth;
    ImageSource source;
}

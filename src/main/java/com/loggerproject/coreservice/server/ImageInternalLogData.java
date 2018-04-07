package com.loggerproject.coreservice.server;

import lombok.Data;

@Data
public class ImageInternalLogData extends ALogData {
    String imageURL;
    String imageID;
}

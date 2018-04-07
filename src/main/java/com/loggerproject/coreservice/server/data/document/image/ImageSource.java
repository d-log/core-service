package com.loggerproject.coreservice.server.data.document.image;

import lombok.Data;

@Data
public class ImageSource {
    ImageSourceType type;
    String url;
    String base64;
}

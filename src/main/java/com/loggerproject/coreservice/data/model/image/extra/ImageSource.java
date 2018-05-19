package com.loggerproject.coreservice.data.model.image.extra;

import lombok.Data;

@Data
public class ImageSource {
    ImageSourceType type;
    String url;
    String base64;
}

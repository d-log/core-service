package com.loggerproject.coreservice.data.document.file.extra.data.image.extra;

import lombok.Data;

@Data
public class ImageSource {
    ImageSourceType type;
    String url;
    String base64;
}

package com.loggerproject.coreservice.data.model.image;

import com.loggerproject.coreservice.data.model.image.extra.ImageSource;
import com.loggerproject.coreservice.data.model.shared.GlobalModel;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "image")
public class ImageModel extends GlobalModel {
    @Id
    String id;

    String imageURL;
    String extension;
    Integer width;
    Integer height;
    Double heightDividedByWidth;
    ImageSource source;
}

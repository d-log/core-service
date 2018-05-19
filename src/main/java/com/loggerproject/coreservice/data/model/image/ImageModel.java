package com.loggerproject.coreservice.data.model.image;

import com.loggerproject.coreservice.data.model._shared.IGlobalModel;
import com.loggerproject.coreservice.data.model._shared.Metadata;
import com.loggerproject.coreservice.data.model.image.extra.ImageSource;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "image")
public class ImageModel implements IGlobalModel {
    @Id
    String id;
    Metadata metadata;

    String imageURL;
    String extension;
    Integer width;
    Integer height;
    Double heightDividedByWidth;
    ImageSource source;
}

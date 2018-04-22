package com.loggerproject.coreservice.server.data.document.image;

import com.loggerproject.coreservice.global.server.document.model.GlobalModel;
import com.loggerproject.coreservice.server.data.document.image.extra.ImageSource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Document(collection = "image")
public class ImageModel extends GlobalModel implements Serializable {
    @Id
    String ID;
    String imageURL;
    String extension;
    Integer width;
    Integer height;
    Double heightDividedByWidth;
    ImageSource source;
}

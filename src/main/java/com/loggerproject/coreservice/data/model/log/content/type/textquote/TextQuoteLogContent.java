package com.loggerproject.coreservice.data.model.log.content.type.textquote;

import lombok.Data;

@Data
public class TextQuoteLogContent {
    // required
    String text;

    // optional, if not provided default to unknown
    String formOfCommunication; // such as: teacher to student
    String sourceType; // such as: book,  movie, song,
    String sourceName; // such as: Bible, Her,   Hey Jude,
}

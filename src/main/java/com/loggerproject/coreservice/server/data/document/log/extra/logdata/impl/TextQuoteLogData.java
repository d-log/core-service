package com.loggerproject.coreservice.server.data.document.log.extra.logdata.impl;

import lombok.Data;

@Data
public class TextQuoteLogData {
    // required
    String quote;

    // optional, if not provided default to unknown
    String formOfCommunication; // such as: teacher to student
    String sourceType; // such as: book,  movie, song,
    String sourceName; // such as: Bible, Her,   Hey Jude,
}

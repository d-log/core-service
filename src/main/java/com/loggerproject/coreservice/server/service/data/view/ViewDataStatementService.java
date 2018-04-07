package com.loggerproject.coreservice.server.service.data.view;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import com.loggerproject.coreservice.server.data.document.view.model.ValidateDataStatement;
import com.loggerproject.coreservice.server.service.data.image.get.ImageModelGetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ViewDataStatementService {

    @Autowired
    ImageModelGetService imageModelGetService;

    public void executeValidateDataStatementAgainstData(String data, ValidateDataStatement validateDataStatement) throws Exception {
        if (validateDataStatement == null) return;

        // split statement by white space
        String[] splitStr = validateDataStatement.getStatement().trim().split("\\s+");

        // replace all {{.*}} with values from String logdata
        for (int i = 0; i<splitStr.length; i++) {
            String str = splitStr[i];
            Integer l = str.length();
            if (l > 5) {
                if (str.substring(0, 2).equals("{{") && str.substring(l-2, l).equals("}}")) {
                    String path = str.substring(2,l-2);
                    try {
                        splitStr[i] = JsonPath.read(data, path);
                    } catch (PathNotFoundException e) {
                        throw new Exception("JSON path: '" + path + "' does not exist in json logdata: '" + data + "'");
                    }
                }
            }
        }

        // execute statement
        if (!executeStatement(splitStr)) {
            throw new Exception("Failed executeValidateDataStatementAgainstData - statement: '" + validateDataStatement.getStatement() + "' - logdata: '" + data + "'");
        }
    }

    /**
     *
     * @param splitStr String[] array of lexical within a single statement eg '! db contains image id bschjsbchsjcbsjc'
     * @return Boolean true for valid, false for invalid
     */
    private Boolean executeStatement(String[] splitStr) {
        Boolean valid = false;

        Integer i = 0;

        if (splitStr[0].equals("!")) {
            valid = true;
            i++;
        }

        try {
            // TODO make this more efficient as we expand this interpreter to handle more functionality
            if (splitStr[i].equals("db")) {
                i++;
                if (splitStr[i].equals("contains")) {
                    i++;
                    if (splitStr[i].equals("image")) {
                        i++;
                        if (splitStr[i].equals("id")) {
                            String id = splitStr[i+1];
                            valid = imageModelGetService.exists(id);
                        }
                    }
                }
            }
        } catch (Exception e) {
            // exception counts as invalid
            return false;
        }

        if (splitStr[0].equals("!")) {
            valid = !valid;
        }

        return valid;
    }
}

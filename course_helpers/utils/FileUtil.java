package demos.springdata.restdemo.utils;

import java.io.IOException;

public interface FileUtil {
    public String[] readFileContent(String filePath) throws IOException;
}

package com.ai.tools.synctools.helper;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@Slf4j
public class ShellCommandsHelper extends CommandHelper {
    private static final File file = new File(FILE_PATH);

    @Override
    public void exec() {
        try {
            FileUtils.forceMkdir(file);

            log.info("Starting clone repository...");

            String shellPath = this.getClass().getClassLoader().getResource(SHELL_NAME).getPath();
            Process process = Runtime.getRuntime().exec("sh " + shellPath, null, file);
            int status = process.waitFor();

            if (status != 0) {
                log.error("[ShellCommandsHelper] exec shell error {}", status);
            } else {
                log.info("Clone repository success");
            }
        } catch (Exception e) {
            log.error("[ShellCommandsHelper] exec shell error", e);
        }
    }

    public static void main(String[] args) {
        new ShellCommandsHelper().exec();
    }

}

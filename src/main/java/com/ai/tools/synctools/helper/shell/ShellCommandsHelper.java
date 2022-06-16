package com.ai.tools.synctools.helper.shell;

import com.ai.tools.synctools.helper.CommandHelper;
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

            Process process = Runtime.getRuntime().exec("sh " + SHELL_PATH, null, file);
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

}

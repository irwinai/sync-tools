package com.ai.tools.synctools.helper;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ShellCommandsHelper extends CommandHelper {
    @Override
    public void exec() {
        try {
            File baseFile = new File(basePath);
            FileUtils.forceMkdir(baseFile);

            File repoFile = new File(repoPath);
            Process process;
            if (repoFile.exists()) {
                log.info("Start pull...");
                process = Runtime.getRuntime().exec("git pull", null, repoFile);
            } else {
                log.info("Start clone...");
                process = Runtime.getRuntime().exec("git clone " + repoUrl, null, baseFile);
            }

            read(process);

            int status = process.waitFor();

            if (status != 0) {
                log.error("Execute shell error {}", status);
            } else {
                log.info("Execute shell complete");
            }
        } catch (Exception e) {
            log.error("Execute shell error", e);
        }
    }

    private void read(Process process) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String content = bufferedReader.lines().collect(Collectors.joining(System.lineSeparator()));
        if (StringUtils.isNotBlank(content)) {
            log.info("Execute shell : {}", content);
        }
    }


}

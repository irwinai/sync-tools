package com.ai.tools.synctools.controller;

import com.ai.tools.synctools.helper.CommandHelper;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class WebhookController {
    @Resource
    private CommandHelper shellCommandsHelper;

    @PostMapping("/webhook")
    public void webhook(@RequestBody JsonNode jsonNode) throws Exception {
        shellCommandsHelper.exec();
    }


}

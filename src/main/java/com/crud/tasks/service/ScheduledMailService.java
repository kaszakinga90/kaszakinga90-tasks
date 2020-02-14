package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class ScheduledMailService {

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    public String scheduledMail(String message) {

        Context schedulerContext = new Context();
        schedulerContext.setVariable("message", message);
        schedulerContext.setVariable("tasks_url", "http://localhost:8888/tasks_frontend/");
        schedulerContext.setVariable("button", "Visit website");
        schedulerContext.setVariable("admin_name", adminConfig.getAdminName());
        schedulerContext.setVariable("preview_message", "This message is auto-repeated by scheduler!");
        schedulerContext.setVariable("goodbye_message", "See you soon!");
        schedulerContext.setVariable("company_name", adminConfig.getCompanyName());
        schedulerContext.setVariable("company_goal", adminConfig.getCompanyGoal());
        schedulerContext.setVariable("company_email", adminConfig.getCompanyEmail());
        schedulerContext.setVariable("company_phone", adminConfig.getCompanyPhone());
        schedulerContext.setVariable("show_button", false);
        schedulerContext.setVariable("is_friend", false);
        schedulerContext.setVariable("admin_config", adminConfig);
        return templateEngine.process("mail/scheduled-mail", schedulerContext);
    }
}

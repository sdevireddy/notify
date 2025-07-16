package com.zen.notify.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zen.notify.dto.EmailTemplateDTO;
import com.zen.notify.entity.EmailTemplate;
import com.zen.notify.repository.EmailTemplateRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class EmailTemplateService {

    @Autowired
    private EmailTemplateRepository repository;

    public EmailTemplate createTemplate(EmailTemplateDTO dto, String createdBy) {
        EmailTemplate template = new EmailTemplate();
        template.setName(dto.getName());
        template.setSubject(dto.getSubject());
        template.setBody(dto.getBody());
        template.setCreatedBy(createdBy);
        template.setCreatedAt(LocalDateTime.now());
        template.setUpdatedAt(LocalDateTime.now());
        return repository.save(template);
    }

    public EmailTemplate updateTemplate(Long id, EmailTemplateDTO dto) {
        EmailTemplate template = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Template not found"));

        template.setName(dto.getName());
        template.setSubject(dto.getSubject());
        template.setBody(dto.getBody());
        template.setUpdatedAt(LocalDateTime.now());

        return repository.save(template);
    }

    public void deleteTemplate(Long id) {
        repository.deleteById(id);
    }

    public List<EmailTemplate> getTemplates(String createdBy) {
        return repository.findByCreatedBy(createdBy);
    }

    public EmailTemplate getTemplateById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Template not found"));
    }
}


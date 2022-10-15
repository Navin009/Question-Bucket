package com.nrgroup.bucket.email.service;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import software.amazon.awssdk.services.sesv2.SesV2Client;
import software.amazon.awssdk.services.sesv2.model.Body;
import software.amazon.awssdk.services.sesv2.model.Content;
import software.amazon.awssdk.services.sesv2.model.CreateEmailTemplateRequest;
import software.amazon.awssdk.services.sesv2.model.CreateEmailTemplateResponse;
import software.amazon.awssdk.services.sesv2.model.DeleteEmailTemplateRequest;
import software.amazon.awssdk.services.sesv2.model.DeleteEmailTemplateResponse;
import software.amazon.awssdk.services.sesv2.model.Destination;
import software.amazon.awssdk.services.sesv2.model.EmailContent;
import software.amazon.awssdk.services.sesv2.model.EmailTemplateContent;
import software.amazon.awssdk.services.sesv2.model.ListEmailTemplatesRequest;
import software.amazon.awssdk.services.sesv2.model.ListEmailTemplatesResponse;
import software.amazon.awssdk.services.sesv2.model.Message;
import software.amazon.awssdk.services.sesv2.model.SendEmailRequest;
import software.amazon.awssdk.services.sesv2.model.SendEmailResponse;
import software.amazon.awssdk.services.sesv2.model.SesV2Exception;
import software.amazon.awssdk.services.sesv2.model.Template;
import software.amazon.awssdk.services.sesv2.model.UpdateEmailTemplateRequest;
import software.amazon.awssdk.services.sesv2.model.UpdateEmailTemplateResponse;

@Service
public class SesService {

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	SesV2Client client;

	public SendEmailResponse sendSesHtmlEmail(String emailBodyData, String emailSubject, String emailFrom,
			String emailTo) throws SesV2Exception {

		Content bodyContent = Content.builder()
				.charset("UTF-8")
				.data(emailBodyData)
				.build();

		Content subjectContent = Content.builder()
				.charset("UTF-8")
				.data(emailSubject)
				.build();

		Body emailBody = Body.builder()
				.html(bodyContent)
				.build();

		Message message = Message.builder()
				.subject(subjectContent)
				.body(emailBody)
				.build();

		EmailContent emailContent = EmailContent.builder()
				.simple(message)
				.build();

		Destination destination = Destination.builder()
				.toAddresses(emailTo)
				.build();

		SendEmailRequest request = SendEmailRequest.builder()
				.fromEmailAddress(emailFrom)
				.content(emailContent)
				.destination(destination)
				.build();

		return client.sendEmail(request);
	}

	public SendEmailResponse sendSesEmailTemplate(String emailFrom, String templateName, String emailTo,
			Map<String, String> templateData) throws Exception {

		Template template = Template.builder()
				.templateName(templateName)
				.templateData(objectMapper.writeValueAsString(templateData))
				.build();
		EmailContent emailContent = EmailContent.builder()
				.template(template)
				.build();
		Destination destination = Destination.builder()
				.toAddresses(emailTo)
				.build();
		SendEmailRequest request = SendEmailRequest.builder()
				.fromEmailAddress(emailFrom)
				.content(emailContent)
				.destination(destination)
				.build();
		return client.sendEmail(request);
	}

	public CreateEmailTemplateResponse createSesTemplate(String templateName, String templateSubject,
			String templateHtml, String templateText) throws Exception {

		EmailTemplateContent emailTemplateContent = EmailTemplateContent.builder()
				.subject(templateSubject)
				.html(templateHtml)
				.text(templateText)
				.build();

		CreateEmailTemplateRequest request = CreateEmailTemplateRequest.builder()
				.templateName(templateName)
				.templateContent(emailTemplateContent)
				.build();

		return client.createEmailTemplate(request);
	}

	public UpdateEmailTemplateResponse updateSesTemplate(String templateName, String templateSubject,
			String templateHtml, String templateText) throws Exception {

		EmailTemplateContent emailTemplateContent = EmailTemplateContent.builder()
				.subject(templateSubject)
				.html(templateHtml)
				.text(templateText)
				.build();

		UpdateEmailTemplateRequest request = UpdateEmailTemplateRequest.builder()
				.templateName(templateName)
				.templateContent(emailTemplateContent)
				.build();

		return client.updateEmailTemplate(request);
	}

	public DeleteEmailTemplateResponse deleteSesTemplate(String templateName) throws Exception {

		DeleteEmailTemplateRequest request = DeleteEmailTemplateRequest.builder()
				.templateName(templateName)
				.build();

		return client.deleteEmailTemplate(request);
	}

	public ListEmailTemplatesResponse listSesTemplates() throws Exception {

		ListEmailTemplatesRequest request = ListEmailTemplatesRequest.builder().build();
		return client.listEmailTemplates(request);
	}
}

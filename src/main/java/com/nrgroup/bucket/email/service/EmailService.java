package com.nrgroup.bucket.email.service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.nrgroup.bucket.email.model.request.EmailTemplateRequest;
import com.nrgroup.bucket.email.model.response.EmailTemplateResponse;
import com.nrgroup.bucket.user.model.request.ForgotRequest;
import com.nrgroup.bucket.user.model.request.RegisterRequest;
import com.nrgroup.bucket.utils.CustomUtils;

import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.sesv2.model.CreateEmailTemplateResponse;
import software.amazon.awssdk.services.sesv2.model.DeleteEmailTemplateResponse;
import software.amazon.awssdk.services.sesv2.model.ListEmailTemplatesResponse;
import software.amazon.awssdk.services.sesv2.model.SendEmailResponse;
import software.amazon.awssdk.services.sesv2.model.SesV2Exception;
import software.amazon.awssdk.services.sesv2.model.UpdateEmailTemplateResponse;

@Slf4j
@Service
public class EmailService {

	@Value("${mail.email-from}")
	private String emailOTPfrom;

	@Value("${mail.template.register.otp}")
	private String registerEmailOTPTemplate;

	@Value("${mail.template.forgot.otp}")
	private String forgotEmailOTPTemplate;


	@Autowired
	private SesService sesService;

	public Boolean sendRegisterOTPEmail(RegisterRequest request, String otp) {
		log.info("Start Sending email to {} with otp {}", request.getEmail(), otp);
		try {
			Map<String, String> model = new HashMap<>();
			model.put("otp", otp);
			model.put("name", request.getFirstName() + " " + request.getLastName());
			SendEmailResponse response = sesService.sendSesEmailTemplate(emailOTPfrom, registerEmailOTPTemplate,
					request.getEmail(), model);
			log.info("Email sent successfully with response {}", response.sdkHttpResponse().statusCode());
			return true;
		} catch (SesV2Exception se) {
			log.error("SesV2Exception while register otp send for email - {} - {}", request.getEmail(),
					se.getMessage());
		} catch (Exception e) {
			log.error("Error Sending Register OTP Email - {} - {}", e.getClass(), e.getMessage());
		}
		return false;
	}

	public Boolean sendForgotOTPEmail(ForgotRequest request, String otp) {
		try {
			log.info("Start Sending email to {} with otp {}", request.getEmail(), otp);
			Map<String, String> model = new HashMap<>();
			model.put("otp", otp);
			model.put("email", request.getEmail());
			SendEmailResponse response = sesService.sendSesEmailTemplate(emailOTPfrom, forgotEmailOTPTemplate,
					request.getEmail(), model);
			log.info("Send Forgot OTP Email response statusCode - {}", response.sdkHttpResponse().statusCode());
			return true;
		} catch (Exception e) {
			log.error("Error when sending OTP Email - {} - {}", e.getClass(), e.getMessage());
		}
		return false;
	}

	public EmailTemplateResponse createTemplate(EmailTemplateRequest request) {
		EmailTemplateResponse response = new EmailTemplateResponse();
		log.info("Start Creating Email Template - {}", request.getTemplateName());
		try {
			String htmlContent = CustomUtils.multiPartFileToString(request.getTemplateHtmlPart(), "");
			String textContent = CustomUtils.multiPartFileToString(request.getTemplateTextPart(), "");
			CreateEmailTemplateResponse sesResponse = sesService.createSesTemplate(request.getTemplateName(),
					request.getTemplateSubject(),
					htmlContent,
					textContent);
			response.setResponseCode(sesResponse.sdkHttpResponse().statusCode());
			response.setResponseData(sesResponse.responseMetadata().toString());
		} catch (SesV2Exception e) {
			log.error("SesV2Exception creating template - {} - {}", request.getTemplateName(), e.getMessage());
			response.setResponseCode(e.statusCode());
			response.setErrorMessage(e.awsErrorDetails().errorMessage());
		} catch (Exception e) {
			log.error("Exception creating template - {} - {}", e.getClass(), e.getMessage());
			response.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setErrorMessage(e.getMessage());
		}
		return response;
	}

	public EmailTemplateResponse updateTemplate(EmailTemplateRequest request) {
		EmailTemplateResponse response = new EmailTemplateResponse();
		log.info("Start Updating Email Template - {}", request.getTemplateName());
		try {
			String htmlContent = CustomUtils.multiPartFileToString(request.getTemplateHtmlPart(), "");
			String textContent = CustomUtils.multiPartFileToString(request.getTemplateTextPart(), "");
			UpdateEmailTemplateResponse sesResponse = sesService.updateSesTemplate(request.getTemplateName(),
					request.getTemplateSubject(),
					htmlContent,
					textContent);
			response.setResponseCode(sesResponse.sdkHttpResponse().statusCode());
			response.setResponseData(sesResponse.sdkFields().toString());
		} catch (SesV2Exception e) {
			log.error("SesV2Exception updating template - {} - {}", request.getTemplateName(), e.getMessage());
			response.setResponseCode(e.statusCode());
			response.setErrorMessage(e.awsErrorDetails().errorMessage());
		} catch (Exception e) {
			log.error("Exception updating template - {} - {}", e.getClass(), e.getMessage());
			response.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setErrorMessage(e.getMessage());
		}
		return response;
	}

	public EmailTemplateResponse deleteTemplate(String deleteTemplateName) {
		EmailTemplateResponse response = new EmailTemplateResponse();
		log.info("Start Deleting Email Template - {}", deleteTemplateName);
		try {
			DeleteEmailTemplateResponse sesResponse = sesService.deleteSesTemplate(deleteTemplateName);
			response.setResponseCode(sesResponse.sdkHttpResponse().statusCode());
			response.setResponseData(sesResponse.sdkHttpResponse().statusText());
		} catch (SesV2Exception e) {
			log.error("SesV2Exception deleting template - {}", e.getMessage());
			response.setResponseCode(e.statusCode());
			response.setErrorMessage(e.awsErrorDetails().errorMessage());
		} catch (Exception e) {
			log.error("Exception deleting template - {} - {}", e.getClass(), e.getMessage());
			response.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setErrorMessage(e.getMessage());
		}
		return response;
	}

	public EmailTemplateResponse listTemplates() {
		EmailTemplateResponse response = new EmailTemplateResponse();
		log.info("Start fetching List of Email Templates");
		try {
			ListEmailTemplatesResponse sesResponse = sesService.listSesTemplates();
			response.setResponseCode(sesResponse.sdkHttpResponse().statusCode());
			response.setResponseData(sesResponse.templatesMetadata().stream()
					.map(t -> Map.ofEntries(
							Map.entry("templateName", t.templateName()),
							Map.entry("createdAt", t.createdTimestamp())))
					.collect(Collectors.toList()));
		} catch (SesV2Exception e) {
			log.error("SesV2Exception listing template - {}", e.getMessage());
			response.setResponseCode(e.statusCode());
			response.setErrorMessage(e.awsErrorDetails().errorMessage());
		} catch (Exception e) {
			log.error("Exception listing templates - {} - {}", e.getClass(), e.getMessage());
			response.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setErrorMessage(e.getMessage());
		}
		return response;
	}

}

package org.vbazurtob.HRRecruitApp.ui.secured;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;


import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.vbazurtob.HRRecruitApp.lib.common.DeleteResponse;
import org.vbazurtob.HRRecruitApp.lib.common.RecordNotFoundException;
import org.vbazurtob.HRRecruitApp.lib.common.Utils;
import org.vbazurtob.HRRecruitApp.model.ApplicantWithPassword;
import org.vbazurtob.HRRecruitApp.model.ApplicantWithoutPassword;
import org.vbazurtob.HRRecruitApp.model.ApplicantAcademic;
import org.vbazurtob.HRRecruitApp.model.ApplicantChangePasswordForm;
import org.vbazurtob.HRRecruitApp.model.ApplicantBaseClass;
import org.vbazurtob.HRRecruitApp.model.ApplicantSkill;
import org.vbazurtob.HRRecruitApp.model.ApplicantWorkExperience;
import org.vbazurtob.HRRecruitApp.model.Job;
import org.vbazurtob.HRRecruitApp.model.repository.ApplicantAcademicsRepository;
import org.vbazurtob.HRRecruitApp.model.repository.ApplicantRepository;
import org.vbazurtob.HRRecruitApp.model.repository.ApplicantSkillRepository;
import org.vbazurtob.HRRecruitApp.model.repository.ApplicantWorkExpRepository;
import org.vbazurtob.HRRecruitApp.model.repository.JobRepository;
import org.vbazurtob.HRRecruitApp.model.service.ApplicantAcademicsService;
import org.vbazurtob.HRRecruitApp.model.service.ApplicantService;
import org.vbazurtob.HRRecruitApp.model.service.ApplicantSkillService;
import org.vbazurtob.HRRecruitApp.model.service.ApplicantWorkExpService;
import org.vbazurtob.HRRecruitApp.model.service.CountryService;
import org.vbazurtob.HRRecruitApp.model.service.JobService;
import org.vbazurtob.HRRecruitApp.model.service.ProficiencyService;
import org.vbazurtob.HRRecruitApp.model.service.TypeDegreeService;

import ch.qos.logback.classic.pattern.Util;



@Controller
@RequestMapping("/jobs")
public class JobAdsManagementController {
	
	private final static String  DASHBOARD_JOB_MANAGEMENT_BASE_URL = "/management/";
	
	
	private final static int RECORDS_PER_PAGE = 10;
	
	@Autowired
	private JobRepository jobRepository;
	
	@Autowired
	private JobService jobService;
	
	
	@RequestMapping(DASHBOARD_JOB_MANAGEMENT_BASE_URL)
	public String showDetails(@PathVariable Optional<Integer> page, Model model) {
		
		//Get Controller Name
		String controllerMapping = this.getClass().getAnnotation(RequestMapping.class).value()[0];
		// Get logged username
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		//TODO
		username = "admin";
		
		// Pagination for listing
		Page<Job> jobPageObj = jobService.getPaginatedRecords(page, RECORDS_PER_PAGE);
		long previousPageNum = jobService.getPaginationNumbers(jobPageObj)[0];
		long nextPageNum = jobService.getPaginationNumbers(jobPageObj)[1];
	
		
		// View attributes
		model.addAttribute("baseUrl", controllerMapping + DASHBOARD_JOB_MANAGEMENT_BASE_URL);
		model.addAttribute("prevPage", previousPageNum);
		model.addAttribute("nextPage", nextPageNum);
		model.addAttribute("pageObj", jobPageObj );
		model.addAttribute("jobsList", jobPageObj.getContent());
		

		return "secured/job_management.html";
	}
	
	
	@ExceptionHandler(RecordNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handleResourceNotFoundException() {
	        return "error.html";
	}
	
	
	
}
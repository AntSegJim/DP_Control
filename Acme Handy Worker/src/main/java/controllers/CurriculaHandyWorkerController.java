
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.CurriculaService;
import services.EducationRecordService;
import services.EndoserRecordService;
import services.HandyWorkerService;
import services.MiscellaneousRecordService;
import services.PersonalRecordService;
import services.ProfessionalRecordService;
import domain.Curricula;
import domain.EducationRecord;
import domain.EndoserRecord;
import domain.HandyWorker;
import domain.MiscellaneousRecord;
import domain.PersonalRecord;
import domain.ProfessionalRecord;

@Controller
@RequestMapping("/curricula/handyWorker")
public class CurriculaHandyWorkerController extends AbstractController {

	@Autowired
	private CurriculaService			curriculaService;
	@Autowired
	private HandyWorkerService			hwService;
	@Autowired
	private EducationRecordService		ERService;
	@Autowired
	private ProfessionalRecordService	PRService;
	@Autowired
	private MiscellaneousRecordService	MRService;
	@Autowired
	private EndoserRecordService		EnRService;
	@Autowired
	private PersonalRecordService		PerRService;


	public CurriculaHandyWorkerController() {
		super();
	}

	@RequestMapping(value = "/curriculas", method = RequestMethod.GET)
	public ModelAndView curriculas() {
		ModelAndView result;
		final Collection<Curricula> curriculas;
		final UserAccount user = LoginService.getPrincipal();
		final HandyWorker h = this.hwService.handyWorkerUserAccount(user.getId());
		curriculas = this.curriculaService.findAllHandyWorkerCurricula(h.getId());

		result = new ModelAndView("curricula/curriculas");
		result.addObject("curriculas", curriculas);
		//result.addObject("requestURI", "complaint/customer/complaints.do");

		return result;
	}

	@RequestMapping(value = "/createCurricula", method = RequestMethod.GET)
	public ModelAndView createCurricula() {
		final ModelAndView result;
		final Curricula curricula;
		final Collection<EducationRecord> educationsRecords;
		final Collection<ProfessionalRecord> professionalsRecords;
		final Collection<MiscellaneousRecord> miscellaneousRecords;
		final Collection<EndoserRecord> endosersRecords;
		final Collection<PersonalRecord> personalRecord;

		educationsRecords = this.ERService.findAll();
		professionalsRecords = this.PRService.findAll();
		miscellaneousRecords = this.MRService.findAll();
		endosersRecords = this.EnRService.findAll();
		personalRecord = this.PerRService.findAll();

		curricula = this.curriculaService.create();

		result = new ModelAndView("curricula/createCurricula");
		result.addObject("curricula", curricula);
		result.addObject("educationsRecords", educationsRecords);
		result.addObject("professionalsRecords", professionalsRecords);
		result.addObject("miscellaneousRecords", miscellaneousRecords);
		result.addObject("endosersRecords", endosersRecords);
		result.addObject("personalRecord", personalRecord);

		return result;
	}

	@RequestMapping(value = "/createCurricula", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Curricula curricula, final BindingResult binding) {
		ModelAndView result;

		if (!binding.hasErrors()) {
			this.curriculaService.save(curricula);
			result = new ModelAndView("redirect:curriculas.do");
		} else {
			result = new ModelAndView("curricula/createCurricula");
			result.addObject("curricula", curricula);
		}
		return result;
	}

}

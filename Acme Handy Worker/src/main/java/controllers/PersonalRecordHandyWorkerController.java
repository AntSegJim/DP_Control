
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CurriculaService;
import domain.Curricula;
import domain.PersonalRecord;

@Controller
@RequestMapping("/personalRecord/handyWorker")
public class PersonalRecordHandyWorkerController extends AbstractController {

	@Autowired
	private CurriculaService	curriculaService;


	public PersonalRecordHandyWorkerController() {
		super();
	}

	@RequestMapping(value = "/showPersonalRecord", method = RequestMethod.GET)
	public ModelAndView showPersonalRecord(@RequestParam final int curriculaId) {
		ModelAndView result;
		final PersonalRecord personalRecord;
		Curricula curricula;
		curricula = this.curriculaService.findOne(curriculaId);
		personalRecord = curricula.getPersonalRecord();

		result = new ModelAndView("personalRecord/showPersonalRecord");
		result.addObject("personalRecord", personalRecord);
		//result.addObject("requestURI", "complaint/customer/complaints.do");

		return result;
	}
}

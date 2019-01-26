
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CurriculaService;
import domain.Curricula;
import domain.EducationRecord;

@Controller
@RequestMapping("/educationRecord/handyWorker")
public class EducationRecordHandyWorkerController extends AbstractController {

	@Autowired
	private CurriculaService	curriculaService;


	public EducationRecordHandyWorkerController() {
		super();
	}

	@RequestMapping(value = "/showEducationsRecords", method = RequestMethod.GET)
	public ModelAndView showEducationsRecords(@RequestParam final int curriculaId) {
		ModelAndView result;
		final Collection<EducationRecord> educationsRecords;
		Curricula curricula;
		curricula = this.curriculaService.findOne(curriculaId);
		educationsRecords = curricula.getEducationsRecords();

		result = new ModelAndView("educationRecord/showEducationsRecords");
		result.addObject("educationsRecords", educationsRecords);
		//result.addObject("requestURI", "complaint/customer/complaints.do");

		return result;
	}

}

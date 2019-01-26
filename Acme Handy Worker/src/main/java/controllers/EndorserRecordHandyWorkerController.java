
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
import domain.EndoserRecord;

@Controller
@RequestMapping("/endorserRecord/handyWorker")
public class EndorserRecordHandyWorkerController extends AbstractController {

	@Autowired
	private CurriculaService	curriculaService;


	public EndorserRecordHandyWorkerController() {
		super();
	}

	@RequestMapping(value = "/showEndorsersRecords", method = RequestMethod.GET)
	public ModelAndView showEndorsersRecords(@RequestParam final int curriculaId) {
		ModelAndView result;
		final Collection<EndoserRecord> endorsersRecords;
		Curricula curricula;
		curricula = this.curriculaService.findOne(curriculaId);
		endorsersRecords = curricula.getEndosersRecords();

		result = new ModelAndView("endorserRecord/showEndorsersRecords");
		result.addObject("endorsersRecords", endorsersRecords);
		//result.addObject("requestURI", "complaint/customer/complaints.do");

		return result;
	}
}

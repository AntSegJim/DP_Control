
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
import domain.MiscellaneousRecord;

@Controller
@RequestMapping("/miscellaneousRecord/handyWorker")
public class MiscellaneousRecordHandyWorkerController extends AbstractController {

	@Autowired
	private CurriculaService	curriculaService;


	public MiscellaneousRecordHandyWorkerController() {
		super();
	}

	@RequestMapping(value = "/showMiscellaneousRecords", method = RequestMethod.GET)
	public ModelAndView showMiscellaneousRecords(@RequestParam final int curriculaId) {
		ModelAndView result;
		final Collection<MiscellaneousRecord> miscellaneousRecords;
		Curricula curricula;
		curricula = this.curriculaService.findOne(curriculaId);
		miscellaneousRecords = curricula.getMiscellaneousRecords();

		result = new ModelAndView("miscellaneousRecord/showMiscellaneousRecords");
		result.addObject("miscellaneousRecords", miscellaneousRecords);
		//result.addObject("requestURI", "complaint/customer/complaints.do");

		return result;
	}

}


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
import domain.ProfessionalRecord;

@Controller
@RequestMapping("/professionalRecord/handyWorker")
public class ProfessionalRecordHandyWorkerController extends AbstractController {

	@Autowired
	private CurriculaService	curriculaService;


	public ProfessionalRecordHandyWorkerController() {
		super();
	}

	@RequestMapping(value = "/showProfessionalsRecords", method = RequestMethod.GET)
	public ModelAndView showProfessionalsRecords(@RequestParam final int curriculaId) {
		ModelAndView result;
		final Collection<ProfessionalRecord> professionalsRecords;
		Curricula curricula;
		curricula = this.curriculaService.findOne(curriculaId);
		professionalsRecords = curricula.getProfessionalsRecords();

		result = new ModelAndView("professionalRecord/showProfessionalsRecords");
		result.addObject("professionalsRecords", professionalsRecords);
		//result.addObject("requestURI", "complaint/customer/complaints.do");

		return result;
	}

}

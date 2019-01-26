/*
 * AnnouncementCustomerController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.referee;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.NoteService;
import services.ReportService;
import controllers.AbstractController;
import domain.Note;
import domain.Report;

@Controller
@RequestMapping("/note/referee")
public class NotetRefereeController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ReportService	reportService;

	@Autowired
	private NoteService		noteService;


	// Constructors -----------------------------------------------------------
	public NotetRefereeController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listIdComplaint(@RequestParam final int reportId) {
		final ModelAndView result;

		Collection<Note> notes;
		final Report report = this.reportService.findOne(reportId);
		notes = this.noteService.findAllNoteReportId(reportId);

		result = new ModelAndView("note/list");
		result.addObject("requestURI", "note/referee/list.do");
		result.addObject("notes", notes);
		result.addObject("report", report);

		return result;
	}
	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@Valid final int reportId) {
		ModelAndView result;
		Note note;

		note = this.noteService.create();
		note.setReport(this.reportService.findOne(reportId));
		result = this.createEditModelAndView(note);

		return result;
	}
	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int noteId) {
		ModelAndView result;
		Note note;

		note = this.noteService.findOne(noteId);
		Assert.notNull(note);
		result = this.createEditModelAndView(note);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Note note, final BindingResult binding) {
		ModelAndView result;

		if (!binding.hasErrors()) {
			this.noteService.save(note);
			final Integer idReport = note.getReport().getId();
			result = new ModelAndView("redirect:list.do?reportId=" + idReport);
		} else
			result = this.createEditModelAndView(note);

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Note note) {
		ModelAndView result;

		result = this.createEditModelAndView(note, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Note note, final String message) {
		ModelAndView result;
		final Report report;
		report = note.getReport();

		result = new ModelAndView("note/edit");
		result.addObject("note", note);
		result.addObject("report", report);
		result.addObject("message", message);

		return result;
	}

}

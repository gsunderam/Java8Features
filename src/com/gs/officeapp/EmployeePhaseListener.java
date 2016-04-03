package com.gs.officeapp;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

public class EmployeePhaseListener implements PhaseListener {

	private static final long serialVersionUID = 1001L;

	@Override
	public void afterPhase(PhaseEvent arg0) {
		System.out.println("Current Phase in before " + arg0.getFacesContext().getCurrentPhaseId().getName());
	}

	@Override
	public void beforePhase(PhaseEvent arg0) {
		System.out.println("Phase begin " + arg0.getPhaseId().getName());
		// TODO Auto-generated method stub
		
	}

	@Override
	public PhaseId getPhaseId() {
		// TODO Auto-generated method stub
		return PhaseId.ANY_PHASE;
	}

}

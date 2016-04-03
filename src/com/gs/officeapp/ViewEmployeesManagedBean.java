package com.gs.officeapp;

import java.util.ArrayList;
import java.util.List;
 

import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
 
@ManagedBean(name="emp")
@SessionScoped
public class ViewEmployeesManagedBean {
	private static AtomicInteger i = new AtomicInteger();
    private List<Employee> employees = new ArrayList<Employee>();
 
    public ViewEmployeesManagedBean(){
 
    }
    
    public String getName(String name) {
    	System.out.println("update dom");
    	return name + " Success";
    }
    
    public void testActionListener(ActionEvent e) {
    	System.out.println("Employee listener called");
    }
 
    @PostConstruct
    public void populateEmployeeList(){
        for(int i = 1 ; i <= 10 ; i++){
            Employee emp = new Employee();
            emp.setEmployeeId(String.valueOf(i));
            emp.setEmployeeName("Employee#"+i);
            this.employees.add(emp);
        }
    }
    
    public int increment() {
    	System.out.println("inc called");
    	return i.incrementAndGet();
    }
    
    public int decrement() {
    	System.out.println("decrement called");
    	return i.decrementAndGet();
    }
 
    public List<Employee> getEmployees() {
        return employees;
    }
 
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
    
    public void testRemoteCmd() {
    	System.out.println("Remote Command called");
    }
}

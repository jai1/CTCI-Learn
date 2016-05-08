package org.ctci.applications;

import java.util.concurrent.Future;
import java.util.concurrent.PriorityBlockingQueue;

public class Callable {
	
	Future<Void> future = null;
	PriorityBlockingQueue<Call> calls = new PriorityBlockingQueue<Call>(1000); 
	PriorityBlockingQueue<Employee> employees = new PriorityBlockingQueue<Employee>(1000); 
	
	public boolean addCall(Call c) {
		if(calls.contains(c))
			return false;
		return calls.add(c);
	}
	
	public boolean addEmployee(Employee e) {
		if(employees.contains(e))
			return false;
		return employees.add(e);
	}
	
	public boolean removeEmployee(Employee e) {
		return employees.remove(e);
	}
	
	
	
	public void run() {
		if(future == null && !future.isDone()) {
			
		}
	}
	
	public void stop() {
		if(future == null && future.isDone())
			return;
	}
}

package repo.bigdata.quartz;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

import org.quartz.JobBuilder;
import org.quartz.DateBuilder;
import org.quartz.TriggerBuilder;

public class SimpleScheduler {
	
	public static void main(String [] args) throws SchedulerException, InterruptedException {
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler sched = sf.getScheduler();
		JobDetail job = JobBuilder.newJob(SimpleJobExample.class)
			    .withIdentity("job1", "group1")
			    .build();
		Date runTime = DateBuilder.evenMinuteDate(new Date());
		
		Trigger trigger = TriggerBuilder.newTrigger()
		    .withIdentity("trigger1", "group1")
		    .startAt(runTime)
		    .build();
		// Tell quartz to schedule the job using our trigger
		sched.scheduleJob(job, trigger);
		sched.start();
		Thread.sleep(90L * 1000L);
		sched.shutdown();
		
	}
}

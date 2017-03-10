package repo.bigdata.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class SimpleJobExample implements Job {

	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("SimpleJob Executed @"+System.currentTimeMillis());
	}

}

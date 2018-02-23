/*cron表达式简要介绍：

一个cron表达式有至少6个（也可能7个）有空格分隔的时间元素。依次顺序为：

秒（0~59）
分钟（0~59）
小时（0~23）
天（月）（0~31，需要考虑月的天数）
月（0~11）
天（星期）（1~7 1=SUN 或 SUN，MON，TUE，WED，THU，FRI，SAT）
年份（1970－2099）
常用符号代表意思：

* 字符代表所有可能的值。因此，* 在子表达式（月）里表示每个月的含义，* 在子表达式（天（星期））表示星期的每一天
？字符仅被用于天（月）和天（星期）两个子表达式，表示不指定值当2个子表达式其中之一被指定了值以后，为了避免冲突，需要将另一个子表达式的值设为“？”
/ 字符表示起始时间开始触发，然后每隔固定时间触发一次，例如在Minutes域使用5/20,则意味着5分钟触发一次
几个示例：

每天的凌晨4点 @Scheduled(cron = "0 0 4 * * *")
每周日的凌晨5点  @Scheduled(cron = "0 0 5 * * SUN")
每月第一天凌晨2点10分 @Scheduled(cron="0 10 02 01 * ?")
每天从下午2点开始到2点55分结束每隔5分钟触发一次 @Schedule(cron = "0 0/5 14 * * ?")*/
package com.demo;

import org.apache.log4j.Logger;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
public class UserTaskJob {
	
	private static final Logger logger = Logger.getLogger(UserTaskJob.class);
        /**
     * 每月第一天凌晨一点执行 0 0 01 01 * ?
     */
    @Scheduled(cron="0/30 * * * * ?")
    public void tempUserTaskDelete() {
        //do something
    	logger.info("定时任务被调用了{0/30 * * * * ?}");
    }
}

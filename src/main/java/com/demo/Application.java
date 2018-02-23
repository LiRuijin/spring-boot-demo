package com.demo;

import org.apache.log4j.Logger;
import org.springframework.boot.Banner.Mode;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;

import com.demo.config.WebConfig;

@SpringBootApplication
@ServletComponentScan
public class Application implements CommandLineRunner {

	private static final Logger logger = Logger.getLogger(Application.class);

	public static void main(String[] args) {

		// SpringApplication.run(Application.class, args);

		SpringApplication app = new SpringApplication(Application.class);
		app.setBannerMode(Mode.OFF);
		ApplicationContext ctx = app.run(args);
		WebConfig webConfig=ctx.getBean(WebConfig.class);
		logger.info("系统配置上传路径为："+webConfig.getUploadPath());
		
		 logger.info("系统正常启动了...");
	}
	
//	@Autowired
//	private HelloWorldService helloWorldService;

	@Override
	public void run(String... args) {
		
		logger.info("系统启动中...");
//		if (args.length > 0 && args[0].equals("exitcode")) {
//			throw new ExitException();
//		}
	}
}

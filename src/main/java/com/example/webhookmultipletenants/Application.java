package com.example.webhookmultipletenants;

import com.box.sdk.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.FileReader;
import java.io.Reader;
import java.net.URL;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		System.out.println("main start");

		try (ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args)) {
			Application app = ctx.getBean(Application.class);
			app.run(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run(String... args) throws Exception {

		// tenant B sa : AutomationUser_1453444_ZWPfKxybRc@boxdevedition.com, 15084782872

		String tenantBFolderId = "130589318495";
		String tenantBEID = "317356714";
		Reader reader = new FileReader("324763110_injlz4d8_config.json");
		URL webhookURL = new URL("https://webhook.site/d7874014-2f86-4724-863f-1d8bbe237379");

		System.out.println("url " + webhookURL.toString());
		BoxConfig boxConfig = BoxConfig.readFrom(reader);

		boxConfig.setEnterpriseId(tenantBEID);

		BoxDeveloperEditionAPIConnection api = BoxDeveloperEditionAPIConnection.getAppEnterpriseConnection(boxConfig);

		BoxUser currentUser = BoxUser.getCurrentUser(api);
		System.out.println("current user id = " + currentUser.getID());

		BoxFolder folder = new BoxFolder(api, tenantBFolderId);
		System.out.println("folder id = " + folder.getID());

		BoxWebHook.Info webhookInfo = BoxWebHook.create(folder, webhookURL, BoxWebHook.Trigger.FILE_UPLOADED);
		System.out.println("webhook id" + webhookInfo.getID());
	}

}


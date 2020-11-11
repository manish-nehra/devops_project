package devops_class_project.Class.Project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.FileInputStream;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.auth.oauth2.GoogleCredentials;

@SpringBootApplication
public class GenericApplication {

    public static void main(String[] args) {
	try {
	    FileInputStream serviceAccount = new FileInputStream("/home/law/playground/devops_project/scratchspacekey.json");

	    FirebaseOptions options = new FirebaseOptions.Builder()
		.setCredentials(GoogleCredentials.fromStream(serviceAccount))
		.setDatabaseUrl("https://sratchspace.firebaseio.com")
		.build();
	    FirebaseApp.initializeApp(options);

	    System.out.println("FirebaseApp Authenticated and Connected");
	} catch (Exception e) {
	    System.out.println(e.toString());
	}


	SpringApplication.run(GenericApplication.class, args);
    }

}

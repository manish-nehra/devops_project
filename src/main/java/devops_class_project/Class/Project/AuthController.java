package devops_class_project.Class.Project;

import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.google.cloud.firestore.WriteResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.json.simple.JSONObject; 
import org.json.simple.parser.*; 
//import org.springframework.web.bind.annotation.GetMapping;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.DocumentReference;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.firebase.cloud.FirestoreClient;
import java.util.Map;



@RestController
public class AuthController {
    Firestore db = FirestoreClient.getFirestore();

    Map<String, Object> getdocument(String name){
	DocumentReference docRef = db.collection("denoter").document(name);
	ApiFuture<DocumentSnapshot> future = docRef.get();
	try{
	    DocumentSnapshot document = future.get();
	    if (document.exists()) {
		return document.getData();
	    } else {
		System.out.println("No such document!");
	    }
	} catch(Exception e) {
	    System.out.println(e.toString());
	}

	return null;
    }

    @PostMapping(path= "/api/registeruser", consumes = "application/json")
    public String handler(@RequestBody String data) {
	try{
	    Object load = new JSONParser().parse(data);
	    JSONObject obj = (JSONObject)load;
	    String name = (String) obj.get("name");
	    String password = (String) obj.get("password");
	    String uid = (String) obj.get("username");

	    Map<String, Object> users = getdocument("users");

	    if(users.containsKey(uid)){
		return "not unique uid";
	    } else{
		Map<String, String> inner = new HashMap<>();
		inner.put("name", name);
		inner.put("password", password);
		users.put(uid, inner);

		ApiFuture<WriteResult> future = db.collection("denoter").document("users").set(users);

		System.out.println("Update time : " + future.get().getUpdateTime());
	    }

	    return "ok";
	} catch (Exception e){
	    e.printStackTrace();
	}

	return "failed";
    }

    @PostMapping(path= "/api/validatelogin")
    public String validatelogin(@RequestBody String payload) {
	if(db == null || payload.isEmpty()) {
	    return "Failed";
	}

	try{
	    Object load = new JSONParser().parse(payload);
	    JSONObject obj = (JSONObject)load;

	    String uid = (String) obj.get("username");
	    String password = (String) obj.get("password");

	    Map<String, Object> document = getdocument("users");

	    if (document != null) {
		Object user_obj = document.get(uid);

		@SuppressWarnings("unchecked")
		Map<String, String> user = (Map<String, String>)user_obj;

		if(user != null)
		    if(user.get("password").equals(password)){
			return "success";
		    }
		else 
		    return "user not found";
	    } else {
		System.out.println("doc not found");
	    }
	} catch(Exception e) {
	    System.out.println(e.toString());
	}
	return "failed";
    }
}

package devops_class_project.Class.Project;

import java.util.Map;
import java.util.ArrayList;

class User{
    String name;
    String email;
    String password;
    
    User() {

    }

    User(String n, String e, String p){
	this.name = n;
	this.email = e;
	this.password = p;
    }
}


class Users{
    ArrayList<Map<String, Map<String, String>>> load;

    Users() {

    }

    Users(ArrayList<Map<String, Map<String, String>>> data) {
	this.load = data;
    } 


}

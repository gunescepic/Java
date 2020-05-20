package hw2;
public class StudyGroup {
    private String name;
    private Lab lab;

    public StudyGroup(String name, Lab lab){
        this.name = name;
        this.lab = lab;
    }
    public String getName(){
        return name;
    }
    public Lab getLab() {
        return lab;
    }
    /* I decided to implement synchronization on Lab objects, since everytime when a Lab object is initiated,
    the student threads should be synchronized according to that. In studyGroup class when startStudyingWith is called,
    it calls another function in Lab class, which is login(#name) with the name of the studyGroups's name.
    And when it calls stopStudyingWith(), again another function is called in Lab class, logout().
    Details are explained in Lab class.
     */
    public  void startStudyingWith() {
        lab.login(this.name);
    }

    public  void stopStudyingWith() {
        lab.logout();
    }
}
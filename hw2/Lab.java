package hw2;

import java.util.concurrent.Semaphore;
public class Lab {
    private String name;
    private int capacity;
    private String currentStudyGroup; //current study group's name in the lab
    private Semaphore isFull; //Semaphore for capacity in lab
    private Semaphore isOccupied; //Semaphore to check if lab is occupied or not

    public Lab(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
        this.isFull = new Semaphore(capacity, true); //isFull is initialized to capacity
        this.isOccupied = new Semaphore(1, true); //isOccupied here is used as mutex
    }
    public  String getName() {
        return this.name;
    }
    public int getCapacity() {
        return this.capacity;
    }
    public  String getCurrentStudyGroup(){
        return this.currentStudyGroup;
    }
    public  void setCurrentStudyGroup(String currentStudyGroup){
        this.currentStudyGroup = currentStudyGroup;
    }
    /* returns if the given name in the parameter is the same as the name in Lab currently. */
    public boolean compareGroupNames(String groupName){
        return currentStudyGroup.equals(groupName);
    }
    /* login function implements startStudyingWith() which takes groupName as parameter, which indicates a current thread which tries to get in a lab,
    We have 3 conditions for a thread to get in a lab:
     - Lab might be empty.
        In this case, we check if the current study group in the lab is null, then, isOccupied semaphore will be
        acquired to imply that we conquer this lab and set the name! and don't let any other student from different group in.
        Also, isFull semaphore is acquired to increase the number of students in the lab,
        we need that information to not fill the lab more than people it can handle.
     - Lab might be occupied and the conqueror group might be different then the current one.
        In this case, there is no chance other than wait that group to finish their job and leave. When they leave
        they release isOccupied semaphore and current thread can finally enter to lab.
        Also, isFull semaphore is acquired to increase the number of students in the lab,
        we need that information to not fill the lab more than people it can handle.
     - Lab might be occupied by the group same with the current thread.
        In this case, current thread only waits for a place to get in, which acquires isFull semaphore.
     */
    public void login(String groupName) {
        if(getCurrentStudyGroup()==null){
            try{
                isOccupied.acquire();
                isFull.acquire();
                setCurrentStudyGroup(groupName);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            return;
        }
        if(!compareGroupNames(groupName) && getCurrentStudyGroup() != null){
            try{
                isOccupied.acquire();
                isFull.acquire();
                setCurrentStudyGroup(groupName);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            return;
        }
        if(compareGroupNames(groupName)){
            try {
                isFull.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return;
        }
    }
    /* logout function implements stopStudyingWith function, here we have 2 cases,
     - thread leaves and still people in the lab, so it just releases isFull semaphore to notify other threads from same study group
      that there is a place for them to get in!
     - thread is the last one who leaves the lab, in this case it not just notifies the empty place, also notifies that we as group X
     leave the lab, so a thread from another study group can take the lab freely.
     */
    public void logout() {
        isFull.release();
        if(isFull.availablePermits() == getCapacity()){
            setCurrentStudyGroup(null);
            isOccupied.release();
        }
    }
}
package xugong.com.test02parent;

public class Student extends Person{
    public String sid;

    @Override
    public void eat() {
        System.out.println("学生吃食堂");
    }
}

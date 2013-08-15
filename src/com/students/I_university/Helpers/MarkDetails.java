package com.students.I_university.Helpers;

/**
 * Created with IntelliJ IDEA.
 * User: kotvaska
 * Date: 11.08.13
 * Time: 15:58
 * To change this template use File | Settings | File Templates.
 */
public class MarkDetails {

    private int id;
    private String courseName;
    private String mark;

    public MarkDetails (int id, String courseName, String mark) {
        setId(id);
        setCourseName(courseName);
        setMark(mark);
    }

    public void setId (int id) {
        this.id = id;
    }

    public void setCourseName (String courseName) {
        this.courseName = courseName;
    }

    public void setMark (String mark) {
        this.mark = mark;
    }

    public int getId () {
        return this.id;
    }

    public String getCourseName () {
        return this.courseName;
    }

    public String getMark () {
        return this.mark;
    }

}

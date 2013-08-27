package com.students.I_university.Courses;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Echoes
 * Date: 26.08.13
 * Time: 12:44
 * To change this template use File | Settings | File Templates.
 */
public class TopicClass {
    public String Name;
    public List<ElementClass> ELEMENTS = new ArrayList<ElementClass>();

    public TopicClass(String aName, List<ElementClass> aElements) {
        Name = aName;
        ELEMENTS = aElements;
    }
}
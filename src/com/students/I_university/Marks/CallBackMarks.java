package com.students.I_university.Marks;

import com.students.I_university.Helpers.MarkDetails;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: kirilovskikh
 * Date: 09.08.13
 * Time: 16:41
 * To change this template use File | Settings | File Templates.
 */
public interface CallBackMarks {

    public void returnMarks(HashMap<Integer, MarkDetails> map);

}

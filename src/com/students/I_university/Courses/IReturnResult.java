package com.students.I_university.Courses;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Echoes
 * Date: 26.08.13
 * Time: 12:45
 * To change this template use File | Settings | File Templates.
 */
public interface IReturnResult <T>
{
    public void returnResult(List<T> LIST);
}
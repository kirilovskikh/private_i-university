package com.students.I_university.Courses;

/**
 * Created with IntelliJ IDEA.
 * User: Echoes
 * Date: 26.08.13
 * Time: 12:44
 * To change this template use File | Settings | File Templates.
 */
public class ElementClass {
    //Тип элемента (label - текстовый блок; resourse - файл)
    public String Type;
    //Текст в label и Имя.Расширение для resourse
    public String Text;
    //Ссылка на скачивание resourse
    public String URL;
    //Расширение resourse в формате ".расширение"
    public String Extension;
    //Размер resourse в Кбайтах
    public String Size;

    //Конструктор для resourse
    public ElementClass(String aType, String aText, String aURL, String aExtension, String aSize) {
        Type = aType;
        Text = aText;
        URL = aURL;
        Extension = aExtension;
        Size = aSize;
    }

    //Конструктор для label
    public ElementClass(String aType, String aText) {
        Type = aType;
        Text = aText;
    }
}

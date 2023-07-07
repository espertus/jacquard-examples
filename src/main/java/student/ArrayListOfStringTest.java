package student;

import buggy.ArrayListOfString;

public abstract class ArrayListOfStringTest {
    // begin code provided to students
    public AbstractArrayListOfString makeArrayListOfString() {
        return new ArrayListOfString();
    }

    public AbstractArrayListOfString makeArrayListOfString(String... strings) {
        AbstractArrayListOfString list = makeArrayListOfString();
        for (String string : strings) {
            list.add(string);
        }
        return list;
    }
    // end code provided to students


}

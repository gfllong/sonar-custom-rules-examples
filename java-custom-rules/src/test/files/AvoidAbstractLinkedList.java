import org.apache.commons.collections4.list.AbstractLinkedList;
import java.util.ArrayList;

class A {
  void foo() {
    AbstractLinkedList myList = new AbstractLinkedList(new ArrayList<>()); // Noncompliant {{Avoid using AbstractLinkedList}}
    // Noncompliant@+1
    MyList myOtherList = new MyList(); // as MyList extends the UnmodifiableList, we expect an issue here
  }
}

class MyList extends AbstractLinkedList {

}

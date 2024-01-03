package LabaratoryExercises.Lab8.XML;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

interface XMLComponent {
    String print(int indent);

    void addAttribute(String key, String value);

}

abstract class XMLElement implements XMLComponent {
    String tagName;
    Map<String, String> attributes;

    public XMLElement(String tagName) {
        this.tagName = tagName;
        this.attributes = new LinkedHashMap<>();
    }

    public void addAttribute(String key, String value) {
        this.attributes.put(key, value);
    }
}
class XMLLeaf extends XMLElement{

    String value;

    public XMLLeaf(String tagName, String value) {
        super(tagName);
        this.value = value;
    }

    @Override
    public String print(int indent) {
        //<student type="redoven" smer="KNI"> Trajce Trajkov </student>
        return String.format(
                "%s<%s%s>%s</%s>",
                IntStream.range(0, indent).mapToObj(i->"\t").collect(Collectors.joining("")),
                tagName,
                attributes.entrySet().stream()
                        .map(entry -> String.format(" %s=\"%s\"", entry.getKey(), entry.getValue()))
                        .collect(Collectors.joining("")),
                value,
                tagName
        );
    }
}

class XMLComposite extends XMLElement{

    List<XMLComponent> children;

    public XMLComposite(String tagName) {
        super(tagName);
        this.children = new ArrayList<>();
    }

    public void addComponent(XMLComponent child) {
        children.add(child);
    }

    @Override
    public String print(int indent) {
        return String.format(
                "%s<%s %s>\n%s\n%s</%s>",
                IntStream.range(0, indent).mapToObj(i->"\t").collect(Collectors.joining("")),
                tagName,
                attributes.entrySet().stream()
                        .map(entry -> String.format("%s=\"%s\"", entry.getKey(), entry.getValue()))
                        .collect(Collectors.joining(" ")),
                children.stream().map(child -> child.print(indent+1)).collect(Collectors.joining("\n")),
                IntStream.range(0, indent).mapToObj(i->"\t").collect(Collectors.joining("")),
                tagName
        );
    }
}




public class XMLTest {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int testCase = sc.nextInt();
        XMLComponent component = new XMLLeaf("student", "Trajce Trajkovski");
        component.addAttribute("type", "redoven");
        component.addAttribute("program", "KNI");

        XMLComposite composite = new XMLComposite("name");
        composite.addComponent(new XMLLeaf("first-name", "trajce"));
        composite.addComponent(new XMLLeaf("last-name", "trajkovski"));
        composite.addAttribute("type", "redoven");
        component.addAttribute("program", "KNI");

        if (testCase==1) {
            //TODO Print the component object
            System.out.println(component.print(0));
        } else if(testCase==2) {
            //TODO print the composite object
            System.out.println(composite.print(0));
        } else if (testCase==3) {
            XMLComposite main = new XMLComposite("level1");
            main.addAttribute("level","1");
            XMLComposite lvl2 = new XMLComposite("level2");
            lvl2.addAttribute("level","2");
            XMLComposite lvl3 = new XMLComposite("level3");
            lvl3.addAttribute("level","3");
            lvl3.addComponent(component);
            lvl2.addComponent(lvl3);
            lvl2.addComponent(composite);
            lvl2.addComponent(new XMLLeaf("something", "blabla"));
            main.addComponent(lvl2);
            main.addComponent(new XMLLeaf("course", "napredno programiranje"));

            //TODO print the main object
            System.out.println(main.print(0));
        }
    }
}

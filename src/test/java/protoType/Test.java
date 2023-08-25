package protoType;

public class Test {
    public static void main(String[] args) {
        Manager manager = new Manager();
        UnderLinePen underLinePen = new UnderLinePen('~');
        MessageBox messageBox1 = new MessageBox('*');
        MessageBox messageBox2 = new MessageBox('/');
        manager.register("strong message", underLinePen);
        manager.register("warning box", messageBox1);
        manager.register("slash box", messageBox2);

        Product p1 = manager.create("strong message");
        p1.use("helloWorld");
        Product p2 = manager.create("warning box");
        p2.use("helloWorld");
        Product p3 = manager.create("slash box");
        p3.use("helloWorld");

    }
}

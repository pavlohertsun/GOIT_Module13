import user.Address;
import user.Company;
import user.Geo;
import user.User;

public class Main {
    public static void main(String[] args) throws Exception{
        HTTPMethods http = new HTTPMethods();
        User user = new User(0,"Pavlo Hertsun", "pavlo_hertsun", "pavlohertsun@example.com",
                new Address("101 Maple St.", "Apt. 12", "Lviv", "79032", new Geo(-37.12, 37.12)),
                        "+380961234567", "pavlohertsun.com", new Company("PH", "Multi-layered client-server neural-net",
                        "harness real-time e-markets"));
        System.out.println(http.getAllUsers());
//        System.out.println(http.getUserById(11));
//        System.out.println(http.getUserByName("Delphine"));
//        System.out.println(http.createUser(user));
//        System.out.println(http.updateUser(5, user));
//        System.out.println(http.deleteUser(6));
//        HTTPMethodsPostsTodos http = new HTTPMethodsPostsTodos();
//        System.out.println(http.getAllCommentsOfPost(1));
//        System.out.println(http.getAllNotCompletedTodosOfUser(1));
    }
}

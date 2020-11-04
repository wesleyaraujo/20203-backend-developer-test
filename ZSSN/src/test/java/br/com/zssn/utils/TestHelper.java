//package br.com.zssn.utils;
//
//import br.com.zssn.entity.User;
//
//import java.util.Random;
//import java.util.UUID;
//
//import static java.lang.String.format;
//
//public class TestHelper {
//    public static User buildUser() {
//        String uuid = UUID.randomUUID().toString();
//        return User.builder()
//                .name("name-"+uuid)
//                .email(format("someone-%s@gmail.com", uuid))
//                .build();
//    }
//
//    public static User buildUserWithId() {
//        Random random = new Random();
//        String uuid = UUID.randomUUID().toString();
//        return User.builder()
//                .id(random.nextLong())
//                .name("name-"+uuid)
//                .email(format("someone-%s@gmail.com", uuid))
//                .build();
//    }
//}

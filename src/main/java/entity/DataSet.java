package entity;

import java.util.ArrayList;

public class DataSet {
    public String type;
    public Object data;

    public class Auth {
        public String type;
        public Data data;

        public class Data {
            public int success;
            public int user_id;
            public String auth_token;
            public ArrayList<ErrorAction> error;
        }
    }

    public class PublicMessage {
        public String type;
        public MessageSet data;

        public class MessageSet {
            public int count;
            public ArrayList<Message> list;
        }
    }
}

import com.google.gson.Gson;
import entity.DataSet;
import entity.Message;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.*;

public class Chat {
    private int last_message_id = 0;
    private static Chat chat = null;
    private static final String URL = "ws://0.0.0.0:27800";

    WebsocketClientEndpoint clientEndPoint = null;

    public static Chat getInstance() {
        if (chat == null) {
            chat =  new Chat();
        }

        return chat;
    }
    private Chat() {
        try{
            // open websocket
            clientEndPoint = new WebsocketClientEndpoint(new URI(URL));

            // add listener
            clientEndPoint.addMessageHandler(new WebsocketClientEndpoint.MessageHandler() {
                public void handleMessage(String message) {
                    System.out.println(message);
                }
            });
        }   catch(Exception e){System.out.println(e);}
    }

    public ArrayList<Message> getMessage() {
        String json_messages = null;
        ArrayList<Message> message_list = null;

        //=== Получаем данные из АПИ
        try {
            Map<String,Object> data = new LinkedHashMap<>();
            data.put("chat_id", 1);

            if (last_message_id > 0) {
                data.put("id", last_message_id);
            } else {
                data.put("count", 5);
            }

            json_messages =  new RequestApi().sendRequest("/get_message/", data);
        } catch (Exception e) {
            System.out.println("Exception in NetClientGet:- " + e);
        }

        //=== Парсим JSON
        if (json_messages != null) {
            Gson g = new Gson();
            DataSet data_set_message = g.fromJson(json_messages, DataSet.class);

            last_message_id = data_set_message.list.get(0).id;

            Collections.sort(data_set_message.list, new Comparator<Message>() {
                public int compare(Message o1, Message o2) {
                    int result = 0;

                    if (o1.id > o2.id) {
                        result = 1;
                    }
                    if (o1.id < o2.id) {
                        result = -1;
                    }
                    return result;
                }
            });

            message_list = data_set_message.list;
            /*for (Message message : data_set_message.list) {
                System.out.println(message.id);
            }*/
        }

        return message_list;
    }

    public static String printMessage(ArrayList<Message> message_list) {
        StringBuilder result= new StringBuilder();

        if (message_list != null && !message_list.isEmpty()) {
            for (Message message : message_list) {
                result.append("id: "+message.id+"\n");
                result.append("user_author: "+message.user_author+"\n");
                result.append("message: "+message.message+"\n");
                result.append("============\n");
            }
        }

        return result.toString();
    }

    public void sendMessage(String message) {
        String json_messages = null;

        try {
            Map<String,Object> data = new LinkedHashMap<>();
            data.put("action", "PublicMessage");
            data.put("chat_id", 1);
            data.put("user_author", 1);
            data.put("message",message);

            Gson gson = new Gson();
            String json = gson.toJson(data, LinkedHashMap.class);

            clientEndPoint.sendMessage(json);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

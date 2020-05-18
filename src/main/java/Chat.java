import com.google.gson.Gson;
import entity.*;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.net.URI;
import java.util.*;

public class Chat {
    private static Chat chat = null;
    private static final String URL = "ws://0.0.0.0:27800";
    WebsocketClientEndpoint clientEndPoint = null;

    public Controller controller;

    public static Chat getInstance(Controller controller) {
        if (chat == null) {
            chat =  new Chat(controller);
        }

        return chat;
    }
    private Chat(Controller controller) {
        this.controller = controller;
    }

    public void connect() {
        try{
            // open websocket
            clientEndPoint = new WebsocketClientEndpoint(new URI(URL));

            // add listener
            clientEndPoint.addMessageHandler(new WebsocketClientEndpoint.MessageHandler() {
                public void handleMessage(String message) {
                    System.out.println(message);

                    if (message != null) {
                        Gson g = new Gson();
                        DataSet data_set_message = g.fromJson(message, DataSet.class);

                        //=== Auth
                        if (data_set_message.type != null && data_set_message.type.equals("auth")) {
                            DataSet.Auth data_result_action = g.fromJson(message, DataSet.Auth.class);

                            //=== Show error's
                            if (!data_result_action.data.error.isEmpty()) {
                                StringBuilder error_text = new StringBuilder();
                                for (ErrorAction error : data_result_action.data.error) {
                                    error_text.append(error.text+"\n");
                                }
                                JOptionPane.showMessageDialog(null, error_text);
                            }

                            if (data_result_action.data.success == 1) {
                                // Авторизуем пользователя
                                controller.getWindow().loginForm.setVisible(false);
                                controller.getWindow().viewForm.setVisible(true);
                            }
                        }

                        //=== Chat message
                        if (data_set_message.type != null && data_set_message.type.equals("public_message")) {
                            DataSet.PublicMessage data_result_action = g.fromJson(message, DataSet.PublicMessage.class);

                            if (!data_result_action.data.list.isEmpty()) {
                                for (Message chat_message: data_result_action.data.list) {
                                    controller.getWindow().getField_chat().append(printMessage(chat_message));
                                }
                            }
                        }
                    }

                    //=== Парсим JSON
                    /*if (message != null) {
                        Gson g = new Gson();
                        DataSet data_set_message = g.fromJson(message, DataSet.class);

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

                        field_chat.append(printMessage(data_set_message.list));
                    }*/
                }
            });
        }   catch(Exception e){System.out.println(e);}
    }

    /*public ArrayList<Message> getMessage() {
        String json_messages = null;
        ArrayList<Message> message_list = null;

        //=== Получаем данные из АПИ
        try {
            Map<String,Object> data = new LinkedHashMap<>();
            data.put("chat_id", 1);

            json_messages =  new RequestApi().sendRequest("/get_message/", data);
        } catch (Exception e) {
            System.out.println("Exception in NetClientGet:- " + e);
        }

        //=== Парсим JSON
        if (json_messages != null) {
            Gson g = new Gson();
            MessageSet data_set_message = g.fromJson(json_messages, MessageSet.class);

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
            //for (Message message : data_set_message.list) {
            //    System.out.println(message.id);
            //}
        }

        return message_list;
    }*/

    public static String printMessage(ArrayList<Message> message_list) {
        StringBuilder result= new StringBuilder();

        if (message_list != null && !message_list.isEmpty()) {
            for (Message message : message_list) {
                result.append(printMessage(message));
            }
        }

        return result.toString();
    }

    public static String printMessage(Message message) {
        StringBuilder result= new StringBuilder();

        result.append("user_author: "+message.user_author+"\n");
        result.append("message: "+message.message+"\n");
        result.append("============\n");

        return result.toString();
    }

    public String prepareData(Map<String,Object> data) {
        Gson gson = new Gson();
        String json = gson.toJson(data, LinkedHashMap.class);

        return json;
    }

    public void sendData(Map<String,Object> data) {
        try {
            String json = prepareData(data);

            clientEndPoint.sendMessage(json);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void sendMessage(String message) {
        Map<String,Object> data = new LinkedHashMap<>();
        data.put("action", "public_message");
        data.put("chat_id", 1);
        data.put("user_author", 1);
        data.put("message",message);

        sendData(data);
    }

    public void authUser(String name, String password) {
        Map<String,Object> data = new LinkedHashMap<>();
        data.put("action", "auth_user");
        data.put("name", name);
        data.put("password",password);

        sendData(data);
    }

    public void createUser(String name, String password, String password_confirm) {
        Map<String,Object> data = new LinkedHashMap<>();
        data.put("action", "create_user");
        data.put("name", name);
        data.put("password",password);
        data.put("password_confirm",password_confirm);

        sendData(data);
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(null, message);
    }
}

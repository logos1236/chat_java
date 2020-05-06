import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window implements ActionListener {
    public Window() {
        initComponents();
    }

    private JFrame viewForm;
    private int gridy = 0;

    private void initComponents() {
        //JFrame.setDefaultLookAndFeelDecorated(false);
        viewForm = new JFrame("Чат");
        viewForm.setSize(620, 620);
        viewForm.setLocationRelativeTo(null);
        viewForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        //=== Создание панели с текстовыми полями
        JPanel contents = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        contents.setLayout(layout);
        GridBagConstraints constraints = new GridBagConstraints();

        //=== По умолчанию натуральная высота, максимальная ширина
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.5;

        //=== Поле чата
            JLabel title_field_chat = new JLabel("Чат");
            constraints.gridx = 0;
            constraints.gridy = gridy;
            constraints.gridwidth = 3;
            contents.add(title_field_chat, constraints);
            gridy++;

            JTextArea field_chat = new JTextArea( 10, 10);
            field_chat.setToolTipText("Чат");
            field_chat.setAutoscrolls(true);
            field_chat.setEditable(false);
            JScrollPane jsp_field_chat = new JScrollPane(field_chat);

            constraints.fill = GridBagConstraints.HORIZONTAL;
            constraints.gridx = 0;
            constraints.gridy = gridy;
            constraints.gridwidth = 3;
            contents.add(jsp_field_chat, constraints);

            //=== Добавляем текст в поле
            //Chat chat = Chat.getInstance();
            //field_chat.setText(chat.printMessage(chat.getMessage()));

            gridy++;

        //=== Поле ввода сообщения
            JLabel title_field_message = new JLabel("Введите сообщение");
            constraints.gridx = 0;
            constraints.gridy = gridy;
            constraints.gridwidth = 3;
            contents.add(title_field_message, constraints);
            gridy++;

            JTextArea field_message = new JTextArea( 3, 10);
            field_message.setToolTipText("Сообщение");
            field_message.setAutoscrolls(true);
            JScrollPane jsp_field_message = new JScrollPane(field_message);

            constraints.fill = GridBagConstraints.HORIZONTAL;
            constraints.gridx = 0;
            constraints.gridy = gridy;
            constraints.gridwidth = 3;
            contents.add(jsp_field_message, constraints);

            gridy++;

        //=== Отправить сообщение
            JButton button_send_message = new JButton("Отправить сообщение");
            button_send_message.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    //=== Отправляем сообщение
                    Chat chat = Chat.getInstance();
                    chat.sendMessage(field_message.getText());

                    //=== Получаем новые сообщения
                    //field_chat.append(chat.printMessage(chat.getMessage()));
                }
            });
            constraints.fill = GridBagConstraints.HORIZONTAL;
            constraints.gridx = 0;
            constraints.gridy = gridy;
            constraints.gridwidth = 1;
            contents.add(button_send_message, constraints);

        viewForm.getContentPane().add(contents);
        viewForm.pack();
        viewForm.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}

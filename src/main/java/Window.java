import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window implements ActionListener {
    public Controller controller;

    public JFrame viewForm;
    public JFrame loginForm;
    public JFrame newUserForm;

    private JTextArea field_chat = new JTextArea( 10, 10);
    public JTextArea getField_chat() {
        return field_chat;
    }

    public Window(Controller controller) {
        this.controller = controller;

        //=== Инициализируем формы
        initLoginForm();
        initNewUserForm();
        initViewForm();
    }

    private void initLoginForm() {
        int gridy = 0;

        loginForm = new JFrame("Авторизиция");
        loginForm.setSize(620, 620);
        loginForm.setLocationRelativeTo(null);
        loginForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //=== Создание панели с текстовыми полями
        JPanel contents = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        contents.setLayout(layout);
        GridBagConstraints constraints = new GridBagConstraints();

        //=== По умолчанию натуральная высота, максимальная ширина
        //constraints.fill = GridBagConstraints.HORIZONTAL;
        //constraints.weightx = 0.5;

        //=== Поле логина
            JLabel title_field_login = new JLabel("Логин", SwingConstants.CENTER);
            constraints.gridx = 0;
            constraints.gridy = gridy;
            constraints.gridwidth = 3;
            contents.add(title_field_login, constraints);
            gridy++;

            JTextField field_login = new JTextField("", 20);
            field_login.setToolTipText("Чат");

            constraints.fill = GridBagConstraints.HORIZONTAL;
            constraints.gridx = 0;
            constraints.gridy = gridy;
            constraints.gridwidth = 3;
            contents.add(field_login, constraints);
            gridy++;

        //=== Поле пароля
            JLabel title_field_password = new JLabel("Пароль", SwingConstants.CENTER);
            constraints.gridx = 0;
            constraints.gridy = gridy;
            constraints.gridwidth = 3;
            contents.add(title_field_password, constraints);
            gridy++;

            JTextField field_password = new JTextField("", 20);
            field_password.setToolTipText("Чат");

            constraints.fill = GridBagConstraints.HORIZONTAL;
            constraints.gridx = 0;
            constraints.gridy = gridy;
            constraints.gridwidth = 3;
            contents.add(field_password, constraints);
            gridy++;

        //=== Авторизоваться
            JButton button_login = new JButton("Авторизоваться");
            button_login.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    controller.getChat().authUser(field_login.getText(), field_password.getText());
                }
            });
            constraints.fill = GridBagConstraints.HORIZONTAL;
            constraints.gridx = 0;
            constraints.gridy = gridy;
            constraints.gridwidth = 3;
            contents.add(button_login, constraints);
            gridy++;

        //=== Создать нового пользователя
            JButton button_newUser = new JButton("Новый пользователь");
            button_newUser.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    //=== Показываем форму создания нового пользователя
                    loginForm.setVisible(false);
                    newUserForm.setVisible(true);
                }
            });
            constraints.fill = GridBagConstraints.HORIZONTAL;
            constraints.gridx = 0;
            constraints.gridy = gridy;
            constraints.gridwidth = 3;
            contents.add(button_newUser, constraints);
            gridy++;

        loginForm.getContentPane().add(contents);
        loginForm.pack();
        loginForm.setVisible(true);
    }

    private void initNewUserForm() {
        int gridy = 0;

        newUserForm = new JFrame("Новый пользователь");
        newUserForm.setSize(620, 620);
        newUserForm.setLocationRelativeTo(null);
        newUserForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //=== Создание панели с текстовыми полями
            JPanel contents = new JPanel();
            GridBagLayout layout = new GridBagLayout();
            contents.setLayout(layout);
            GridBagConstraints constraints = new GridBagConstraints();

        //=== Поле логина
            JLabel title_field_login = new JLabel("Логин", SwingConstants.CENTER);
            constraints.gridx = 0;
            constraints.gridy = gridy;
            constraints.gridwidth = 3;
            contents.add(title_field_login, constraints);
            gridy++;

            JTextField field_login = new JTextField("", 20);
            field_login.setToolTipText("Чат");

            constraints.fill = GridBagConstraints.HORIZONTAL;
            constraints.gridx = 0;
            constraints.gridy = gridy;
            constraints.gridwidth = 3;
            contents.add(field_login, constraints);
            gridy++;

        //=== Поле пароля
            JLabel title_field_password = new JLabel("Пароль", SwingConstants.CENTER);
            constraints.gridx = 0;
            constraints.gridy = gridy;
            constraints.gridwidth = 3;
            contents.add(title_field_password, constraints);
            gridy++;

            JTextField field_password = new JTextField("", 20);
            field_password.setToolTipText("Чат");

            constraints.fill = GridBagConstraints.HORIZONTAL;
            constraints.gridx = 0;
            constraints.gridy = gridy;
            constraints.gridwidth = 3;
            contents.add(field_password, constraints);
            gridy++;

        //=== Поле подтверждения пароля
            JLabel title_field_password_confirm = new JLabel("Повторите пароль", SwingConstants.CENTER);
            constraints.gridx = 0;
            constraints.gridy = gridy;
            constraints.gridwidth = 3;
            contents.add(title_field_password_confirm, constraints);
            gridy++;

            JTextField field_password_confirm = new JTextField("", 20);
            field_password.setToolTipText("Чат");

            constraints.fill = GridBagConstraints.HORIZONTAL;
            constraints.gridx = 0;
            constraints.gridy = gridy;
            constraints.gridwidth = 3;
            contents.add(field_password_confirm, constraints);
            gridy++;

        //=== Создать пользователя
        JButton button_create_user = new JButton("Создать пользователя");
        button_create_user.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.getChat().createUser(field_login.getText(), field_password.getText(), field_password_confirm.getText());
            }
        });
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = gridy;
        constraints.gridwidth = 3;
        contents.add(button_create_user, constraints);

        newUserForm.getContentPane().add(contents);
        newUserForm.pack();
        newUserForm.setVisible(false);
    }

    private void initViewForm() {
        int gridy = 0;

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

            field_chat = new JTextArea( 10, 10);
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
            //field_chat.setText(chat.printMessage(chat.getMessage()));

            //chat.field_chat = field_chat;

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
                    controller.getChat().sendMessage(field_message.getText());

                    field_message.setText("");
                }
            });
            constraints.fill = GridBagConstraints.HORIZONTAL;
            constraints.gridx = 0;
            constraints.gridy = gridy;
            constraints.gridwidth = 1;
            contents.add(button_send_message, constraints);

        viewForm.getContentPane().add(contents);
        viewForm.pack();
        viewForm.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}

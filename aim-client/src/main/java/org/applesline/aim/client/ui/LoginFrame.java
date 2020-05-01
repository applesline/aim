package org.applesline.aim.client.ui;

import org.applesline.aim.client.ClientFrame;
import org.applesline.aim.client.ui.event.LoginListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author liuyaping
 * 创建时间：2020年04月28日
 */
public class LoginFrame extends JFrame {

    private ClientFrame chatFrame;
    private JTextField hostField,portField,nickField;
    private JLabel loginName;

    public LoginFrame(ClientFrame chatFrame, JLabel loginName) {
        this.chatFrame = chatFrame;
        chatFrame.getConnectBtn().setEnabled(false);
        this.loginName = loginName;
        this.setName("连接配置");
        this.setSize(345,160);
        Container container = this.getContentPane();
        container.setLayout(new FlowLayout());

        JLabel hostLabel = new JLabel("主机");
        JLabel portLabel = new JLabel("端口");
        JLabel nickLabel = new JLabel("昵称");
        hostField = new JTextField(25);
        portField = new JTextField(25);
        nickField = new JTextField(25);
        hostField.setText("localhost");
        portField.setText("8080");
        JButton confirmBtn = new JButton("确定");
        JButton cancelBtn = new JButton("取消");
        this.add(hostLabel);
        this.add(hostField);
        this.add(portLabel);
        this.add(portField);
        this.add(nickLabel);
        this.add(nickField);
        this.add(confirmBtn);
        this.add(cancelBtn);
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        confirmBtn.addMouseListener(new LoginListener(this));
        cancelBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                LoginFrame.this.setVisible(false);
            }
        });
    }

    public JLabel getLoginName() {
        return loginName;
    }

    public ClientFrame parentUI() {
        return this.chatFrame;
    }

    public JTextField getHostField() {
        return hostField;
    }

    public JTextField getPortField() {
        return portField;
    }

    public JTextField getNickField() {
        return nickField;
    }

}

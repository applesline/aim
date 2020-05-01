package org.applesline.aim.client.ui.event;

import org.applesline.aim.client.connect.AimClient;
import org.applesline.aim.client.ui.LoginFrame;
import org.applesline.aim.common.util.AimUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author liuyaping
 * 创建时间：2020年04月28日
 */
public class LoginListener extends MouseAdapter {

    private LoginFrame loginFrame;

    public LoginListener(LoginFrame loginFrame) {
        this.loginFrame = loginFrame;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        String h = loginFrame.getHostField().getText();
        String p = loginFrame.getPortField().getText();
        String n = loginFrame.getNickField().getText();
        if (AimUtils.isEmptyParam(h,p,n)) {
            JOptionPane.showMessageDialog(loginFrame,"信息不完善","温馨提示", 1);
            return;
        }
        JLabel label = loginFrame.getLoginName();
        label.setForeground(Color.RED);
        label.setText(n);
        try {
            new AimClient(loginFrame.parentUI()).connect(h,Integer.parseInt(p),n,1);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(loginFrame,"连接失败"+ex.getMessage(),"连接失败",1);
        } finally {
            loginFrame.setVisible(false);
            loginFrame.parentUI().setTitle(n);
        }
    }
}

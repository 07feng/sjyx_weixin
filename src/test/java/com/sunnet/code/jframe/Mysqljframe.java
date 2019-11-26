package com.sunnet.code.jframe;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.sunnet.code.util.EntityString;
import com.sunnet.code.util.TestUtil;

import javax.swing.JComboBox;
import java.awt.Panel;
import java.awt.Toolkit;
import java.sql.SQLException;
import java.util.Map;
import java.awt.FlowLayout;
import java.awt.Label;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.Window.Type;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class Mysqljframe extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.生成实体类
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Mysqljframe frame = new Mysqljframe();
					int w = (Toolkit.getDefaultToolkit().getScreenSize().width - frame.getWidth()) / 2;
					int h = (Toolkit.getDefaultToolkit().getScreenSize().height - frame.getHeight()) / 2;
					frame.setLocation(w, h);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Mysqljframe() {
		setTitle("实体类生成");
		setType(Type.UTILITY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 427, 201);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		Panel panel = new Panel();
		FlowLayout flowLayout_2 = (FlowLayout) panel.getLayout();
		flowLayout_2.setAlignment(FlowLayout.LEFT);
		contentPane.add(panel, BorderLayout.CENTER);

		Panel panel_2 = new Panel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_2.getLayout();
		panel.add(panel_2);

		Panel panel_1 = new Panel();
		panel_2.add(panel_1);
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);

		JLabel lblNewLabel = new JLabel("数据库表:");
		panel_1.add(lblNewLabel);

		JComboBox comboBox = new JComboBox();
		panel_1.add(comboBox);

		JLabel label = new JLabel("         请选择数据表生成对应的实体类");
		panel_1.add(label);

		Panel panel_6 = new Panel();
		panel.add(panel_6);

		Panel panel_7 = new Panel();
		panel_6.add(panel_7);

		JLabel label_1 = new JLabel("指定包:");
		panel_7.add(label_1);

		textField = new JTextField();
		textField.setToolTipText("");
		textField.setText("");
		panel_7.add(textField);
		textField.setColumns(10);

		Panel panel_3 = new Panel();
		FlowLayout flowLayout_3 = (FlowLayout) panel_3.getLayout();
		flowLayout_3.setHgap(65);
		panel.add(panel_3);

		Panel panel_4 = new Panel();
		panel_3.add(panel_4);

		JButton btnNewButton = new JButton("指定生成");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String Str_A = comboBox.getSelectedItem().toString();
				int a = JOptionPane.showConfirmDialog(null, "确定要生成么？", "提示", JOptionPane.YES_NO_OPTION);
				if (a == 0) {
						try {
							if (textField.getText().trim().equals("")) {
								String inputValue = JOptionPane.showInputDialog("请输入生成的指定包！");
								textField.setText(inputValue);
							}
							EntityString.getCole(Str_A, JOptionPane.showInputDialog("请输入注释！"),
									textField.getText());
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
			}
		});
		panel_4.add(btnNewButton);

		Panel panel_5 = new Panel();
		panel_3.add(panel_5);

		JButton button = new JButton("全部生成");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int a = JOptionPane.showConfirmDialog(null, "确定要全部生成么？", "提示", JOptionPane.YES_NO_OPTION);
				if (a == 0) {
					for (Map<String, Object> map : EntityString.getTable()) {
						try {
							if (textField.getText().trim().equals("")) {
								String inputValue = JOptionPane.showInputDialog("请输入生成的指定包！");
								textField.setText(inputValue);
							}
							EntityString.getCole(map.get("TABLE_NAME").toString(), "",
									textField.getText());
						} catch (Exception e1) {
							e1.printStackTrace();
						}

					}
				}
			}
		});
		panel_5.add(button);
		for (Map<String, Object> map : EntityString.getTable()) {
			System.err.println("实体：" + TestUtil.getToClassName(map.get("TABLE_NAME").toString()));
			try {
				comboBox.addItem(map.get("TABLE_NAME").toString());
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

}

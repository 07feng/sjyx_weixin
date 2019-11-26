package com.sunnet.code.jframe;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.sunnet.code.common.CodeManyUtil;
import com.sunnet.code.factory.CodeManyGenerateFactory;

import java.awt.Panel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Checkbox;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.UIManager;
import java.awt.Window.Type;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.Choice;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import java.awt.Font;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.MenuListener;
import javax.swing.event.MenuEvent;

public class jframe extends JFrame {

	private JPanel contentPane;
	private JTextField className;
	private JTextField type;
	private JTextField auto;
	private JTextField title;
	private JCheckBox vo;
	private JCheckBox controller;
	private JCheckBox service;
	private JCheckBox serviceImpl;
	private JCheckBox daoImpl;
	private JCheckBox dao;
	private JCheckBox _list;
	private JCheckBox _add;
	private JCheckBox _select;
	private JCheckBox permission;
	private JCheckBox is_permission;
	private JCheckBox standard;
	private JCheckBox txtContent;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					jframe frame = new jframe();
					int w = (Toolkit.getDefaultToolkit().getScreenSize().width - frame.getWidth()) / 2;
					int h = (Toolkit.getDefaultToolkit().getScreenSize().height -frame.getHeight()) / 2;
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
	public jframe() {
		setTitle("代码生成");
		setType(Type.UTILITY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 555, 446);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		Panel panel = new Panel();
		FlowLayout flowLayout_2 = (FlowLayout) panel.getLayout();
		flowLayout_2.setAlignment(FlowLayout.LEFT);
		contentPane.add(panel, BorderLayout.CENTER);

		Panel panel_1 = new Panel();
		panel.add(panel_1);

		JCheckBox checkBox = new JCheckBox("");
		checkBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (checkBox.isSelected()) {
					vo.setSelected(true);
					controller.setSelected(true);
					service.setSelected(true);
					serviceImpl.setSelected(true);
					daoImpl.setSelected(true);
					dao.setSelected(true);
				} else {
					vo.setSelected(false);
					controller.setSelected(false);
					service.setSelected(false);
					serviceImpl.setSelected(false);
					daoImpl.setSelected(false);
					dao.setSelected(false);
				}
			}
		});
		checkBox.setToolTipText("");
		panel_1.add(checkBox);

		JLabel label_1 = new JLabel("代码生成：");
		panel_1.add(label_1);

		Panel panel_2 = new Panel();
		panel_1.add(panel_2);

		controller = new JCheckBox("controller");
		controller.setToolTipText("");
		panel_2.add(controller);

		service = new JCheckBox("service");
		panel_2.add(service);

		serviceImpl = new JCheckBox("serviceImpl");
		panel_2.add(serviceImpl);

		dao = new JCheckBox("dao");
		panel_2.add(dao);

		daoImpl = new JCheckBox("daoImpl");
		panel_2.add(daoImpl);

		vo = new JCheckBox("vo");
		panel_2.add(vo);

		Panel panel_6 = new Panel();
		FlowLayout flowLayout = (FlowLayout) panel_6.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel.add(panel_6);

		JCheckBox checkBox_1 = new JCheckBox("");
		checkBox_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (checkBox_1.isSelected()) {
					_list.setSelected(true);
					_add.setSelected(true);
					_select.setSelected(true);
					txtContent.setSelected(true);
				} else {
					_list.setSelected(false);
					_add.setSelected(false);
					_select.setSelected(false);
					txtContent.setSelected(false);
				}
			}
		});
		checkBox_1.setToolTipText("");
		panel_6.add(checkBox_1);

		JLabel label_3 = new JLabel("页面生成：");
		panel_6.add(label_3);

		Panel panel_7 = new Panel();
		panel_6.add(panel_7);

		_list = new JCheckBox("_list");
		panel_7.add(_list);

		_add = new JCheckBox("_add");
		panel_7.add(_add);

		_select = new JCheckBox("_select");
		panel_7.add(_select);

		txtContent = new JCheckBox("txtContent");
		panel_7.add(txtContent);

		Panel panel_4 = new Panel();
		panel.add(panel_4);
		FlowLayout flowLayout_3 = (FlowLayout) panel_4.getLayout();
		flowLayout_3.setAlignment(FlowLayout.LEFT);

		JCheckBox checkBox_2 = new JCheckBox("");
		checkBox_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (checkBox_2.isSelected()) {
					permission.setSelected(true);
					is_permission.setSelected(true);
					standard.setSelected(true);
				} else {
					permission.setSelected(false);
					is_permission.setSelected(false);
					standard.setSelected(false);
				}
			}
		});
		checkBox_2.setToolTipText("");
		panel_4.add(checkBox_2);

		JLabel label_2 = new JLabel("权限生成：");
		panel_4.add(label_2);

		Panel panel_5 = new Panel();
		panel_4.add(panel_5);

		permission = new JCheckBox("permission");
		panel_5.add(permission);

		is_permission = new JCheckBox("is_permission");
		panel_5.add(is_permission);

		standard = new JCheckBox("标准");
		panel_5.add(standard);

		Panel panel_8 = new Panel();
		panel_8.setBackground(SystemColor.menu);
		panel.add(panel_8);

		JLabel label_4 = new JLabel("/**********************************请填写相关信息*********************************/");
		label_4.setBackground(Color.BLACK);
		panel_8.add(label_4);

		Panel panel_9 = new Panel();
		panel_9.setBackground(SystemColor.menu);
		panel.add(panel_9);

		JLabel label = new JLabel("请填写类名称（大写）：");
		panel_9.add(label);

		className = new JTextField();
		className.setColumns(20);
		panel_9.add(className);

		Panel panel_10 = new Panel();
		panel_10.setBackground(SystemColor.menu);
		panel.add(panel_10);

		JLabel label_5 = new JLabel("请填写包名称（小写）：");
		panel_10.add(label_5);

		type = new JTextField();
		type.setColumns(20);
		panel_10.add(type);

		Panel panel_11 = new Panel();
		panel_11.setBackground(SystemColor.menu);
		panel.add(panel_11);

		JLabel label_6 = new JLabel("请填作者名（可不填）：");
		panel_11.add(label_6);

		auto = new JTextField();
		auto.setColumns(20);
		panel_11.add(auto);

		Panel panel_12 = new Panel();
		panel_12.setBackground(SystemColor.menu);
		panel.add(panel_12);

		JLabel label_7 = new JLabel("请填写注释（可不填）：");
		panel_12.add(label_7);

		title = new JTextField();
		title.setColumns(20);
		panel_12.add(title);

		Panel panel_3 = new Panel();
		panel_3.setBackground(UIManager.getColor("Button.background"));
		FlowLayout flowLayout_1 = (FlowLayout) panel_3.getLayout();
		flowLayout_1.setHgap(220);
		flowLayout_1.setVgap(20);
		panel.add(panel_3);

		JButton btnNewButton = new JButton("生成开始");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				/******************* 页面块 ***********************/
				if (_list.isSelected()) {
					CodeManyUtil._list = true;
				}
				if (_add.isSelected()) {
					CodeManyUtil._add = true;
				}
				if (_select.isSelected()) {
					CodeManyUtil._select = true;
				}

				/******************* 代码块 ***********************/
				if (vo.isSelected()) {
					CodeManyUtil.vo = true;
				}
				if (controller.isSelected()) {
					CodeManyUtil.controller = true;
				}
				if (service.isSelected()) {
					CodeManyUtil.service = true;
				}
				if (serviceImpl.isSelected()) {
					CodeManyUtil.serviceImpl = true;
				}
				if (daoImpl.isSelected()) {
					CodeManyUtil.daoImpl = true;
				}
				if (dao.isSelected()) {
					CodeManyUtil.dao = true;
				}

				/******************* 权限块 *************************/
				if (permission.isSelected()) {
					CodeManyUtil.permission = true;
				}
				if (is_permission.isSelected()) {
					CodeManyUtil.is_permission = true;
				}
				if (standard.isSelected()) {
					CodeManyUtil.standard = true;
				}

				/******************* 提示块 *************************/
				if (className.getText().trim().equals("")) {
					String inputValue = JOptionPane.showInputDialog("请输入类名称！");
					className.setText(inputValue);
				}
				if (type.getText().trim().equals("")) {
					String inputValue = JOptionPane.showInputDialog("请输入包名称!");
					type.setText(inputValue);
				}

				System.err.println("开始生成:----");
				try {
					CodeManyGenerateFactory.codeGenerate(className.getText(), type.getText(), title.getText(),
							auto.getText());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		panel_3.add(btnNewButton);
	}

}

package com.sunnet.code.jframe;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.sunnet.code.util.HtmlToJsp;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JTabbedPane;
import java.awt.TextArea;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Panel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import java.awt.Button;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Window.Type;
import javax.swing.JTable;
import java.awt.TextField;
import java.awt.Toolkit;

import javax.swing.JEditorPane;
import javax.swing.JComboBox;
import java.awt.ScrollPane;

public class Htmlframe extends JFrame {

	private JPanel contentPane;
	private JTextField path_url;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JPanel panels;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Htmlframe frame = new Htmlframe();
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
	public Htmlframe() {
		setTitle("html转换成jsp");
		setType(Type.UTILITY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 458, 323);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		Panel panel_1 = new Panel();
		FlowLayout flowLayout_2 = (FlowLayout) panel_1.getLayout();
		flowLayout_2.setAlignment(FlowLayout.LEFT);
		contentPane.add(panel_1, BorderLayout.NORTH);

		Panel panel = new Panel();
		panel_1.add(panel);
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);

		path_url = new JTextField();
		panel.add(path_url);
		path_url.setColumns(13);

		JButton btnNewButton = new JButton("选择文件夹");
		btnNewButton.setFont(new Font("宋体", Font.PLAIN, 11));
		btnNewButton.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(btnNewButton);

		JButton btnJia = new JButton("＋");
		btnJia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Panel panel_4 = new Panel();

				JLabel label_5 = new JLabel("替換前：");
				panel_4.add(label_5);

				JTextField textField_5 = new JTextField();
				textField_5.setColumns(13);
				panel_4.add(textField_5);

				JLabel label_6 = new JLabel("替換后：");
				panel_4.add(label_6);

				JTextField textField_6 = new JTextField();
				textField_6.setColumns(13);
				panel_4.add(textField_6);

				panels.add(panel_4);
				setVisible(true);
			}
		});
		panel.add(btnJia);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnval = chooser.showDialog(null, "选择文件夹");
				if (returnval == JFileChooser.APPROVE_OPTION) {
					String str = chooser.getSelectedFile().getPath();
					// System.out.println(str);
					path_url.setText(str);
				}
			}
		});

		panels = new JPanel();
		FlowLayout fl_panels = (FlowLayout) panels.getLayout();
		fl_panels.setAlignment(FlowLayout.LEFT);
		contentPane.add(panels, BorderLayout.CENTER);

		Panel panel_2 = new Panel();
		panels.add(panel_2);

		JLabel label_1 = new JLabel("替換前：");
		panel_2.add(label_1);

		textField = new JTextField();
		textField.setColumns(13);
		panel_2.add(textField);

		JLabel label = new JLabel("替換后：");
		panel_2.add(label);

		textField_1 = new JTextField();
		textField_1.setColumns(13);
		panel_2.add(textField_1);

		Panel panel_3 = new Panel();
		panels.add(panel_3);

		JLabel label_2 = new JLabel("替換前：");
		panel_3.add(label_2);

		textField_2 = new JTextField();
		textField_2.setColumns(13);
		panel_3.add(textField_2);

		JLabel label_3 = new JLabel("替換后：");
		panel_3.add(label_3);

		textField_3 = new JTextField();
		textField_3.setColumns(13);
		panel_3.add(textField_3);

		JButton btnNewButton_1 = new JButton("确认替换");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				int count = panels.getComponentCount();
				for (int i = 0; i < count; i++) {
					Object obj = panels.getComponent(i);
					Map<String, Object> map = new HashMap<String, Object>();
					if (((JTextField) ((Panel) obj).getComponent(1)).getText().trim().equals("")
							|| ((JTextField) ((Panel) obj).getComponent(3)).getText().trim().equals("")) {
					} else {
						map.put("str", ((JTextField) ((Panel) obj).getComponent(1)).getText());
						map.put("replace", ((JTextField) ((Panel) obj).getComponent(3)).getText());
						list.add(map);
					}
				}
				HtmlToJsp.getString(path_url.getText(), list);
			}
		});
		contentPane.add(btnNewButton_1, BorderLayout.SOUTH);
	}

}

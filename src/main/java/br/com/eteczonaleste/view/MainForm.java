package br.com.eteczonaleste.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPTemplate;

import br.com.eteczonaleste.entity.Alunos;
import br.com.eteczonaleste.entityManager.AlunosManager;

public class MainForm extends JFrame {
	public static String TEMPLATE_PROPERTY = "template";
	private DPFPTemplate template;
	private Integer ra;
	private EnrollmentForm form;

	public class TemplateFileFilter extends javax.swing.filechooser.FileFilter {
		@Override
		public boolean accept(File f) {
			return f.getName().endsWith(".fpt");
		}

		@Override
		public String getDescription() {
			return "Fingerprint Template File (*.fpt)";
		}
	}

	MainForm() {
		setState(Frame.NORMAL);
		//setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setTitle("Amostra de Inscrição e Verificação de Impressão Digital");
		setResizable(false);

		final JButton enroll = new JButton("Inscrição de impressão digital");
		enroll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onEnroll();
			}
		});

		final JButton verify = new JButton("Verificação de impressão digital");
		verify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onVerify();
			}
		});

		final JButton save = new JButton("Salvar modelo de impressão digital");
		save.setVisible(false);
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onSave();
			}
		});

		final JButton load = new JButton("Leia o modelo de impressão digital");
		load.setVisible(false);
		load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onLoad();
			}
		});

		final JButton quit = new JButton("Fechar");
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.exit(0);
				setVisible(false);
			}
		});

		this.addPropertyChangeListener(TEMPLATE_PROPERTY, new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				//verify.setEnabled(template != null);
				//save.setEnabled(template != null);
				if (evt.getNewValue() == evt.getOldValue())
					return;
				if (template != null) {
					// Atualizar o cadastro de aluno com os templates da digital.

					AlunosManager alunosManager = new AlunosManager();
					String text = MainForm.this.form.txtRA.getText();					
					Alunos alunosPesqRA = alunosManager.findByRm(Integer.parseInt(text));
					
					alunosPesqRA.setTemplate(template.serialize());
					alunosManager.atualizar(alunosPesqRA);
					
					System.out.println("DIGITAL DO ALUNO "+alunosPesqRA.getNome()+" CADASTRADA!");

					JOptionPane.showMessageDialog(MainForm.this,
							"DIGITAL DO ALUNO "+alunosPesqRA.getNome()+" CADASTRADA!",
							"Inscrição de impressão digital", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		JPanel center = new JPanel();
		center.setLayout(new GridLayout(4, 1, 0, 5));
		center.setBorder(BorderFactory.createEmptyBorder(20, 20, 5, 20));
		center.add(enroll);
		center.add(verify);
		center.add(save);
		center.add(load);

		JPanel bottom = new JPanel(new FlowLayout(FlowLayout.TRAILING));
		bottom.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
		bottom.add(quit);

		setLayout(new BorderLayout());
		add(center, BorderLayout.CENTER);
		add(bottom, BorderLayout.PAGE_END);

		pack();
		setSize((int) (getSize().width * 1.6), getSize().height);
		setLocationRelativeTo(null);
		setTemplate(null);
		setVisible(true);
	}

	private void onEnroll() {
		form = new EnrollmentForm(this);
		form.setVisible(true);
	}

	private void onVerify() {
		VerificationForm form = new VerificationForm(this);
		form.setVisible(true);
	}

	private void onSave() {
		JFileChooser chooser = new JFileChooser();
		chooser.addChoosableFileFilter(new TemplateFileFilter());
		while (true) {
			if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
				try {
					File file = chooser.getSelectedFile();
					if (!file.toString().toLowerCase().endsWith(".fpt"))
						file = new File(file.toString() + ".fpt");
					if (file.exists()) {
						int choice = JOptionPane
								.showConfirmDialog(this,
										String.format("File \"%1$s\" already exists.\nDo you want to replace it?",
												file.toString()),
										"Fingerprint saving", JOptionPane.YES_NO_CANCEL_OPTION);
						if (choice == JOptionPane.NO_OPTION)
							continue;
						else if (choice == JOptionPane.CANCEL_OPTION)
							break;
					}
					FileOutputStream stream = new FileOutputStream(file);
					stream.write(getTemplate().serialize());
					stream.close();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(this, ex.getLocalizedMessage(), "Fingerprint saving",
							JOptionPane.ERROR_MESSAGE);
				}
			}
			break;
		}
	}

	private void onLoad() {
		JFileChooser chooser = new JFileChooser();
		chooser.addChoosableFileFilter(new TemplateFileFilter());
		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			try {
				FileInputStream stream = new FileInputStream(chooser.getSelectedFile());
				byte[] data = new byte[stream.available()];
				stream.read(data);
				stream.close();
				DPFPTemplate t = DPFPGlobal.getTemplateFactory().createTemplate();
				t.deserialize(data);
				setTemplate(t);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, ex.getLocalizedMessage(), "Fingerprint loading",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public DPFPTemplate getTemplate() {
		return template;
	}

	public void setTemplate(DPFPTemplate template) {
		DPFPTemplate old = this.template;
		this.template = template;
		firePropertyChange(TEMPLATE_PROPERTY, old, template);
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MainForm();
			}
		});
	}

}

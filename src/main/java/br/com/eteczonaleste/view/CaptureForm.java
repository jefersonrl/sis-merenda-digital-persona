package br.com.eteczonaleste.view;

import java.awt.*;

import java.awt.event.*;
import javax.swing.*;
import com.digitalpersona.onetouch.*;
import com.digitalpersona.onetouch.capture.*;
import com.digitalpersona.onetouch.capture.event.*;
import com.digitalpersona.onetouch.processing.*;


public class CaptureForm
	extends JDialog
{
	private DPFPCapture capturer = DPFPGlobal.getCaptureFactory().createCapture();
	private JLabel picture = new JLabel();
	private JTextField prompt = new JTextField();
	private JTextArea log = new JTextArea();
	private JTextField status = new JTextField("[status line]");
	private JLabel lblRA = new JLabel("RA: ");
	public JTextField txtRA = new JTextField();
	
    public CaptureForm(Frame owner) {
        super (owner, true);
        setTitle("Inscrição de impressão digital");

		setLayout(new BorderLayout());
		rootPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		picture.setPreferredSize(new Dimension(240, 280));
		picture.setBorder(BorderFactory.createLoweredBevelBorder());
		prompt.setFont(UIManager.getFont("Panel.font"));
		prompt.setEditable(false);
		prompt.setColumns(40);
		prompt.setMaximumSize(prompt.getPreferredSize());
		prompt.setBorder(
				BorderFactory.createCompoundBorder(
					BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0), "Prompt:"),
					BorderFactory.createLoweredBevelBorder()
				));
		log.setColumns(40);
		log.setEditable(false);
		log.setFont(UIManager.getFont("Panel.font"));
		JScrollPane logpane = new JScrollPane(log);
		logpane.setBorder(
				BorderFactory.createCompoundBorder(
					BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0), "Status:"),
					BorderFactory.createLoweredBevelBorder()
				));
		
		status.setEditable(false);
		status.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		status.setFont(UIManager.getFont("Panel.font"));
		
		JButton quit = new JButton("Fechar");
        quit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { setVisible(false); }});
        
        JPanel left = new JPanel(new BorderLayout());
        left.setBackground(Color.getColor("control"));
        left.add(lblRA, BorderLayout.PAGE_START);
        left.add(txtRA, BorderLayout.CENTER);
        
		JPanel right = new JPanel(new BorderLayout());
		right.setBackground(Color.getColor("control"));
		right.add(prompt, BorderLayout.PAGE_START);
		right.add(logpane, BorderLayout.CENTER);

		JPanel center = new JPanel(new BorderLayout());
		center.setBackground(Color.getColor("control"));
		center.add(right, BorderLayout.CENTER);
		center.add(left, BorderLayout.BEFORE_FIRST_LINE);
		center.add(picture, BorderLayout.LINE_START);
		center.add(status, BorderLayout.PAGE_END);
			
		JPanel bottom = new JPanel(new FlowLayout(FlowLayout.TRAILING));
		bottom.setBackground(Color.getColor("control"));
		bottom.add(quit);

		setLayout(new BorderLayout());
		add(center, BorderLayout.CENTER);
		add(bottom, BorderLayout.PAGE_END);

		
		this.addComponentListener(new ComponentAdapter() {
			@Override public void componentShown(ComponentEvent e) {
				init();
				start();
			}
			@Override public void componentHidden(ComponentEvent e) {
				stop();
			}
			
		});
		
		pack();
        setLocationRelativeTo(null);
	}

	protected void init()
	{
		capturer.addDataListener(new DPFPDataAdapter() {
			@Override public void dataAcquired(final DPFPDataEvent e) {
				SwingUtilities.invokeLater(new Runnable() {	public void run() {
					makeReport("As amostras de impressões digitais foram capturadas.");
					setPrompt("\r\n" + "Digitalize a impressão digital novamente.");
					process(e.getSample());
				}});
			}
		});
		capturer.addReaderStatusListener(new DPFPReaderStatusAdapter() {
			@Override public void readerConnected(final DPFPReaderStatusEvent e) {
				SwingUtilities.invokeLater(new Runnable() {	public void run() {
		 			makeReport("O leitor de impressões digitais esta conectado.");
				}});
			}
			@Override public void readerDisconnected(final DPFPReaderStatusEvent e) {
				SwingUtilities.invokeLater(new Runnable() {	public void run() {
					makeReport("O leitor de impressões digitais esta desconectado.");
				}});
			}
		});
		capturer.addSensorListener(new DPFPSensorAdapter() {
			@Override public void fingerTouched(final DPFPSensorEvent e) {
				SwingUtilities.invokeLater(new Runnable() {	public void run() {
					//makeReport("O leitor de impressões digitais foi tocado.");
				}});
			}
			@Override public void fingerGone(final DPFPSensorEvent e) {
				SwingUtilities.invokeLater(new Runnable() {	public void run() {
					//makeReport("O dedo foi removido do leitor de impressões digitais.");
				}});
			}
		});
		capturer.addImageQualityListener(new DPFPImageQualityAdapter() {
			@Override public void onImageQuality(final DPFPImageQualityEvent e) {
				SwingUtilities.invokeLater(new Runnable() {	public void run() {
					if (e.getFeedback().equals(DPFPCaptureFeedback.CAPTURE_FEEDBACK_GOOD))
						makeReport("\r\n" +"A qualidade das amostras de impressões digitais estão boas.");
					else
						makeReport("A qualidade das amostras de impressões digitais estão ruins.");
				}});
			}
		});
	}

	protected void process(DPFPSample sample)
	{
		// Draw fingerprint sample image.
		drawPicture(convertSampleToBitmap(sample));
	}

	protected void start()
	{
		capturer.startCapture();
		setPrompt("Utilize o leitor de impressão digital e digitalize.");
	}

	protected void stop()
	{
		capturer.stopCapture();
	}

	public void setStatus(String string) {
		status.setText(string);
	}
	public void setPrompt(String string) {
		prompt.setText(string);
	}
	public void makeReport(String string) {
		log.setText(string.toUpperCase() + "\n");
	}
	
	public void drawPicture(Image image) {
		picture.setIcon(new ImageIcon(
			image.getScaledInstance(picture.getWidth(), picture.getHeight(), Image.SCALE_DEFAULT)));
	}
	
	protected Image convertSampleToBitmap(DPFPSample sample) {
		return DPFPGlobal.getSampleConversionFactory().createImage(sample);
	}

	protected DPFPFeatureSet extractFeatures(DPFPSample sample, DPFPDataPurpose purpose)
	{
		DPFPFeatureExtraction extractor = DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction();
		try {
			return extractor.createFeatureSet(sample, purpose);
		} catch (DPFPImageQualityException e) {
			return null;
		}
	}
	
	
}
